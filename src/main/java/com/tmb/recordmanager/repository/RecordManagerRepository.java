package com.tmb.recordmanager.repository;

import com.tmb.recordmanager.repository.entity.Record;

import java.util.List;

public interface RecordManagerRepository {

    Record getRecord(String name);

    List<Record> getRecords(String parent);

    Record save(Record record);
}