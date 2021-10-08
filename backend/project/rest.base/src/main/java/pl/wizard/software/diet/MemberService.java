package pl.wizard.software.diet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.wizard.software.diet.dto.MemberDto;
import pl.wizard.software.diet.dto.MemberWithoutScheduleAndProdsDto;
import pl.wizard.software.diet.mapper.MemberDtoMapper;
import pl.wizard.software.diet.members.MemberDao;
import pl.wizard.software.diet.members.MemberEntity;
import pl.wizard.software.diet.schedules.ScheduleDao;
import pl.wizard.software.diet.schedules.ScheduleEntity;

import javax.persistence.Tuple;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {

    private final MemberDao memberRepository;
    private final ScheduleDao scheduleRepository;

    public List<MemberWithoutScheduleAndProdsDto> findAllMembers(Long accountId) {
        List<Tuple> members = memberRepository.findAllMembers(accountId);
        return MemberDtoMapper.mapToMemberWithoutScheduleAndProdsDtos(members);
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
            Pageable topOne = PageRequest.of(0, 1);
            Optional<ScheduleEntity> scheduleEntity = scheduleRepository.findByMember(accountId, memberId, topOne).stream().findFirst();
            return Optional.of(MemberDtoMapper.mapToMemberDto(member.get(), scheduleEntity));
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
