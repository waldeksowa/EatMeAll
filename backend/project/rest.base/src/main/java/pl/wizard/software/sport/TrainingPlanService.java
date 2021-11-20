package pl.wizard.software.sport;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.wizard.software.diet.members.MemberDao;
import pl.wizard.software.diet.members.MemberEntity;
import pl.wizard.software.dto.CreateTrainingItemDto;
import pl.wizard.software.dto.CreateTrainingPlanDto;
import pl.wizard.software.dto.TrainingPlanDto;
import pl.wizard.software.dto.TrainingPlanItemDto;
import pl.wizard.software.exception.InvalidRequestBodyException;
import pl.wizard.software.mapper.TrainingPlanDtoMapper;
import pl.wizard.software.sport.trainings.TrainingDao;
import pl.wizard.software.sport.trainings.TrainingEntity;
import pl.wizard.software.sport.trainings.TrainingPlanDao;
import pl.wizard.software.sport.trainings.TrainingPlanEntity;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Stream;

import static pl.wizard.software.sport.trainings.TrainingEntity.TrainingType.AS_MANY_REPS_AS_POSSIBLE;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrainingPlanService {

    private final TrainingPlanDao trainingPlanRepository;
    private final TrainingDao trainingRepository;
    private final MemberDao memberRepository;

    public TrainingPlanEntity save(TrainingPlanEntity trainingPlanEntity) {
        return trainingPlanRepository.save(trainingPlanEntity);
    }

    @Transactional
    public TrainingPlanEntity create(CreateTrainingPlanDto createTrainingPlanDto) {
        LocalDate firstTrainingDate = createTrainingPlanDto.getTrainings().get(0).getTrainingDate();
        Stream<Month> months = createTrainingPlanDto.getTrainings().stream()
                .map(createTraining -> createTraining.getTrainingDate().getMonth());
        checkTrainingsMonth(months);

        List<TrainingPlanItemDto> trainingPlanItemDtos = new ArrayList<>();
        for (CreateTrainingItemDto training : createTrainingPlanDto.getTrainings()) {
            TrainingEntity trainingEntity = trainingRepository.findById(training.getTrainingId())
                    .orElseThrow(() -> new NoSuchElementException("Could not find training with id " + training.getTrainingId()));
            TrainingPlanItemDto trainingPlanItemDto = TrainingPlanItemDto.builder()
                    .trainingId(training.getTrainingId())
                    .trainingDate(training.getTrainingDate())
                    .trainingName(trainingEntity.getName())
                    .trainingType(trainingEntity.getTrainingType())
                    .repetitionResult(trainingEntity.getRepetitionResult())
                    .timeWeightResult(trainingEntity.getTimeWeightResult())
                    .trainingRating(trainingEntity.getTrainingRating())
                    .build();
            trainingPlanItemDtos.add(trainingPlanItemDto);
        }

        MemberEntity memberEntity = memberRepository.findById(createTrainingPlanDto.getMemberId())
                .orElseThrow(() -> new NoSuchElementException("Could not find member with id " + createTrainingPlanDto.getMemberId()));
        TrainingPlanEntity trainingPlanEntity = TrainingPlanEntity.builder()
                .trainingPlanDate(firstTrainingDate.withDayOfMonth(1))
                .member(memberEntity)
                .trainings(TrainingPlanDtoMapper.convertToBytes(trainingPlanItemDtos))
                .build();

        return save(trainingPlanEntity);
    }

    @Transactional
    public TrainingPlanEntity update(TrainingPlanDto training, Long id) {
        TrainingPlanEntity trainingPlanEntity = trainingPlanRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Could not find training plan with id " + id));
        Stream<Month> months = training.getTrainings().stream()
                .map(trainingPlanItemDto -> trainingPlanItemDto.getTrainingDate().getMonth());
        checkTrainingsMonth(months);

        List<TrainingPlanItemDto> trainingPlanItemDtos = new ArrayList<>();
        for (TrainingPlanItemDto trainingPlanItemDto : training.getTrainings()) {
            checkResultType(trainingPlanItemDto);
            TrainingEntity trainingEntity = trainingRepository.findById(trainingPlanItemDto.getTrainingId())
                    .orElseThrow(() -> new NoSuchElementException("Could not find training with id " + trainingPlanItemDto.getTrainingId()));
            TrainingPlanItemDto trainingPlanItem = TrainingPlanItemDto.builder()
                    .trainingId(trainingPlanItemDto.getTrainingId())
                    .trainingDate(trainingPlanItemDto.getTrainingDate())
                    .trainingName(trainingEntity.getName())
                    .trainingType(trainingEntity.getTrainingType())
                    .repetitionResult(trainingPlanItemDto.getRepetitionResult())
                    .timeWeightResult(trainingPlanItemDto.getTimeWeightResult())
                    .trainingRating(trainingPlanItemDto.getTrainingRating())
                    .build();
            trainingPlanItemDtos.add(trainingPlanItem);
        }

        MemberEntity memberEntity = memberRepository.findById(training.getMemberId())
                .orElseThrow(() -> new NoSuchElementException("Could not find member with id " + training.getMemberId()));
        trainingPlanEntity.setMember(memberEntity);
        trainingPlanEntity.setTrainingPlanDate(training.getTrainingPlanDate().withDayOfMonth(1));
        trainingPlanEntity.setTrainings(TrainingPlanDtoMapper.convertToBytes(trainingPlanItemDtos));

        return save(trainingPlanEntity);
    }

    @Transactional
    public List<TrainingPlanEntity> findAllByMember(Long accountId, Long memberId) {
        return trainingPlanRepository.findAllByMember(accountId, memberId);
    }

    @Transactional
    public Optional<TrainingPlanEntity> findCurrent(Long accountId, Long memberId) {
        LocalDate currentMonth = LocalDate.now().withDayOfMonth(1);
        Pageable topOne = PageRequest.of(0, 1);
        return trainingPlanRepository.findByTrainingDate(accountId, memberId, currentMonth, topOne).stream().findFirst();
    }

    @Transactional
    public Optional<TrainingPlanEntity> findNext(Long accountId, Long memberId) {
        LocalDate nextMonth = LocalDate.now().plusMonths(1).withDayOfMonth(1);
        Pageable topOne = PageRequest.of(0, 1);
        return trainingPlanRepository.findByTrainingDate(accountId, memberId, nextMonth, topOne).stream().findFirst();
    }

    @Transactional
    public Optional<TrainingPlanEntity> findByIdAndMember(Long accountId, Long memberId, Long trainingPlanId) {
        return trainingPlanRepository.findByIdAndMember(accountId, memberId, trainingPlanId).stream().findFirst();
    }

    @Transactional
    public Optional<TrainingPlanEntity> findById(Long id) {
        return trainingPlanRepository.findById(id);
    }

    @Transactional
    public void delete(Long id) {
        TrainingPlanEntity trainingPlanEntity = trainingPlanRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Could not find training plan with id " + id));
        trainingPlanRepository.deleteById(id);
    }

    private void checkTrainingsMonth(Stream<Month> months) {
        Long numberOfDifferentMonth = months
                .distinct()
                .count();
        if (numberOfDifferentMonth > 1) {
            throw new IllegalArgumentException("All trainings should belong to the same month");
        }
    }

    private void checkResultType(TrainingPlanItemDto trainingPlanItemDto) {
        if ((trainingPlanItemDto.getTrainingType() == AS_MANY_REPS_AS_POSSIBLE && trainingPlanItemDto.getTimeWeightResult() != null)
                || (trainingPlanItemDto.getTrainingType() != AS_MANY_REPS_AS_POSSIBLE && trainingPlanItemDto.getRepetitionResult() != null)) {
            throw new InvalidRequestBodyException("Incompatible training result and training type for training with id " + trainingPlanItemDto.getTrainingId());
        }
    }
}
