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
import pl.wizard.software.sport.trainings.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrainingPlanService {

    private final TrainingPlanDao trainingPlanRepository;
    private final TrainingPlanItemDao trainingPlanItemRepository;
    private final TrainingDao trainingRepository;
    private final MemberDao memberRepository;


    public TrainingPlanEntity save(TrainingPlanEntity trainingPlanEntity) {
        return trainingPlanRepository.save(trainingPlanEntity);
    }

    public TrainingPlanEntity save(CreateTrainingPlanDto createTrainingPlanDto) {
        LocalDate firstTrainingDate = createTrainingPlanDto.getTrainings().get(0).getTrainingDate();
        Stream<Month> months = createTrainingPlanDto.getTrainings().stream()
                .map(createTraining -> createTraining.getTrainingDate().getMonth());
        checkTrainingsMonth(months);

        List<TrainingPlanItemEntity> trainingPlanItemEntities = new ArrayList<>();
        for (CreateTrainingItemDto training : createTrainingPlanDto.getTrainings()) {
            Optional<TrainingEntity> trainingEntity = trainingRepository.findById(training.getTrainingId());
            if (!trainingEntity.isPresent()) {
                log.error("Training with id " + training.getTrainingId() + "does not exists");
            } else {
                TrainingPlanItemEntity trainingPlanItemEntity = TrainingPlanItemEntity.builder()
                        .training(trainingEntity.get())
                        .trainingDate(training.getTrainingDate())
                        .build();
                trainingPlanItemEntities.add(trainingPlanItemEntity);
            }
        }

        MemberEntity memberEntity = memberRepository.findById(createTrainingPlanDto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("Member with id " + createTrainingPlanDto.getMemberId() + "does not exists"));
        TrainingPlanEntity trainingPlanEntity = TrainingPlanEntity.builder()
                .trainingPlanDate(firstTrainingDate.withDayOfMonth(1))
                .member(memberEntity)
                .trainings(trainingPlanItemEntities)
                .build();

        return save(trainingPlanEntity);
    }

    public TrainingPlanEntity update(TrainingPlanDto training, Long id) {
        Stream<Month> months = training.getTrainings().stream()
                .map(trainingPlanItemDto -> trainingPlanItemDto.getTrainingDate().getMonth());
        checkTrainingsMonth(months);

        List<TrainingPlanItemEntity> trainingPlanItemEntities = new ArrayList<>();
        for (TrainingPlanItemDto trainingPlanItemDto : training.getTrainings()) {
            Optional<TrainingEntity> trainingEntity = trainingRepository.findById(trainingPlanItemDto.getTrainingId());
            if (!trainingEntity.isPresent()) {
                log.error("Training with id " + trainingPlanItemDto.getTrainingId() + "does not exists");
            } else {
                if (trainingPlanItemDto.getTrainingItemId() != null) {
                    Optional<TrainingPlanItemEntity> trainingPlanItem = trainingPlanItemRepository.findById(trainingPlanItemDto.getTrainingItemId());
                    if (!trainingPlanItem.isPresent()) {
                        log.error("Training item with id " + trainingPlanItemDto.getTrainingItemId() + "does not exists");
                    } else {
                        TrainingPlanItemEntity trainingPlanItemEntity = trainingPlanItem.get();
                        trainingPlanItemEntity.setTraining(trainingEntity.get());
                        trainingPlanItemEntity.setTrainingDate(trainingPlanItemDto.getTrainingDate());
                        trainingPlanItemEntities.add(trainingPlanItemEntity);
                    }
                } else {
                    TrainingPlanItemEntity trainingPlanItemEntity = TrainingPlanItemEntity.builder()
                            .training(trainingEntity.get())
                            .trainingDate(trainingPlanItemDto.getTrainingDate())
                            .build();
                    trainingPlanItemEntities.add(trainingPlanItemEntity);
                }
            }
        }

        MemberEntity memberEntity = memberRepository.findById(training.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("Member with id " + training.getMemberId() + "does not exists"));
        TrainingPlanEntity trainingPlanEntity = trainingPlanRepository.findById(id).get();
        trainingPlanEntity.setMember(memberEntity);
        trainingPlanEntity.setTrainingPlanDate(training.getTrainingPlanDate().withDayOfMonth(1));
        trainingPlanEntity.setTrainings(trainingPlanItemEntities);

        return save(trainingPlanEntity);
    }

    public List<TrainingPlanEntity> findAllByMember(Long accountId, Long memeberId) {
        return trainingPlanRepository.findAllByMember(accountId, memeberId);
    }

    public Optional<TrainingPlanEntity> findCurrent(Long accountId, Long memberId) {
        LocalDate currentMonth = LocalDate.now().withDayOfMonth(1);
        Pageable topOne = PageRequest.of(0, 1);
        return trainingPlanRepository.findByTrainingDate(accountId, memberId, currentMonth, topOne).stream().findFirst();
    }

    public Optional<TrainingPlanEntity> findNext(Long accountId, Long memberId) {
        LocalDate nextMonth = LocalDate.now().plusMonths(1).withDayOfMonth(1);
        Pageable topOne = PageRequest.of(0, 1);
        return trainingPlanRepository.findByTrainingDate(accountId, memberId, nextMonth, topOne).stream().findFirst();
    }

    public Optional<TrainingPlanEntity> findByIdAndMember(Long accountId, Long memberId, Long trainingPlanId) {
        return trainingPlanRepository.findByIdAndMember(accountId, memberId, trainingPlanId).stream().findFirst();
    }

    public void deleteById(Long id) {
        trainingPlanRepository.deleteById(id);
    }

    public Optional<TrainingPlanEntity> findById(Long id) {
        return trainingPlanRepository.findById(id);
    }

    private void checkTrainingsMonth(Stream<Month> months) {
        Long numberOfDifferentMonth = months
                .distinct()
                .count();
        if (numberOfDifferentMonth > 1) {
            throw new IllegalArgumentException("All trainings should belong to the same month");
        }
    }
}