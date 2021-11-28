package com.tmb.recordmanager.rest;

import com.tmb.recordmanager.business_logic.RecordManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/record/{parent}")
public class RecordManagerController {

    private final RecordManagerService recordManagerService;

    public RecordManagerController(RecordManagerService recordManagerService) {
        this.recordManagerService = recordManagerService;
    }

    @GetMapping
    public ResponseEntity<Object> getRecords(@PathVariable String parent) {
        return ResponseEntity.ok(parent);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<Object> getAllRecords(@PathVariable String parent) {
        return ResponseEntity.ok(parent);
    }

    @PutMapping
    public ResponseEntity<Object> addRecords(@PathVariable String parent,
                                             @RequestParam List<String> records) {
        recordManagerService.addRecords(parent, records);
        return ResponseEntity.ok(parent + " <-- " + records);
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteRecords(@PathVariable String parent) {
        return ResponseEntity.ok(parent + " removed");
    }
}
