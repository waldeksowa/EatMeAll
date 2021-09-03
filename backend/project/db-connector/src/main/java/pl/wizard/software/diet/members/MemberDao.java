package pl.wizard.software.diet.members;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberDao extends JpaRepository<MemberEntity, Long> {
}
