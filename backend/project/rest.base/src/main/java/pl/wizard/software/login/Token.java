package pl.wizard.software.login;

import lombok.Getter;

import java.time.LocalTime;
import java.util.Objects;
import java.util.UUID;

@Getter
public class Token {

    private final String token;
    private final Long accountID;
    private final LocalTime expiredTime;

    public Token(Long aAccountId) {
        this(aAccountId, LocalTime.now().plusMinutes(10));
    }

    public Token(Long aAccountId, LocalTime aLocalTime) {
        accountID = aAccountId;
        token = UUID.randomUUID().toString();
        expiredTime = aLocalTime;
    }

   boolean isNonExpired(Token aToken){
        LocalTime localTimeNow = LocalTime.now();
        LocalTime tokensExpirationTime = aToken.getExpiredTime();
        if(tokensExpirationTime.compareTo(localTimeNow) < 0){
            throw new IllegalArgumentException("Token is invalid");
        }
        return true;
    }

    @Override
    public boolean equals(Object aO) {
        if (this == aO) return true;
        if (aO == null || getClass() != aO.getClass()) return false;
        Token token = (Token) aO;
        return Objects.equals(accountID, token.accountID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountID);
    }
}
