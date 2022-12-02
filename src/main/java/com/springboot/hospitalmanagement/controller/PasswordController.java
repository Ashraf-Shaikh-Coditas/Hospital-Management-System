package com.springboot.hospitalmanagement.controller;

import com.springboot.hospitalmanagement.payload.ForgotPasswordDto;
import com.springboot.hospitalmanagement.service.PasswordService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/credentials")
public class PasswordController {

    @Autowired
    private PasswordService passwordService;


    @PostMapping("/forgotPassword")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordDto forgotPasswordDto) {
        int status = passwordService.forgotPassword(forgotPasswordDto);
        if (status > 0)
            return new ResponseEntity<>("Password Changed Successfully", HttpStatus.OK);
        else
            return new ResponseEntity<>("User Not Exists", HttpStatus.BAD_REQUEST);

    }
}
