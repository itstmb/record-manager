package com.tmb.recordmanager.business_logic;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordManagerServiceImpl implements RecordManagerService {
    @Override
    public ResponseEntity<List<String>> getRecords(String parent) {
        return null;
    }
}
