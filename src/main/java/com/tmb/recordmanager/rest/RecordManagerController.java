package com.tmb.recordmanager.rest;

import com.tmb.recordmanager.business_logic.RecordManagerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/record")
public class RecordManagerController extends RecordManagerExceptionHandler {

    private final RecordManagerService recordManagerService;

    public RecordManagerController(RecordManagerService recordManagerService) {
        this.recordManagerService = recordManagerService;
    }

    @GetMapping
    public ResponseEntity<Object> getRecords(@RequestParam(required = false) String parent) {
        return recordManagerService.getRecords(parent);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<Object> getAllRecords(@RequestParam String parent) {
        return ResponseEntity.ok(parent);
    }

    @PutMapping
    public ResponseEntity<Object> addRecords(@RequestParam List<String> records,
                                             @RequestParam(required = false) String parent) {
        return recordManagerService.addRecords(parent, records);
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteRecords(@RequestParam String parent) {
        return ResponseEntity.ok(parent + " removed");
    }
}
