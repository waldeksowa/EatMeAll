package pl.wizard.software.login;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface AccountDao extends JpaRepository<AccountEntity, Long> {

//    @Query("SELECT a FROM AccountEntity a WHERE a.username = ?1")
    AccountEntity findByUsername(String username);

    @Query("SELECT a FROM AccountEntity a WHERE a.email = ?1")
    AccountEntity findByEmail(String email);
}
