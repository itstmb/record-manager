package com.tmb.recordmanager.business_logic.validation;

import com.tmb.recordmanager.repository.RecordManagerRepository;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.xml.bind.ValidationException;

@Component
@AllArgsConstructor
@Slf4j
public class ParentValidator implements BasicValidator {

    private final RecordManagerRepository recordManagerRepository;
    private final String className = this.getClass().getSimpleName();

    @SneakyThrows
    @Override
    public boolean validate(Object o) {
        if (o == null) {
            log.info("{} validation error - object is null.", className);
            throw new ValidationException("Parent is null");
        }

        if (!(o instanceof String)) {
            log.info("{} validation error - object is not instance of string.", className);
            throw new ValidationException("Parent is not a valid word");
        }

        String parent = (String) o;

        if (recordManagerRepository.getRecord(parent) == null) {
            log.info("{} validation error - parent {} doesn't exist.", className, parent);
            throw new ValidationException("Parent doesn't exist");
        }
        return true;
    }
}
