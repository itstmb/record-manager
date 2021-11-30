package com.tmb.recordmanager.business_logic;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RecordManagerService {

    ResponseEntity<Object> getRecords(String parent);

    ResponseEntity<Object> addRecords(String parent, List<String> records);
}
