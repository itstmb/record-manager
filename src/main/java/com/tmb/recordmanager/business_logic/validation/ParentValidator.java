package com.tmb.recordmanager.business_logic.validation;

import com.tmb.recordmanager.repository.RecordManagerRepository;
import com.tmb.recordmanager.rest.exceptions.ValidationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class ParentValidator implements BasicValidator {

    public static final String validatorName = "parentValidator";
    private final RecordManagerRepository recordManagerRepository;

    @Override
    public boolean validate(Object o) {
        if (o == null) {
            return true;
        }

        if (!(o instanceof String)) {
            log.info("{} validation error - object is not instance of string.", validatorName);
            throw new ValidationException("Parent is not a valid word");
        }

        String parent = (String) o;

        if (recordManagerRepository.getRecord(parent) == null) {
            log.info("{} validation error - parent {} doesn't exist.", validatorName, parent);
            throw new ValidationException(String.format("Parent '%s' doesn't exist", parent));
        }
        return true;
    }
}
