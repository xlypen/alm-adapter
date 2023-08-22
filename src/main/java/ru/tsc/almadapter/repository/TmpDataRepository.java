package ru.tsc.almadapter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.tsc.almadapter.entity.TmpDataEntity;

import java.util.Collection;
import java.util.Optional;


@Repository
public interface TmpDataRepository extends JpaRepository<TmpDataEntity, Long> {
//    @Query(value = "select * from Data d where d.external_id in :externalIds", nativeQuery = true)
//    Set<DataEntity> findDataEntitiesByExternalId(List<String> externalIds);

    Optional<TmpDataEntity> findByExternalId(String externalId);

    //метод для удаления по externalId, не находящимся в заданной коллекции
    void deleteByExternalIdNotIn(Collection<String> externalIds);
}
