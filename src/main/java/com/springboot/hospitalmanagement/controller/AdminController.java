package com.springboot.hospitalmanagement.controller;

import com.springboot.hospitalmanagement.payload.DoctorDto;
import com.springboot.hospitalmanagement.payload.NurseDto;
import com.springboot.hospitalmanagement.service.AdminService;
import com.springboot.hospitalmanagement.service.DoctorService;
import com.springboot.hospitalmanagement.service.NurseService;
import com.springboot.hospitalmanagement.service.impl.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/admin/")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/assignNurseToDoctor/{doctorId}/{nurseId}")
    public String assignNurseToDoctor(@PathVariable long doctorId, @PathVariable long nurseId) {
        return adminService.assignNurseToDoctor(doctorId, nurseId);
    }

    @PostMapping("/assignDoctorToNurse/{nurseId}/{doctorId}")
    public String assignDoctorToNurse(@PathVariable long nurseId,@PathVariable long doctorId) {
        return adminService.assignDoctorToNurse(nurseId,doctorId);
    }

}
