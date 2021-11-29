package com.tmb.recordmanager.business_logic.validation;

import jdk.nashorn.internal.runtime.regexp.joni.exception.InternalException;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Setter
@Slf4j
public class GenericValidationFactory {

    private Map<String, BasicValidator> validatorsMap;

    public GenericValidationFactory(ApplicationContext applicationContext) {
        this.validatorsMap = applicationContext.getBeansOfType(BasicValidator.class);
    }

    public BasicValidator getValidator(String validatorName) {
        BasicValidator validator = this.validatorsMap.get(validatorName);
        if (validator == null) {
            log.error("Cannot find BasicValidator bean object for target '{}'.", validatorName);
            throw new InternalException("Cannot find BasicValidator");
        } else {
            return validator;
        }
    }
}
