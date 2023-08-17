package ru.tsc.almadapter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.tsc.almadapter.entity.DataEntity;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Repository
public interface DataRepository extends JpaRepository<DataEntity, Long> {
//    @Query(value = "select * from Data d where d.external_id in :externalIds", nativeQuery = true)
//    Set<DataEntity> findDataEntitiesByExternalId(List<String> externalIds);

    Optional<DataEntity> findByExternalId(String externalId);

    //метод для удаления по externalId, не находящимся в заданной коллекции
    void deleteByExternalIdNotIn(Collection<String> externalIds);
}
