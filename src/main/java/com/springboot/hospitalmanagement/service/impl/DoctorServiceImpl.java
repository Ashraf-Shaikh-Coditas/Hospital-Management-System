package com.springboot.hospitalmanagement.service.impl;

import com.springboot.hospitalmanagement.entity.Doctor;
import com.springboot.hospitalmanagement.entity.Role;
import com.springboot.hospitalmanagement.entity.User;
import com.springboot.hospitalmanagement.payload.DoctorDto;
import com.springboot.hospitalmanagement.repository.DoctorRepository;
import com.springboot.hospitalmanagement.repository.RoleRepository;
import com.springboot.hospitalmanagement.repository.UserRepository;
import com.springboot.hospitalmanagement.service.DoctorService;
import com.springboot.hospitalmanagement.util.PasswordEncoderProvider;
import com.springboot.hospitalmanagement.util.RandomPasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    @Override
    public DoctorDto addDoctor(DoctorDto doctorDto) {
        doctorDto.setPassword(RandomPasswordGenerator.passwordGenerator());
        Doctor doctor = mapToEntity(doctorDto);
        Doctor newDoctor = doctorRepository.save(doctor);
        saveUserCredentials(doctorDto);
        DoctorDto newDoctorDto = mapToDto(newDoctor);
        newDoctorDto.setPassword(doctorDto.getPassword());
        return newDoctorDto;
    }

    public Doctor mapToEntity(DoctorDto doctorDto) {
        Doctor doctor = new Doctor();
        doctor.setDoctorName(doctorDto.getDoctorName());
        doctor.setEmail(doctorDto.getEmail().trim());
        doctor.setSpecialization(doctorDto.getSpecialization());
        return doctor;
    }

    public DoctorDto mapToDto(Doctor doctor) {
        DoctorDto doctorDto = new DoctorDto();
        doctorDto.setDoctorId(doctor.getDoctorId());
        doctorDto.setDoctorName(doctor.getDoctorName());
        doctorDto.setEmail(doctor.getEmail());
        doctorDto.setSpecialization(doctor.getSpecialization());
        return doctorDto;
    }

    @Override
    public DoctorDto updateDoctor(DoctorDto doctorDto, Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId).get();
        doctor.setDoctorName(doctorDto.getDoctorName());
        doctor.setSpecialization(doctorDto.getSpecialization());
        doctorRepository.save(doctor);
        return mapToDto(doctor);

    }

    @Override
    public void deleteDoctor(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId).get();
        doctor.setDeleted(true);
        doctorRepository.save(doctor);

    }

    @Override
    public List<DoctorDto> getAllDoctors() {
        List<Doctor> doctors = doctorRepository.findByIsDeletedFalse();
        return doctors.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    private void saveUserCredentials(DoctorDto doctorDto) {
        User user = new User();
        user.setUsername(doctorDto.getEmail().trim());
        user.setPassword(PasswordEncoderProvider.passwordEncoding(doctorDto.getPassword().trim()));
        Role role = roleRepository.findByRoleName("ROLE_DOCTOR").get();
        user.setRoles(Collections.singleton(role));
        userRepository.save(user);
    }
}
