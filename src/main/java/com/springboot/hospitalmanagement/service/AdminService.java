package com.springboot.hospitalmanagement.service;

public interface AdminService {
    String assignNurseToDoctor(long doctorId, long newNurseId);

    String assignDoctorToNurse(long nurseId, long doctorId);
}
