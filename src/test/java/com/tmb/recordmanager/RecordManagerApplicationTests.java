package com.tmb.recordmanager;

import com.tmb.recordmanager.business_logic.validation.GenericValidationFactory;
import com.tmb.recordmanager.mocks.EntityManagerMock;
import com.tmb.recordmanager.repository.entity.Record;
import com.tmb.recordmanager.rest.RecordManagerController;
import com.tmb.recordmanager.rest.exceptions.ValidationException;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import java.util.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(classes = {RecordManagerTestConfiguration.class})
class RecordManagerApplicationTests {

    @Autowired
    RecordManagerController recordManagerController;

    @Autowired
    EntityManagerMock entityManagerMock;

    @Autowired
    GenericValidationFactory genericValidationFactory;

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

    @Test
    public void addValidRecords() {
        ArrayList<String> recordsToSave = new ArrayList<>(Arrays.asList("record1", "record2", "record3"));

        HashSet<Record> expectedRecords = new HashSet<>();
        for (String recordName : recordsToSave) {
            expectedRecords.add(new Record(recordName, null));
        }

        ResponseEntity<Object> response = recordManagerController.addRecords(recordsToSave, null);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(expectedRecords, entityManagerMock.getItems());
    }

    @Test
    public void addValidRecordsWithParent() {
        String parent = "parent_record";
        recordManagerController.addRecords(Collections.singletonList(parent), null);

        ArrayList<String> recordsToSave = new ArrayList<>(Arrays.asList("record1", "record2", "record3"));

        HashSet<Record> expectedRecords = new HashSet<>();
        expectedRecords.add(new Record(parent, null));
        for (String recordName : recordsToSave) {
            expectedRecords.add(new Record(recordName, parent));
        }

        ResponseEntity<Object> response = recordManagerController.addRecords(recordsToSave, parent);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(expectedRecords, entityManagerMock.getItems());
    }

    @Test
    public void addDuplicateRecords() {
        ArrayList<String> recordsToSave = new ArrayList<>(Arrays.asList("record1", "record2", "record1", "record1", "record2"));

        HashSet<Record> expectedRecords = new HashSet<Record>() {{
            add(new Record(recordsToSave.get(0), null));
            add(new Record(recordsToSave.get(1), null));
        }};

        ResponseEntity<Object> response = recordManagerController.addRecords(recordsToSave, null);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(expectedRecords, entityManagerMock.getItems());
    }

    @Test
    public void addWithNonexistingParent() {
        String recordToSave = "record";
        String parent = "NonexistingParent";

        Assertions.assertThrows(ValidationException.class,
                () -> recordManagerController.addRecords(Collections.singletonList(recordToSave), parent));
    }

    @Test
    public void addExistingRecord() {
        String recordToSave = "record";
        recordManagerController.addRecords(Collections.singletonList(recordToSave), null);

        Assertions.assertThrows(ValidationException.class,
                () -> recordManagerController.addRecords(Collections.singletonList(recordToSave), null));
    }

    @Test
    public void addNullRecord() {
        Assertions.assertThrows(ValidationException.class,
                () -> recordManagerController.addRecords(null, null));
    }
}
