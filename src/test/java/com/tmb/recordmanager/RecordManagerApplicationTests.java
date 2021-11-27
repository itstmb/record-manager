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

import java.util.ArrayList;
import java.util.Arrays;

@SpringBootTest
@ContextConfiguration(classes = {RecordManagerTestConfiguration.class})
class RecordManagerApplicationTests {

    @Autowired
    RecordManagerController recordManagerController;

    @Test
    public void getEmptyRecordsList() {
        ResponseEntity<Object> response = recordManagerController.getRecords(StringUtils.EMPTY);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(ArrayUtils.EMPTY_STRING_ARRAY, response.getBody());
    }

    @Test
    public void getNonexistingParent() {
        ResponseEntity<Object> response = recordManagerController.getRecords("nonexistingParent");

        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assertions.assertNull(response.getBody());
    }

    @Test
    public void getValidParent() {
        // Add mock data from DB
        ArrayList<String> expectedRecords = new ArrayList<>(Arrays.asList("one", "two", "three"));

        ResponseEntity<Object> response = recordManagerController.getRecords("existingParent");

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(expectedRecords, response.getBody());
    }
}
