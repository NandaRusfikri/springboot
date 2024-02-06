package com.nanda.springboot.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {
    @NotBlank
    @Size(max = 100)
    private String Name;

    @Size(max = 100)
    private String Password;

    @Size(max = 100)
    private String Email;
}
