package ru.tsc.almadapter.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tsc.almadapter.entity.DataEntity;
import ru.tsc.almadapter.dto.*;
import ru.tsc.almadapter.repository.DataRepository;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DataService {

    private final DataRepository dataRepository;

    private DataEntity convertJsonToDataEntity(Record record) {
        DataEntity dataEntity = new DataEntity();

        dataEntity.setInternalId(Integer.parseInt(record.internalID));
        dataEntity.setExternalId(String.valueOf(record.externalID));
        dataEntity.setHeritageId(String.valueOf(record.heritageID));
        dataEntity.setParentId(Long.valueOf(record.parentId));
        dataEntity.setValue(record.value);
        dataEntity.setVersion(Long.parseLong(record.version));
        dataEntity.setIsActive(record.isActive);
        dataEntity.setDateStart(Timestamp.valueOf(record.startDate));
        dataEntity.setDateEnd(Timestamp.valueOf(record.endDate));

        ExtValues extValues = new ExtValues();
        extValues.setExtFields(record.extFields);
        dataEntity.setExtValues(extValues);

        return dataEntity;
    }

    public DataEntity saveFromJson(Root root) {
        DataEntity dataEntity = convertJsonToDataEntity(root.refArray.get(0).recordList.get(0));
        return dataRepository.save(dataEntity);
    }

    public void processAndSaveRecords(Root root) {
        List<DataEntity> existingDataEntities = dataRepository.findAll();

        // Создаем множество для хранения внешних ID из JSON
        Set<String> jsonExternalIds = new HashSet<>();
        for (RefArrayItem refArrayItem : root.refArray) {
            for (Record record : refArrayItem.recordList) {
                jsonExternalIds.add(String.valueOf(record.externalID));
                DataEntity dataEntity = convertJsonToDataEntity(record);

                // Поиск записи по externalID
                Optional<DataEntity> existingDataEntity = dataRepository.findByExternalId(dataEntity.getExternalId());

                if (existingDataEntity.isPresent()) {
                    // Обновить запись
                    DataEntity updatedDataEntity = updateExistingDataEntity(existingDataEntity.get(), dataEntity);
                    dataRepository.save(updatedDataEntity);
                } else {
                    // Создать новую запись
                    dataRepository.save(dataEntity);
                }
            }
        }

        // Деактивация записей, которые есть в базе, но не пришли в JSON
        for (DataEntity existingDataEntity : existingDataEntities) {
            if (!jsonExternalIds.contains(existingDataEntity.getExternalId())) {
                existingDataEntity.setIsActive(false);
                dataRepository.save(existingDataEntity);
            }
        }
    }

    private DataEntity updateExistingDataEntity(DataEntity existingDataEntity, DataEntity newDataEntity) {
        // Здесь логика обновления существующей записи
        existingDataEntity.setValue(newDataEntity.getValue());
        existingDataEntity.setHeritageId(newDataEntity.getHeritageId());
        existingDataEntity.setParentId(newDataEntity.getParentId());
        existingDataEntity.setDateStart(newDataEntity.getDateStart());
        existingDataEntity.setDateEnd(newDataEntity.getDateEnd());
        existingDataEntity.setIsActive(newDataEntity.getIsActive());
        existingDataEntity.setVersion(newDataEntity.getVersion());
        existingDataEntity.setExtValues(newDataEntity.getExtValues());

        return existingDataEntity;
    }
}


//    public void processJson(Root root) {
//
//
//
//        // // Шаг 1: Создаем список идентификаторов из entitiesFromRoot
//        List<String> externalIdsFromRoot = rootMapper.rootToDataEntities(root).stream()
//                .map(DataEntity::getExternalId)
//                .collect(Collectors.toList());
//        // Получаем список сущностей в бд
//        List<DataEntity> entitiesSetFromAlmInDb = dataRepository.findAll();
//
//        // Получаем список сущностей из рута
//        List<DataEntity> entitiesFromRoot = rootMapper.rootToDataEntities(root);
//
//        // Шаг 2 и 3: Итерируемся по entitiesSetFromAlmInDb и проверяем externalId
//        List<DataEntity> entitiesToDelete = new ArrayList<>();
//        for (DataEntity entity : entitiesSetFromAlmInDb) {
//            if (!externalIdsFromRoot.contains(entity.getExternalId())) {
//                entitiesToDelete.add(entity);
//            }
//        }
//
//        // Шаг 4: Удаляем найденные сущности
//        dataRepository.deleteAll(entitiesToDelete);
//        // Сохраняем сущности из джейсона
//        dataRepository.saveAll(entitiesFromRoot);
//
//    }



