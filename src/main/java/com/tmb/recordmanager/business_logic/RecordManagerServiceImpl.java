package com.tmb.recordmanager.business_logic;

import com.tmb.recordmanager.repository.RecordManagerRepository;
import com.tmb.recordmanager.repository.entity.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class RecordManagerServiceImpl implements RecordManagerService {

    private final RecordManagerRepository recordManagerRepository;

    @Autowired
    public RecordManagerServiceImpl(RecordManagerRepository recordManagerRepository) {
        this.recordManagerRepository = recordManagerRepository;
    }

    @Override
    public ResponseEntity<List<String>> getRecords(String parent) {
        return null;
    }

    @Override
    @Transactional
    public ResponseEntity<Object> addRecords(String parent, List<String> records) {
        for (String recordString : records) {
            this.recordManagerRepository.save(new Record(recordString, parent));
        }
        return null;
    }
}
