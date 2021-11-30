package com.tmb.recordmanager.repository;

import com.tmb.recordmanager.repository.entity.Record;

import java.util.List;

public interface RecordManagerRepository {

    Record getRecord(String name);

    List<String> getRecords(String parent);

    void save(Record record);
}
