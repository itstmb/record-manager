package com.tmb.recordmanager;

import com.tmb.recordmanager.rest.RecordManagerController;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = {RecordManagerTestConfiguration.class})
class RecordManagerApplicationTests {

    @Autowired
    RecordManagerController recordManagerController;

    @Test
    public void getEmptyRecordsList() {
        ResponseEntity<Object> response = recordManagerController.getRecords(StringUtils.EMPTY);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        System.out.println("Asserted!");
        Assertions.assertEquals(ArrayUtils.EMPTY_STRING_ARRAY, response.getBody());
    }
}
