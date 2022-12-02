package com.springboot.hospitalmanagement.controller;

import com.springboot.hospitalmanagement.payload.DoctorDto;
import com.springboot.hospitalmanagement.payload.NurseDto;
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

@RestController
@RequestMapping("/api/hms/")

public class NurseController {
    @Autowired
    private DoctorService doctorService;

    @Autowired
    private NurseService nurseService;

    @Autowired
    private EmailSenderService emailSenderService;

    @PostMapping("/addNurse")
    public ResponseEntity<HashMap<NurseDto, String>> addNurse(@RequestBody NurseDto nurseDto) {
        HashMap<NurseDto, String> map = new HashMap<>();
        NurseDto newNurseDto = nurseService.addNurse(nurseDto);
        if (newNurseDto == null) {
            map.put(null, "Error occured while adding Nurse.");
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }
        map.put(newNurseDto, null);
        emailSenderService.sendSimpleEmail(nurseDto.getEmail(),newNurseDto.show(),"NURSE " +
                "VERIFICATION MAIL.");
        return new ResponseEntity<>(map, HttpStatus.CREATED);

    }


    @CrossOrigin
    @PutMapping("/updateNurse/{nurseId}")
    public ResponseEntity<HashMap<NurseDto, String>> updateDoctor(@RequestBody NurseDto nurseDto,
                                                                   @PathVariable long nurseId) {
        HashMap<NurseDto, String> map = new HashMap<>();
        NurseDto newNurseDto = nurseService.updateNurse(nurseDto,nurseId);
        if (newNurseDto == null) {
            map.put(null, "Error occured while updating Nurse.");
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }
        map.put(newNurseDto, null);
        return new ResponseEntity<>(map, HttpStatus.CREATED);

    }

    @CrossOrigin
    @DeleteMapping("/deleteNurse/{nurseId}")
    public ResponseEntity<HashMap<String,String>> deleteDoctor(@PathVariable Long nurseId) {
        nurseService.deleteNurse(nurseId);
        HashMap<String, String> map = new HashMap<>();
        map.put("Nurse deleted successfully",null);
        return new ResponseEntity<>(map,HttpStatus.OK);
    }

    @GetMapping("/allocatedNurses")
    public ResponseEntity<List<NurseDto>> getAllocatedNurses() {
        return new ResponseEntity<>(nurseService.findAllocatedNurses(),HttpStatus.OK);
    }

    @GetMapping("/unallocatedNurses")
    public ResponseEntity<List<NurseDto>> getUnAllocatedNurses() {
        return new ResponseEntity<>(nurseService.findUnAllocatedNurses(),HttpStatus.OK);
    }

    @GetMapping("/allNurses")
    public ResponseEntity<List<NurseDto>> getAllNurses() {
        return new ResponseEntity<>(nurseService.getAllNurses(),HttpStatus.OK);
    }


}
