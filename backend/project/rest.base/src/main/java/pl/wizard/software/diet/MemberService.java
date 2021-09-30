package pl.wizard.software.diet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.wizard.software.diet.dto.MemberDto;
import pl.wizard.software.diet.mapper.MemberDtoMapper;
import pl.wizard.software.diet.members.MemberDao;
import pl.wizard.software.diet.members.MemberEntity;
import pl.wizard.software.diet.schedules.ScheduleDao;
import pl.wizard.software.diet.schedules.ScheduleEntity;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {

    private final MemberDao memberRepository;
    private final ScheduleDao scheduleRepository;

    @Transactional
    public List<MemberEntity> findAllMembers(Long accountId) {
        return memberRepository.findAllMembers(accountId);
    }

    @Transactional
    public Optional<MemberEntity> findById(Long id) {
        return memberRepository.findById(id);
    }

    @Transactional
    public Optional<MemberDto> findMemberById(Long accountId, Long memberId) {
        Optional<MemberEntity> member = memberRepository.findMemberById(accountId, memberId);
        if (!member.isPresent()) {
            return Optional.empty();
        } else {
            Optional<ScheduleEntity> scheduleEntity = scheduleRepository.findByMember(accountId, memberId);
            if (!scheduleEntity.isPresent()) {
                return Optional.empty();
            } else {
                return Optional.of(MemberDtoMapper.mapToMemberDto(member.get(), scheduleEntity.get()));
            }
        }
    }

    public MemberEntity save(MemberEntity stock) {
        return memberRepository.save(stock);
    }

    public void deleteById(Long id) {
        memberRepository.deleteById(id);
    }

    public void setAccountId(MemberEntity member, Long accountId) {
        member.setAccountId(accountId);
    }
}
