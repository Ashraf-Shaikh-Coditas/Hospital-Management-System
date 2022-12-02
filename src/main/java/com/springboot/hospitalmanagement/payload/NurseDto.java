package com.springboot.hospitalmanagement.payload;

import com.springboot.hospitalmanagement.util.RandomPasswordGenerator;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NurseDto {
    private Long nurseId;
    @NotEmpty
    private String nurseName;
    @NotEmpty
    @Email
    private String email;

    private boolean isAllocated;
    private DoctorDto doctorDto;

    private String password;


    public String show() {
        return "Nurse Id : "+nurseId+
                "\nNurse Name : "+nurseName+
                "\nNurse Email : "+email+
                "\nYOUR PASSWORD IS :"+password;
    }

}
