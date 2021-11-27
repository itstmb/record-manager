package com.tmb.recordmanager.business_logic;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RecordManagerService {

    ResponseEntity<List<String>> getRecords(String parent);
}
