package com.tmb.recordmanager.business_logic.validation;

import com.tmb.recordmanager.repository.RecordManagerRepository;
import com.tmb.recordmanager.rest.exceptions.ValidationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class AddRecordsValidator implements BasicValidator {

    public static final String validatorName = "addRecordsValidator";
    private final RecordManagerRepository recordManagerRepository;

    @Override
    public boolean validate(Object o) {
        if (o == null) {
            log.info("{} validation error - object is null.", validatorName);
            throw new ValidationException("Record is null");
        }

        if (!(o instanceof List)) {
            log.info("{} validation error - object is not instance of list.", validatorName);
            throw new ValidationException("Records list is invalid");
        }

        List<Object> recordObjects = (List<Object>) o;
        for (Object item : recordObjects) {
            if (!(item instanceof String)) {
                log.info("{} validation error - record is not a valid string", validatorName);
                throw new ValidationException("A record within records list is invalid");
            }
        }

        List<String> recordStrings = (List<String>) o;
        for (String record : recordStrings) {
            if (recordManagerRepository.getRecord(record) != null) {
                log.info("{} validation error - record already exists", validatorName);
                throw new ValidationException(String.format("Record '%s' already exists", record));
            }
        }

        return true;
    }
}
