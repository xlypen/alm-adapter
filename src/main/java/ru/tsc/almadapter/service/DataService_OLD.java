//package ru.tsc.almadapter.service;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import ru.tsc.almadapter.entity.TmpDataEntity;
//import ru.tsc.almadapter.entity.PreStage;
//import ru.tsc.almadapter.dto.*;
//import ru.tsc.almadapter.repository.TmpDataRepository;
//import ru.tsc.almadapter.repository.PreStageRepository;
//
//import javax.persistence.EntityManager;
//import javax.persistence.Query;
//import java.sql.Timestamp;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Optional;
//import java.util.Set;
//
//@Service
//@RequiredArgsConstructor
//public class DataService_OLD {
//
//    private final TmpDataRepository tmpDataRepository;
//    private final PreStageRepository preStageRepository;
//    private final EntityManager entityManager;
//    private final ObjectMapper objectMapper;
//
//    private TmpDataEntity convertJsonToDataEntity(Record record) {
//        TmpDataEntity tmpDataEntity = new TmpDataEntity();
//
////        dataEntity.setInternalId(Integer.parseInt(record.internalID));
//        tmpDataEntity.setExternalId(String.valueOf(record.externalID));
//        tmpDataEntity.setHeritageId(String.valueOf(record.heritageID));
//        tmpDataEntity.setParentId(Long.valueOf(record.parentId));
//        tmpDataEntity.setValue(record.value);
//        tmpDataEntity.setVersion(Long.parseLong(record.version));
//        tmpDataEntity.setIsActive(record.isActive);
//        tmpDataEntity.setDateStart(Timestamp.valueOf(record.startDate));
//        tmpDataEntity.setDateEnd(Timestamp.valueOf(record.endDate));
//
//        ExtValues extValues = new ExtValues();
//        extValues.setExtFields(record.extFields);
//        tmpDataEntity.setExtValues(extValues);
//
//        return tmpDataEntity;
//    }
//
////    public DataEntity saveFromJson(Root root) {
////        DataEntity dataEntity = convertJsonToDataEntity(root.refArray.get(0).recordList.get(0));
////        return dataRepository.save(dataEntity);
////    }
//
//    public void processAndSaveRecords(Root root) {
//        List<TmpDataEntity> existingDataEntities = tmpDataRepository.findAll();
//
//        Set<String> jsonExternalIds = new HashSet<>();
//        for (RefArrayItem refArrayItem : root.refArray) {
//            for (Record record : refArrayItem.recordList) {
//                jsonExternalIds.add(String.valueOf(record.externalID));
//                TmpDataEntity tmpDataEntity = convertJsonToDataEntity(record);
//
//                Optional<TmpDataEntity> existingDataEntity = tmpDataRepository.findByExternalId(tmpDataEntity.getExternalId());
//
//                if (existingDataEntity.isPresent()) {
//                    TmpDataEntity updatedTmpDataEntity = updateExistingDataEntity(existingDataEntity.get(), tmpDataEntity);
//                    tmpDataRepository.save(updatedTmpDataEntity);
//                } else {
//                    tmpDataRepository.save(tmpDataEntity);
//                }
//            }
//        }
//
//        for (TmpDataEntity existingTmpDataEntity : existingDataEntities) {
//            if (!jsonExternalIds.contains(existingTmpDataEntity.getExternalId())) {
//                existingTmpDataEntity.setIsActive(false);
//                tmpDataRepository.save(existingTmpDataEntity);
//            }
//        }
//    }
//
//    private TmpDataEntity updateExistingDataEntity(TmpDataEntity existingTmpDataEntity, TmpDataEntity newTmpDataEntity) {
//        existingTmpDataEntity.setValue(newTmpDataEntity.getValue());
//        existingTmpDataEntity.setHeritageId(newTmpDataEntity.getHeritageId());
//        existingTmpDataEntity.setParentId(newTmpDataEntity.getParentId());
//        existingTmpDataEntity.setDateStart(newTmpDataEntity.getDateStart());
//        existingTmpDataEntity.setDateEnd(newTmpDataEntity.getDateEnd());
//        existingTmpDataEntity.setIsActive(newTmpDataEntity.getIsActive());
//        existingTmpDataEntity.setVersion(newTmpDataEntity.getVersion());
//        existingTmpDataEntity.setExtValues(newTmpDataEntity.getExtValues());
//
//        return existingTmpDataEntity;
//    }
//
//    public Long getMaxInternalIdByDictionaryId(Long dictionaryId) {
//        String sql = "SELECT * FROM adapter_get_max_internal_id_by_dictionary_id(:dictionaryId)";
//        Query query = entityManager.createNativeQuery(sql);
//        query.setParameter("dictionaryId", dictionaryId);
//        Object result = query.getSingleResult();
//        return result == null ? 0L : Long.parseLong(result.toString());
//    }
//
//    public void saveDataToPreStage(TmpDataEntity tmpDataEntity, Long dictionaryId) throws JsonProcessingException {
//        PreStage preStage = new PreStage();
//        preStage.setDictionaryId(dictionaryId);
//        preStage.setJsonData(objectMapper.writeValueAsString(tmpDataEntity)); // Обработайте исключение
//        preStageRepository.save(preStage);
//    }
//
//    public void processAndSaveToPreStage(Root root, Long dictionaryId) throws JsonProcessingException {
//        List<TmpDataEntity> existingDataEntities = tmpDataRepository.findAll();
//        Long maxInternalId = getMaxInternalIdByDictionaryId(dictionaryId);
//
//        for (TmpDataEntity tmpDataEntity : existingDataEntities) {
//            tmpDataEntity.setInternalId(Math.toIntExact(++maxInternalId));
//            saveDataToPreStage(tmpDataEntity, dictionaryId);
//        }
//    }
//}
