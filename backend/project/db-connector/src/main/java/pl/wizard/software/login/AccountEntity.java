package pl.wizard.software.login;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.wizard.software.AbstractBaseEntity;
import pl.wizard.software.diet.members.MemberEntity;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "ACCOUNTS")
@Data
@Getter
@Setter
@NoArgsConstructor
public class AccountEntity extends AbstractBaseEntity {

    private String username;
    private String password;
    private String email;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "account_id")
    private Set<MemberEntity> members;

    @Override
    public boolean equals(Object aO) {
        if (this == aO) return true;
        if (aO == null || getClass() != aO.getClass()) return false;
        AccountEntity that = (AccountEntity) aO;
        return Objects.equals(username, that.username) &&
                Objects.equals(password, that.password) &&
                Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, email);
    }
}
