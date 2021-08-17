package pl.wizard.software.login;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import pl.wizard.software.AbstractBaseEntity;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "ACCOUNTS")
@Data
@Getter
@Setter
@NoArgsConstructor
public class AccountEntity extends AbstractBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    @Version
    private int version;

    private String username;
    private String password;
    private String email;

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
