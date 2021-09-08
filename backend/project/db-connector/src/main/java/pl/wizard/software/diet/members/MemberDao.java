package pl.wizard.software.diet.members;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MemberDao extends JpaRepository<MemberEntity, Long> {

    @Query("select m from MemberEntity m where m.accountId = ?1")
    List<MemberEntity> findAllForAccount(Long accountId);

    @Query("select m from MemberEntity m where m.accountId = ?1 and m.id = ?2")
    Optional<MemberEntity> findByIdForAccount(Long accountId, Long memberId);
}
