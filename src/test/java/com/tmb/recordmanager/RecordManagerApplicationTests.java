package com.tmb.recordmanager;

import com.tmb.recordmanager.business_logic.validation.GenericValidationFactory;
import com.tmb.recordmanager.mocks.EntityManagerMock;
import com.tmb.recordmanager.repository.entity.Record;
import com.tmb.recordmanager.rest.RecordManagerController;
import com.tmb.recordmanager.rest.exceptions.ValidationException;
import org.hibernate.query.Query;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

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

    @Autowired
    Query query;

    @Test
    public void getEmptyRecordsList() {
        ArrayList<String> expectedArray = new ArrayList<>();
        addValidRecords();
        ResponseEntity<Object> response = recordManagerController.getRecords("record1");

        Mockito.when(query.setParameter((String) Mockito.any(), Mockito.any())).thenReturn(query);
        Mockito.when(query.getResultList()).thenReturn(expectedArray);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(expectedArray, response.getBody());
    }

    @Test
    public void getNonexistingParent() {
        Assertions.assertThrows(ValidationException.class,
                () -> recordManagerController.getRecords("nonexistingParent"));
    }

    @Test
    public void getValidRecords() {
        addValidRecords();
        ArrayList<String> expectedRecords = new ArrayList<>(Arrays.asList("record1", "record2", "record3"));

        Mockito.when(query.getResultList()).thenReturn(expectedRecords);

        ResponseEntity<Object> response = recordManagerController.getRecords(null);

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

    @Test
    public void deleteExistingRecord() {
        addValidRecords();

        ResponseEntity<Object> response = recordManagerController.deleteRecords("record1");

        HashSet<Record> expectedRecords = new HashSet<Record>() {{
            add(new Record("record2", null));
            add(new Record("record3", null));
        }};

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(expectedRecords, entityManagerMock.getItems());
    }

    @Test
    public void deleteRootRecord() {

    }

    @Test
    public void deleteNonexistingRecord() {

    }
}
