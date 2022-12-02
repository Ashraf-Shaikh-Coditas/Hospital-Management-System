package com.springboot.hospitalmanagement.payload;

import com.springboot.hospitalmanagement.util.RandomPasswordGenerator;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Set;
import java.util.UUID;

@Data
public class DoctorDto {
    private Long doctorId;
    @NotEmpty

    private String doctorName;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String specialization;

    private String password;

    private Set<NurseDto> nurses;



    public String show() {
        return "Doctor Id : "+doctorId+
                "\nDoctor Name : "+doctorName+
                "\nDoctor Email : "+email+
                "\nSpecialization : "+specialization+
                "\n"+
                "YOUR PASSWORD IS :"+password;
    }


}
