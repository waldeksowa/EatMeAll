package pl.wizard.software.login;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService{

    private final AccountDao accountDao;

    private final HashSet<Token> tokens;

    private final TokenFactory tokenFactory;


    public Optional<AccountEntity> findByID(Long Id){
        return accountDao.findById(Id);
    }

    HashSet<Token> getTokens() {
        return tokens;
    }

    public Optional<Long> getAccountByIdToken(Token aToken){
        if(tokens.contains(aToken) && aToken.isNonExpired(aToken)){
         return Optional.of(aToken.getAccountID());
        }
            return Optional.empty();
    }

    public Optional<Long> getAccountIdByTokenUUID(String token){
        String aUUID = token.substring(7);
        Optional<Token> accountToken = tokens.stream()
                .filter(t -> t.getToken().equals(aUUID))
                .findFirst();
        if (accountToken.isPresent()) {
            Token account = accountToken.get();
            if (account.isNonExpired(account)) {
                return Optional.of(account.getAccountID());
            }
            return Optional.empty();
        }
        return Optional.empty();
    }

    Token login(Credencials aUser) {
        AccountEntity account = accountDao.findByUsername(aUser.getUsername());
        if(account == null){
            account = accountDao.findByEmail(aUser.getEmail());
        }
        if(account != null && account.getPassword().equals(aUser.getPassword())){
            Token loginToken = tokenFactory.createToken(account.getId());

            tokens.remove(loginToken);
            tokens.add(loginToken);

            return loginToken;
        } else {
            throw new IllegalArgumentException();
        }
    }

}
