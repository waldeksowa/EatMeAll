package pl.wizard.software.register;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import pl.wizard.software.login.AccountDao;
import pl.wizard.software.login.AccountEntity;
import pl.wizard.software.register.dto.AccountDto;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

    @Mock
    AccountDao mockDao;

    @Test
    public void shouldCreateNewAccount(){
        AccountService accountService = new AccountService(mockDao);
        AccountDto accountDto = new AccountDto("user@gmail.com","password123","password123");

        accountService.createAccount(accountDto);

        Mockito.lenient().when(mockDao.findByEmail(accountDto.getEmail())).thenReturn(new AccountEntity());

        verify(mockDao, times(1)).findByEmail(accountDto.getEmail());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionAfterIncorrectPasswords(){
        AccountService accountService = new AccountService(mockDao);
        AccountDto accountDto = new AccountDto("user1@gmail.com","password12","password123");

        accountService.createAccount(accountDto);

    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionAfterAccountFoundInRepository(){
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setEmail("usergmail.com");
        Mockito.when(mockDao.findByEmail(anyString())).thenReturn(accountEntity);

        AccountService accountService = new AccountService(mockDao);
        AccountDto accountDto1 = new AccountDto("user@gmail.com","password123","password123");

        accountService.createAccount(accountDto1);

    }
}