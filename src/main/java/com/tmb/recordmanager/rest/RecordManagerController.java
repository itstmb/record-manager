package com.tmb.recordmanager.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/record/{parent}")
@RequiredArgsConstructor
public class RecordManagerController {

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
        return ResponseEntity.ok(parent + " <-- " + records);
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteRecords(@PathVariable String parent) {
        return ResponseEntity.ok(parent + " removed");
    }
}
