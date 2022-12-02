package com.springboot.hospitalmanagement.service;

import com.springboot.hospitalmanagement.payload.NurseDto;

import java.util.List;

public interface NurseService {
    NurseDto addNurse(NurseDto nurseDto);

    NurseDto updateNurse(NurseDto nurseDto, Long nurseId);

    void deleteNurse(Long nurseId);

    List<NurseDto> findAllocatedNurses();

    List<NurseDto> findUnAllocatedNurses();

    List<NurseDto> getAllNurses();
}
