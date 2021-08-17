package pl.wizard.software.login;

import java.time.LocalTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class Token {

    private final String token;
    private final Long accountID;
    private final LocalTime expiredTime;

    public Token(Long aAccountId) {
        accountID = aAccountId;
        token = UUID.randomUUID().toString();
        expiredTime = LocalTime.now().plusMinutes(10);
    }

    String getToken() {
        return token;
    }

    Long getAccountID() {
        return accountID;
    }

    LocalTime getExpiredTime() {
        return expiredTime;
    }

    boolean isNonExpired(Token aToken){
        LocalTime LocalTimeNow = LocalTime.now();
        if((LocalTimeNow.compareTo(aToken.getExpiredTime())) < 0){
            throw new IllegalArgumentException("Token is invalid");
        }
        return true;
    }

    @Override
    public boolean equals(Object aO) {
        if (this == aO) return true;
        if (aO == null || getClass() != aO.getClass()) return false;
        Token token1 = (Token) aO;
        return Objects.equals(token, token1.token) && Objects.equals(accountID, token1.accountID) && Objects.equals(expiredTime, token1.expiredTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, accountID, expiredTime);
    }
}
