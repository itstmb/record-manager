package com.tmb.recordmanager.rest;

import com.tmb.recordmanager.business_logic.RecordManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/record")
@Slf4j
public class RecordManagerController extends RecordManagerExceptionHandler {

    private final RecordManagerService recordManagerService;

    public RecordManagerController(RecordManagerService recordManagerService) {
        this.recordManagerService = recordManagerService;
    }

    @GetMapping
    public ResponseEntity<Object> getRecords(@RequestParam(required = false) String parent) {
        log.debug("Received request of type GET");
        return recordManagerService.getRecords(parent);
    }

    @PutMapping
    public ResponseEntity<Object> addRecords(@RequestParam List<String> records,
                                             @RequestParam(required = false) String parent) {
        log.debug("Received request of type PUT");
        return recordManagerService.addRecords(parent, records);
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteRecords(@RequestParam(required = false) String parent) {
        log.debug("Received request of type DELETE");
        return recordManagerService.deleteRecords(parent);
    }
}
