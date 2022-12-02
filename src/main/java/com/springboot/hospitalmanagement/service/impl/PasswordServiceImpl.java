package com.springboot.hospitalmanagement.service.impl;

import com.springboot.hospitalmanagement.entity.User;
import com.springboot.hospitalmanagement.payload.ForgotPasswordDto;
import com.springboot.hospitalmanagement.repository.UserRepository;
import com.springboot.hospitalmanagement.service.PasswordService;
import com.springboot.hospitalmanagement.util.RandomPasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordServiceImpl implements PasswordService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailSenderService emailSenderService;


    PasswordEncoder passwordEncoder;


    @Override
    public int forgotPassword(ForgotPasswordDto forgotPasswordDto) {
        if (userRepository.existsByUsername(forgotPasswordDto.getEmail().trim())) {
            User user = userRepository.findByUsername(forgotPasswordDto.getEmail()).get();

            String newPassword = RandomPasswordGenerator.passwordGenerator();
            user.setPassword(passwordEncoder.encode(newPassword.trim()));

            emailSenderService.sendSimpleEmail(user.getUsername(),
                    "Hi user "+user.getUsername()+
                            "\nYour new password is : "+newPassword,"PASSWORD CHANGED SUCCESSFULLY");
            return 1;
        } else {
            System.out.println("User not exists.");
            return 0;
        }

    }
}
