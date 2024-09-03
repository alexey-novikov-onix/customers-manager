package com.onix.customers.requests;

import com.onix.customers.enumerations.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequest {

    @NotBlank
    @Size(max = 100)
    private String name;
    @NotNull
    @Positive
    private Integer age;
    @NotNull
    private LocalDate dateOfBirth;
    @Size(max = 200)
    private String address;
    private Gender gender;

}
