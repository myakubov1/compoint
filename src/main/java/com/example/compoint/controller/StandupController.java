package com.example.compoint.controller;

import com.example.compoint.entity.StandupEntity;
import com.example.compoint.exception.AccessDenied;
import com.example.compoint.exception.StandupAlreadyExist;
import com.example.compoint.exception.StandupNotFound;
import com.example.compoint.exception.UserNotFound;
import com.example.compoint.service.StandupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/standups")
public class StandupController {
    @Autowired
    private StandupService standupService;

    public StandupController(StandupService standupService) {
        this.standupService = standupService;
    }

    //Создание нового стендапа, {id} должен быть равен userId
    @PostMapping("{id}/create")
    @PreAuthorize("hasAuthority('ADMIN') or #id == principal.id")
    public ResponseEntity createStandup(@PathVariable Long id, @RequestBody StandupEntity standup) {

        try {
            return ResponseEntity.ok(standupService.create(standup, id));
        } catch (StandupAlreadyExist | UserNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity getAllStandups() {
        try {
            return ResponseEntity.ok(standupService.getAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or #id == principal.id")
    public ResponseEntity getStandupById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(standupService.getById(id));
        } catch (StandupNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/search")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity getStandupByName(@RequestParam String name) {
        try {
            return ResponseEntity.ok(standupService.getByName(name));
        } catch (StandupNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //TODO: ДОДЕЛАТЬ ЛОГИКУ
    @PutMapping("/{id}/update")
    @PreAuthorize("hasAuthority('ADMIN') or #id == principal.id")
    public ResponseEntity updateStandup(@PathVariable Long id, @RequestBody StandupEntity standup){
        try {
            return ResponseEntity.ok(standupService.update(id, standup));
        } catch (StandupNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (StandupAlreadyExist e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity deleteStandup(@PathVariable Long id){
        try {
            return ResponseEntity.ok(standupService.delete(id));
        } catch (StandupNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (AccessDenied e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }
}
