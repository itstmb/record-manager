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
    public ResponseEntity<List<String>> getRecords(String parent) {
        return null;
    }

    @Override
    @Transactional
    public ResponseEntity<Object> addRecords(String parent, List<String> records) {
        if (parent != null) { // a null parent is valid and indicates a root level node
            genericValidationFactory.getValidator(ParentValidator.validatorName).validate(parent);
        }
        genericValidationFactory.getValidator(AddRecordsValidator.validatorName).validate(records);

        HashSet<String> recordsWithoutDuplicates = new HashSet<>(records);
        for (String recordString : recordsWithoutDuplicates) {
            this.recordManagerRepository.save(new Record(recordString, parent));
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
