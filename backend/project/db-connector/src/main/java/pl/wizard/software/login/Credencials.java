package pl.wizard.software.login;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Credencials {

    private String username;
    private String password;
    private String email;

}
