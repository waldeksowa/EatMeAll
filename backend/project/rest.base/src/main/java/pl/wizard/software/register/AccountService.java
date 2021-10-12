package pl.wizard.software.register;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.wizard.software.login.AccountDao;
import pl.wizard.software.login.AccountEntity;
import pl.wizard.software.register.dto.AccountDto;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class AccountService{

    private final AccountDao accountDao;

    private final String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}";

    public void createAccount(AccountDto aAccountDto) {
        if(emailExist(aAccountDto.getEmail())) {
            throw new IllegalArgumentException("Account with the given email exist!");
        }
        if(passwordsMatch(aAccountDto.getPassword(), aAccountDto.getMatchingPassword()) && isEmailValid(aAccountDto.getEmail())){
                AccountEntity accountEntity = new AccountEntity();
                accountEntity.setUsername(aAccountDto.getEmail());
                accountEntity.setEmail(aAccountDto.getEmail());
                accountEntity.setPassword(aAccountDto.getPassword());

                accountDao.save(accountEntity);
        } else {
            throw new IllegalArgumentException();
        }
    }

    boolean emailExist(String email){
        return accountDao.findByEmail(email) != null;
    }

    boolean passwordsMatch(String password1, String password2){
        return password1.equals(password2);
    }

    private boolean isEmailValid(String email){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }
}