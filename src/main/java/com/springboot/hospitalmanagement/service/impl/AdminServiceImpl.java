package com.springboot.hospitalmanagement.service.impl;

import com.springboot.hospitalmanagement.entity.Doctor;
import com.springboot.hospitalmanagement.entity.Nurse;
import com.springboot.hospitalmanagement.repository.DoctorRepository;
import com.springboot.hospitalmanagement.repository.NurseRepository;
import com.springboot.hospitalmanagement.service.AdminService;
import com.springboot.hospitalmanagement.service.NurseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private NurseRepository nurseRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public String assignNurseToDoctor(long doctorId, long nurseId) {
        Nurse nurse = nurseRepository.findById(nurseId).get();
        Doctor doctor = doctorRepository.findById(doctorId).get();

        Set<Nurse> nursesSet = doctor.getNurses();
        nursesSet.add(nurse);

        doctorRepository.save(doctor);
        nurse.setAllocated(true);
        nurse.setDoctor(doctor);
        nurseRepository.save(nurse);

        return "Nurse assigned Successfully";

    }

    @Override
    public String assignDoctorToNurse(long nurseId, long doctorId) {
        Nurse nurse = nurseRepository.findById(nurseId).get();
        Doctor doctor = doctorRepository.findById(doctorId).get();

        nurse.setDoctor(doctor);
        nurse.setAllocated(true);
        nurseRepository.save(nurse);

        return "Doctor assigned to nurse successfully";

    }
}
