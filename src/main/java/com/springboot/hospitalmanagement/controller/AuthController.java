package com.springboot.hospitalmanagement.controller;

import com.springboot.hospitalmanagement.entity.Role;
import com.springboot.hospitalmanagement.entity.User;
import com.springboot.hospitalmanagement.payload.JWTAuthResponse;
import com.springboot.hospitalmanagement.payload.LoginDto;
import com.springboot.hospitalmanagement.payload.SignUpDto;
import com.springboot.hospitalmanagement.repository.RoleRepository;
import com.springboot.hospitalmanagement.repository.UserRepository;
import com.springboot.hospitalmanagement.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<JWTAuthResponse> autheticateUser(@RequestBody LoginDto loginDto) {
        System.out.println(loginDto.getUsernameOrEmail());
        System.out.println(loginDto.getPassword());
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(),
                loginDto.getPassword()));
        System.out.println("1");

        SecurityContextHolder.getContext().setAuthentication(authentication);
        System.out.println("2");

        // get token from tokenprovider

        String token = tokenProvider.generateToken(authentication);
        System.out.println("3");

//        return new ResponseEntity<>("User signed in successsfully", HttpStatus.OK);
        return ResponseEntity.ok(new JWTAuthResponse(token,
                tokenProvider.getRoleFromUsername(tokenProvider.getUsernameFromJWT(token)
                ), tokenProvider.getIdFromUsername(tokenProvider.getUsernameFromJWT(token))));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto) {
        // add a check for username exists in a db
        if(userRepository.existsByUsername(signUpDto.getUsername())) {
            return new ResponseEntity<>("Username is already taken",HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByUsername(signUpDto.getEmail())) {
            return new ResponseEntity<>("Email is already taken",HttpStatus.BAD_REQUEST);
        }

        System.out.println(signUpDto);

        // create user Object
        User user = new User();
        user.setUsername(signUpDto.getUsername());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

        Role roles = roleRepository.findByRoleName("ROLE_ADMIN").get();
        user.setRoles(Collections.singleton(roles));

        userRepository.save(user);

        return new ResponseEntity<>("User Registered Successfully",HttpStatus.OK);
    }

//    @PostMapping("/forgotPassword")
//    public ResponseEntity<String> forgotPassword() {
//        return null;
//    }
//
//    @PostMapping("/changePassword/")
//    public ResponseEntity<String> changePassword() {
//
//    }
}
