package pl.wizard.software.login;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginService{

    private final AccountDao accountDao;

    public Optional<Account> findByID(Long Id){
        return accountDao.findById(Id);
    }

    Token login(Account aUser) {
        Account account = accountDao.findByUsername(aUser.getUsername());
        if(
            account.getPassword().equals(aUser.getPassword())
        ){
            return new Token(account.getId());
        } else {
            throw new IllegalArgumentException();
        }
    }
    //stworzyć hashmape, Hashmapa ma zwracać czy token już był
}
