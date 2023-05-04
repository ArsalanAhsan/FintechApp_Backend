package com.iconsult.controller;

import com.iconsult.entity.Complaint;
import com.iconsult.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/complaint")
public class ComplaintController {

    @Autowired
    ComplaintService complaintService;

    @GetMapping("/")
    public ResponseEntity<?> getAllComplaint(@RequestHeader ("userId") int userId) {
        return new ResponseEntity<>(complaintService.getAllComplaint(userId), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<?> addComplaint(@RequestBody Complaint complaint, @RequestHeader ("userId") int userId) {
        complaint.setUserId(userId);
        return new ResponseEntity<>(complaintService.addComplaint(complaint), HttpStatus.CREATED);
    }

    @GetMapping("/{complaintId}")
    public ResponseEntity<?> getComplaintById(@PathVariable("complaintId") Long id, @RequestHeader ("userId") int userId) {
        return new ResponseEntity<>(complaintService.getComplaintById(id,userId), HttpStatus.OK);
    }

    @PutMapping("/{complaintId}")
    public ResponseEntity<?> updateComplaint(@PathVariable("complaintId") Long id,@RequestBody Complaint complaint) {
        return new ResponseEntity<>(complaintService.updateComplaint(complaint,id), HttpStatus.FOUND);
    }

    @DeleteMapping("/{complaintId}")
    public ResponseEntity<?> deleteComplaint(@PathVariable("complaintId") Long id) {
        return new ResponseEntity<>(complaintService.deleteComplaint(id), HttpStatus.FOUND);
    }
}
