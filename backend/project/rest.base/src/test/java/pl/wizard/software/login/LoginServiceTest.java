package pl.wizard.software.login;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalTime;
import java.util.HashSet;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginServiceTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void shouldFindAccountIdByTokenAfterLogin(){
        AccountDao accountDao = Mockito.mock(AccountDao.class);
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setId(2L);
        accountEntity.setUsername("user");
        accountEntity.setPassword("pass");
        when(accountDao.findByUsername("user")).thenReturn(accountEntity);
        TokenFactory tokenFactory = Mockito.mock(TokenFactory.class);
        when(tokenFactory.createToken(anyLong())).thenReturn(new Token(2L, LocalTime.now().plusMinutes(15)));
        LoginService service = new LoginService(accountDao, new HashSet<>(), tokenFactory);

        Credencials credencials = new Credencials();
        credencials.setUsername("user");
        credencials.setPassword("pass");
        Token token = service.login(credencials);
        assertNotNull(token);

        assertTrue(service.getAccountByIdToken(token).isPresent());
    }

    @Test(expected =  IllegalArgumentException.class)
    public void shouldThrowTrueAfterIncorrectUsername(){
        AccountDao accountDao = Mockito.mock(AccountDao.class);
        AccountEntity testAcc = new AccountEntity();
        testAcc.setId(2L);
        testAcc.setPassword("pass");
        lenient().when(accountDao.findByUsername("user")).thenReturn(testAcc);
        TokenFactory tokenFactory = Mockito.mock(TokenFactory.class);
        lenient().when(tokenFactory.createToken(anyLong())).thenReturn(new Token(2L, LocalTime.now().plusMinutes(15)));
        LoginService loginService = new LoginService(accountDao, new HashSet<>(), tokenFactory);

        Credencials credencials = new Credencials();
        credencials.setUsername("INCORRECT_USER");
        credencials.setPassword("pass");
        Token token = loginService.login(credencials);
        assertNotNull(token);

        exception.expect(IllegalArgumentException.class);
    }

    @Test(expected =  IllegalArgumentException.class)
    public void shouldThrowTrueAfterIncorrectPassword(){
        AccountDao accountDao = Mockito.mock(AccountDao.class);
        AccountEntity testAcc = new AccountEntity();
        testAcc.setId(2L);
        testAcc.setUsername("user");
        testAcc.setPassword("pass");
        when(accountDao.findByUsername("user")).thenReturn(testAcc);
        TokenFactory tokenFactory = Mockito.mock(TokenFactory.class);
        lenient().when(tokenFactory.createToken(anyLong())).thenReturn(new Token(2L, LocalTime.now().plusMinutes(15)));
        LoginService loginService = new LoginService(accountDao, new HashSet<>(), tokenFactory);

        Credencials credencials = new Credencials();
        credencials.setUsername("user");
        credencials.setPassword("INCORRECT_PASSWORD");
        Token token = loginService.login(credencials);
        assertNotNull(token);

        exception.expect(IllegalArgumentException.class);
    }

    @Test(expected =  IllegalArgumentException.class)
    public void shouldThrowTrueAfterExpiredToken(){
        AccountDao accountDao = Mockito.mock(AccountDao.class);
        AccountEntity testAcc = new AccountEntity();
        testAcc.setId(2L);
        testAcc.setUsername("user");
        testAcc.setPassword("pass");
        when(accountDao.findByUsername("user")).thenReturn(testAcc);
        TokenFactory tokenFactory = Mockito.mock(TokenFactory.class);
        lenient().when(tokenFactory.createToken(anyLong())).thenReturn(new Token(2L, LocalTime.now()));
        LoginService loginService = new LoginService(accountDao, new HashSet<>(), tokenFactory);

        Credencials credencials = new Credencials();
        credencials.setUsername("user");
        credencials.setPassword("password");
        Token token = loginService.login(credencials);
        assertNotNull(token);

        exception.expect(IllegalArgumentException.class);
    }

}