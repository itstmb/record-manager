package com.tmb.recordmanager.repository;

import com.tmb.recordmanager.repository.entity.Record;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class RecordManagerRepositoryImpl implements RecordManagerRepository {

    private final EntityManager entityManager;

    public RecordManagerRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Record> getRecords(String parent) {
        return null;
    }

    public Record save(Record record) {
        entityManager.persist(record);
        return record;
    }
}
