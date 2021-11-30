package com.tmb.recordmanager.business_logic;

import com.tmb.recordmanager.business_logic.validation.AddRecordsValidator;
import com.tmb.recordmanager.business_logic.validation.GenericValidationFactory;
import com.tmb.recordmanager.business_logic.validation.ParentValidator;
import com.tmb.recordmanager.repository.RecordManagerRepository;
import com.tmb.recordmanager.repository.entity.Record;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;

@Service
@Slf4j
public class RecordManagerServiceImpl implements RecordManagerService {

    private final GenericValidationFactory genericValidationFactory;
    private final RecordManagerRepository recordManagerRepository;

    @Autowired
    public RecordManagerServiceImpl(RecordManagerRepository recordManagerRepository,
                                    GenericValidationFactory genericValidationFactory) {
        this.recordManagerRepository = recordManagerRepository;
        this.genericValidationFactory = genericValidationFactory;
    }

    @Override
    public ResponseEntity<Object> getRecords(String parent) {
        genericValidationFactory.getValidator(ParentValidator.validatorName).validate(parent);
        log.debug("GET request for parent '{}' is valid", parent);

        List<String> records = this.recordManagerRepository.getRecords(parent);
        return new ResponseEntity<>(records, HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<Object> addRecords(String parent, List<String> records) {
        genericValidationFactory.getValidator(ParentValidator.validatorName).validate(parent);
        genericValidationFactory.getValidator(AddRecordsValidator.validatorName).validate(records);
        log.debug("PUT request for parent '{}' and records {} is valid", parent, records);

        HashSet<String> recordsWithoutDuplicates = new HashSet<>(records);
        for (String recordString : recordsWithoutDuplicates) {
            this.recordManagerRepository.save(new Record(recordString, parent));
        }

        log.info("Added nodes {} with parent '{}'", records, parent != null ? parent : "root");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<Object> deleteRecords(String parent) {
        genericValidationFactory.getValidator(ParentValidator.validatorName).validate(parent);
        log.debug("DELETE request for parent '{}' is valid", parent);

        int removedCount = deleteRecord(parent);

        log.info("Removed node '{}' and all child nodes (total: {})", parent != null ? parent : "root", removedCount);
        return new ResponseEntity<>(String.format("%d items removed.", removedCount), HttpStatus.OK);
    }

    private int deleteRecord(String parent) {
        int removedNodesCounter = removeChildNodes(parent);

        if (parent != null) {
            this.recordManagerRepository.deleteRecord(parent);
            removedNodesCounter++;
        }
        return removedNodesCounter;
    }

    private int removeChildNodes(String parent) {
        List<String> records = this.recordManagerRepository.getRecords(parent);

        if (records.isEmpty()) {
            log.debug("Node '{}' has no child nodes.", parent);
        } else {
            log.debug("Node '{}' has {} child nodes, deleting child nodes... ", parent, records.size());
        }

        int removedNodesCounter = 0;
        for (String recordName : records) {
            removedNodesCounter += deleteRecord(recordName);
        }
        return removedNodesCounter;
    }
}
