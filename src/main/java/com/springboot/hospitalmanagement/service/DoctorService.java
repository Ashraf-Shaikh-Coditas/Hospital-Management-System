package com.springboot.hospitalmanagement.service;

import com.springboot.hospitalmanagement.entity.Doctor;
import com.springboot.hospitalmanagement.payload.DoctorDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface DoctorService {

    DoctorDto addDoctor(DoctorDto doctorDto);
    Doctor mapToEntity(DoctorDto doctorDto);
    DoctorDto mapToDto(Doctor doctor);

    DoctorDto updateDoctor(DoctorDto doctorDto,Long doctorId);

    void deleteDoctor(Long doctorId);

    List<DoctorDto> getAllDoctors();
}
