package com.tmb.recordmanager.repository;

import com.tmb.recordmanager.repository.entity.Record;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class RecordManagerRepositoryImpl implements RecordManagerRepository {

    private final EntityManager entityManager;

    public RecordManagerRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Record getRecord(String name) {
        return entityManager.find(Record.class, name);
    }

    @Override
    public List<String> getRecords(String parent) {
        List<String> records;
        if (parent != null) {
            TypedQuery<String> query = entityManager.createQuery("select r.name from Record r where r.parent = :parent", String.class);
            query.setParameter("parent", parent);
            records = query.getResultList();
        } else {
            TypedQuery<String> query = entityManager.createQuery("select r.name from Record r where r.parent is null", String.class);
            records = query.getResultList();
        }
        return records;
    }

    public void save(Record record) {
        entityManager.persist(record);
    }

    @Override
    public void deleteRecord(String parent) {
        Record record = this.getRecord(parent);
        entityManager.remove(record);
    }
}
