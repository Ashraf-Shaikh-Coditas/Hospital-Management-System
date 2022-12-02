package com.springboot.hospitalmanagement.service.impl;

import com.springboot.hospitalmanagement.entity.Doctor;
import com.springboot.hospitalmanagement.entity.Nurse;
import com.springboot.hospitalmanagement.entity.Role;
import com.springboot.hospitalmanagement.entity.User;
import com.springboot.hospitalmanagement.payload.NurseDto;
import com.springboot.hospitalmanagement.repository.DoctorRepository;
import com.springboot.hospitalmanagement.repository.NurseRepository;
import com.springboot.hospitalmanagement.repository.RoleRepository;
import com.springboot.hospitalmanagement.repository.UserRepository;
import com.springboot.hospitalmanagement.service.DoctorService;
import com.springboot.hospitalmanagement.service.NurseService;
import com.springboot.hospitalmanagement.util.PasswordEncoderProvider;
import com.springboot.hospitalmanagement.util.RandomPasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NurseServiceImpl implements NurseService {

    @Autowired
    private NurseRepository nurseRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private DoctorService doctorService;

    @Override
    public NurseDto addNurse(NurseDto nurseDto) {
        String password = RandomPasswordGenerator.passwordGenerator();
        nurseDto.setPassword(password);
        Nurse nurse = mapToEntity(nurseDto);
        nurse.setAllocated(false);
        Nurse addedNurse = nurseRepository.save(nurse);
        NurseDto newNurseDto = mapToDto(addedNurse);
        newNurseDto.setPassword(password);
        newNurseDto.setAllocated(false);
        saveUserCredentials(newNurseDto);
        return newNurseDto;
    }

    @Override
    public NurseDto updateNurse(NurseDto nurseDto, Long nurseId) {
        Nurse nurse = nurseRepository.findById(nurseId).get();
        nurse.setNurseName(nurseDto.getNurseName());
        nurseRepository.save(nurse);
        return mapToDto(nurse);

    }

    @Override
    public void deleteNurse(Long nurseId) {
        Nurse nurse = nurseRepository.findById(nurseId).get();
        nurse.setDeleted(true);
        nurseRepository.save(nurse);

    }

    @Override
    public List<NurseDto> findAllocatedNurses() {
        List<Nurse> nurses = nurseRepository.findByIsAllocatedTrueAndIsDeletedFalse();

        return nurses.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<NurseDto> findUnAllocatedNurses() {
        List<Nurse> nurses = nurseRepository.findByIsAllocatedFalseAndIsDeletedFalse();

        return nurses.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public List<NurseDto> getAllNurses() {
        List<Nurse> nurses = nurseRepository.findAll();
        return nurses.stream().map(this::mapToDto).collect(Collectors.toList());
    }


    private Nurse mapToEntity(NurseDto nurseDto) {
        Nurse nurse = new Nurse();
        nurse.setNurseName(nurseDto.getNurseName());
        nurse.setEmail(nurseDto.getEmail());
        return nurse;
    }

    private NurseDto mapToDto(Nurse nurse) {
        NurseDto nurseDto = new NurseDto();
        nurseDto.setNurseId(nurse.getNurseId());
        nurseDto.setNurseName(nurse.getNurseName());
        nurseDto.setEmail(nurse.getEmail().trim());
//        nurseDto.setDoctorDto(doctorService.mapToDto(nurse.getDoctor()));
        return nurseDto;

    }

    private void saveUserCredentials(NurseDto nurseDto) {
        User user = new User();
        user.setUsername(nurseDto.getEmail().trim());
        user.setPassword(PasswordEncoderProvider.passwordEncoding(nurseDto.getPassword().trim()));
        Role role = roleRepository.findByRoleName("ROLE_NURSE").get();
        user.setRoles(Collections.singleton(role));
        userRepository.save(user);
    }
}
