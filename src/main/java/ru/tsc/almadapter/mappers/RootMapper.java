package ru.tsc.almadapter.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.tsc.almadapter.dto.Record;
import ru.tsc.almadapter.dto.Root;
import ru.tsc.almadapter.entity.DataEntity;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface RootMapper {


    default List<DataEntity> rootToDataEntities(Root root) {
        if (root == null || root.getRefArray() == null) {
            return Collections.emptyList();
        }

        return root.getRefArray().stream()
                .flatMap(refArrayItem -> refArrayItem.getRecordList().stream())
                .map(this::recordToDataEntity)
                .collect(Collectors.toList());
    }

    @Mapping(target = "valueId", source = "internalID")
    @Mapping(target = "dictionaryId", ignore = true)
    @Mapping(target = "version", source = "version")
    @Mapping(target = "internalId", ignore = true)
    @Mapping(target = "hash", ignore = true)
    @Mapping(target = "parentId", source = "parentId")
    @Mapping(target = "value", source = "value")
    @Mapping(target = "extValues.extFields", source = "extFields")
    @Mapping(target = "isActive", source = "isActive")
    @Mapping(target = "startDate", source = "startDate")
    @Mapping(target = "endDate", source = "endDate")
    @Mapping(target = "externalId", source = "externalID")
    @Mapping(target = "heritageId", source = "heritageID")
    @Mapping(target = "recordId", ignore = true)
    DataEntity recordToDataEntity(Record record);

    @Mapping(target = "internalID", source = "valueId")
    @Mapping(target = "externalID", source = "externalId")
    @Mapping(target = "heritageID", source = "heritageId")
    @Mapping(target = "parentId", source = "parentId")
    @Mapping(target = "value", source = "value")
    @Mapping(target = "version", source = "version")
    @Mapping(target = "isActive", source = "isActive")
    @Mapping(target = "startDate", source = "startDate")
    @Mapping(target = "endDate", source = "endDate")
    @Mapping(target = "extFields", source = "extValues.extFields")
    Record dataEntityToRecord(DataEntity dataEntity);

    @Mapping(target = "externalId", ignore = true)
        // исключаем externalId из обновления
    void updateEntityFromRecord(@MappingTarget DataEntity existingEntity, Record record);
}

