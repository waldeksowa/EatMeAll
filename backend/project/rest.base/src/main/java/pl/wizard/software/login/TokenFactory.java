package pl.wizard.software.login;

import org.springframework.stereotype.Service;

@Service
public class TokenFactory{

    public Token createToken(Long accountID){
        return new Token(accountID);
    }
}
