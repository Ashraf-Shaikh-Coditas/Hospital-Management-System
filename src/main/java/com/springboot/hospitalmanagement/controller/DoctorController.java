package com.springboot.hospitalmanagement.controller;

import com.springboot.hospitalmanagement.payload.DoctorDto;
import com.springboot.hospitalmanagement.service.DoctorService;
import com.springboot.hospitalmanagement.service.NurseService;
import com.springboot.hospitalmanagement.service.impl.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/hms/")

public class DoctorController {
    @Autowired
    private DoctorService doctorService;

    @Autowired
    private NurseService nurseService;

    @Autowired
    private EmailSenderService emailSenderService;

    @PostMapping("/addDoctor")
    public ResponseEntity<HashMap<DoctorDto, String>> addDoctor(@RequestBody DoctorDto doctorDto) {
        HashMap<DoctorDto, String> map = new HashMap<>();
        DoctorDto newDoctorDto = doctorService.addDoctor(doctorDto);
        if (newDoctorDto == null) {
            map.put(null, "Error occured while adding Doctor.");
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }
        map.put(newDoctorDto, null);
        emailSenderService.sendSimpleEmail(doctorDto.getEmail(),newDoctorDto.show(),"DOCTOR " +
                "VERIFICATION MAIL.");
        return new ResponseEntity<>(map, HttpStatus.CREATED);

    }

    @CrossOrigin
    @PutMapping("/updateDoctor/{doctorId}")
    public ResponseEntity<HashMap<DoctorDto, String>> updateDoctor(@RequestBody DoctorDto doctorDto,
                                                                   @PathVariable long doctorId) {
        HashMap<DoctorDto, String> map = new HashMap<>();
        DoctorDto newDoctorDto = doctorService.updateDoctor(doctorDto,doctorId);
        if (newDoctorDto == null) {
            map.put(null, "Error occured while updating Doctor.");
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }
        map.put(newDoctorDto, null);
        return new ResponseEntity<>(map, HttpStatus.CREATED);

    }

    @CrossOrigin
    @DeleteMapping("/deleteDoctor/{doctorId}")
    public ResponseEntity<HashMap<String,String>> deleteDoctor(@PathVariable Long doctorId) {
        doctorService.deleteDoctor(doctorId);
        HashMap<String, String> map = new HashMap<>();
        map.put("Dcotor deleted successfully",null);
        return new ResponseEntity<>(map,HttpStatus.OK);
    }

    @GetMapping("/doctors")
    public ResponseEntity<List<DoctorDto>> getAllDoctors() {
        return new ResponseEntity<>(doctorService.getAllDoctors(),HttpStatus.OK);
    }


}
