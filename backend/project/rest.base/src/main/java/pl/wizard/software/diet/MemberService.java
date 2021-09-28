package pl.wizard.software.diet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.wizard.software.diet.members.MemberDao;
import pl.wizard.software.diet.members.MemberEntity;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {

    private final MemberDao memberRepository;

    @Transactional
    public List<MemberEntity> findAllMembers(Long accountId) {
        return memberRepository.findAllMembers(accountId);
    }

    @Transactional
    public Optional<MemberEntity> findById(Long id) {
        return memberRepository.findById(id);
    }

    @Transactional
    public Optional<MemberEntity> findMemberById(Long accountId, Long memberId) {
        return memberRepository.findMemberById(accountId, memberId);
    }

    public MemberEntity save(MemberEntity stock) {
        return memberRepository.save(stock);
    }

    public void deleteById(Long id) {
        memberRepository.deleteById(id);
    }
}
