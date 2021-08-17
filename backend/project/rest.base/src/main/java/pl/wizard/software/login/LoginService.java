package pl.wizard.software.login;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService{

    private final AccountDao accountDao;

    private final HashSet<String> tokens;

    public Optional<AccountEntity> findByID(Long Id){
        return accountDao.findById(Id);
    }

    HashSet<String> getTokens() {
        return tokens;
    }

    public Optional<Long> getAccountByIdToken(Token aToken){
        if(tokens.contains(aToken)){
         return Optional.of(aToken.getAccountID());
        }
            return Optional.empty();
    }

    Token login(AccountEntity aUser) {
        AccountEntity account = accountDao.findByUsername(aUser.getUsername());
        if(account.getPassword().equals(aUser.getPassword())){
            Token loginToken = new Token(account.getId());
            tokens.add(loginToken.getToken());

            return loginToken;
        } else {
            throw new IllegalArgumentException();
        }
    }

}
