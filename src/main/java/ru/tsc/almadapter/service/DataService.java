package ru.tsc.almadapter.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tsc.almadapter.entity.TmpDataEntity;
import ru.tsc.almadapter.entity.PreStage;
import ru.tsc.almadapter.dto.*;
import ru.tsc.almadapter.repository.TmpDataRepository;
import ru.tsc.almadapter.repository.PreStageRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DataService {

    private final TmpDataRepository tmpDataRepository;
    private final PreStageRepository preStageRepository;
    private final EntityManager entityManager;
    private final ObjectMapper objectMapper;

    // Конвертация Record в TmpDataEntity
    private TmpDataEntity convertJsonToDataEntity(Record record) {
        TmpDataEntity tmpDataEntity = new TmpDataEntity();

        tmpDataEntity.setExternalId(String.valueOf(record.externalID));
        tmpDataEntity.setHeritageId(String.valueOf(record.heritageID));
        tmpDataEntity.setParentId(Long.valueOf(record.parentId));
        tmpDataEntity.setValue(record.value);
        tmpDataEntity.setVersion(Long.parseLong(record.version));
        tmpDataEntity.setIsActive(record.isActive);
        tmpDataEntity.setDateStart(Timestamp.valueOf(record.startDate));
        tmpDataEntity.setDateEnd(Timestamp.valueOf(record.endDate));

        ExtValues extValues = new ExtValues();
        extValues.setExtFields(record.extFields);
        tmpDataEntity.setExtValues(extValues);

        return tmpDataEntity;
    }

    // Процесс сохранения во временную таблицу
    private void processAndSaveToTmpData(Root root) {
        for (RefArrayItem refArrayItem : root.refArray) {
            for (Record record : refArrayItem.recordList) {
                TmpDataEntity tmpDataEntity = convertJsonToDataEntity(record);
                tmpDataRepository.save(tmpDataEntity);
            }
        }
    }

    // Получение максимального internalId
    public Long getMaxInternalIdByDictionaryId(Long dictionaryId) {
        String sql = "SELECT * FROM adapter_get_max_internal_id_by_dictionary_id(:dictionaryId)";
        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("dictionaryId", dictionaryId);
        Object result = query.getSingleResult();
        return result == null ? 0L : Long.parseLong(result.toString());
    }

    // Процесс сохранения в таблицу preStage
    public void processAndSaveToPreStage(Long dictionaryId) throws JsonProcessingException {
        List<TmpDataEntity> existingDataEntities = tmpDataRepository.findAll();
        Long maxInternalId = getMaxInternalIdByDictionaryId(dictionaryId);

        LinkedList<TmpDataEntity> orderedList = new LinkedList<>();

        // Добавляем записи с ExternalId в начало списка
        for (TmpDataEntity entity : existingDataEntities) {
            if (entity.getExternalId() != null && !entity.getExternalId().isEmpty()) {
                orderedList.addFirst(entity);
            } else {
                orderedList.addLast(entity);
            }
        }

        for (TmpDataEntity tmpDataEntity : orderedList) {
            tmpDataEntity.setInternalId(Math.toIntExact(++maxInternalId));
            saveDataToPreStage(tmpDataEntity, dictionaryId);
        }
    }

    // Сохранение данных в preStage
    private void saveDataToPreStage(TmpDataEntity tmpDataEntity, Long dictionaryId) throws JsonProcessingException {
        PreStage preStage = new PreStage();
        preStage.setDictionaryId(dictionaryId);
        preStage.setJsonData(objectMapper.writeValueAsString(tmpDataEntity));
        preStageRepository.save(preStage);
    }

    // Главный метод, который объединяет все шаги и может быть вызван из контроллера
    public void processAll(Root root, Long dictionaryId) throws JsonProcessingException {
        processAndSaveToTmpData(root);
        processAndSaveToPreStage(dictionaryId);
    }
}
