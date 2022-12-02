package com.springboot.hospitalmanagement.service;

import com.springboot.hospitalmanagement.payload.ForgotPasswordDto;

public interface PasswordService {
    int forgotPassword(ForgotPasswordDto forgotPasswordDto);
}
