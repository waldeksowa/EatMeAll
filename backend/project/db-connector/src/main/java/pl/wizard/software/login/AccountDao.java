package pl.wizard.software.login;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface AccountDao extends JpaRepository<Account, Long> {

    @Query("SELECT a FROM Account a WHERE a.username = ?1")
    Account findByUsername(String username);
}
