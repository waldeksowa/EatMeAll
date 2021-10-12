package pl.wizard.software.register.dto;

import lombok.Value;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Value
public class AccountDto {

    @NotNull
    @NotEmpty
    String email;

    @NotNull
    @NotEmpty
    String password;

    @NotNull
    @NotEmpty
    String matchingPassword;

}
