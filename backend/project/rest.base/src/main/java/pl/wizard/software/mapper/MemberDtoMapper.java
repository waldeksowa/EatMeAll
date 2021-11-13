package pl.wizard.software.mapper;

import pl.wizard.software.dto.MemberDto;
import pl.wizard.software.dto.MemberWithoutScheduleAndProdsDto;
import pl.wizard.software.diet.members.MemberEntity;
import pl.wizard.software.diet.schedules.ScheduleEntity;

import javax.persistence.Tuple;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MemberDtoMapper {

    private MemberDtoMapper() {
    }

    public static MemberDto mapToMemberDto(MemberEntity memberEntity, Optional<ScheduleEntity> schedule) {
        if (schedule.isPresent()) {
            return MemberDto.builder()
                    .id(memberEntity.getId())
                    .name(memberEntity.getName())
                    .age(memberEntity.getAge())
                    .height(memberEntity.getHeight())
                    .currentWeight(memberEntity.getCurrentWeight())
                    .currentFat(memberEntity.getCurrentFat())
                    .currentMussels(memberEntity.getCurrentMussels())
                    .currentWater(memberEntity.getCurrentWater())
                    .recommendedCalories(memberEntity.getRecommendedCalories())
                    .recommendedCarbohydrates(memberEntity.getRecommendedCarbohydrates())
                    .recommendedFat(memberEntity.getRecommendedFat())
                    .recommendedProtein(memberEntity.getRecommendedProtein())
                    .recommendedRoughage(memberEntity.getRecommendedRoughage())
                    .schedule(ScheduleDtoMapper.mapToScheduleDto(schedule.get()))
                    .accountId(memberEntity.getAccountId())
                    .build();
        } else {
            return MemberDto.builder()
                    .id(memberEntity.getId())
                    .name(memberEntity.getName())
                    .age(memberEntity.getAge())
                    .height(memberEntity.getHeight())
                    .currentWeight(memberEntity.getCurrentWeight())
                    .currentFat(memberEntity.getCurrentFat())
                    .currentMussels(memberEntity.getCurrentMussels())
                    .currentWater(memberEntity.getCurrentWater())
                    .recommendedCalories(memberEntity.getRecommendedCalories())
                    .recommendedCarbohydrates(memberEntity.getRecommendedCarbohydrates())
                    .recommendedFat(memberEntity.getRecommendedFat())
                    .recommendedProtein(memberEntity.getRecommendedProtein())
                    .recommendedRoughage(memberEntity.getRecommendedRoughage())
                    .accountId(memberEntity.getAccountId())
                    .build();
        }

    }

    public static List<MemberWithoutScheduleAndProdsDto> mapToMemberWithoutScheduleAndProdsDtos(List<Tuple> memberWithoutSchedule) {
        List<MemberWithoutScheduleAndProdsDto> members = new ArrayList<>();
        for (Tuple row : memberWithoutSchedule) {
            members.add(MemberWithoutScheduleAndProdsDto.builder()
                    .id((Long) row.get(0))
                    .name((String) row.get(1))
                    .age((Integer) row.get(2))
                    .height((Integer) row.get(3))
                    .currentWeight((Double) row.get(4))
                    .currentFat((Double) row.get(5))
                    .currentMussels((Double) row.get(6))
                    .currentWater((Double) row.get(7))
                    .recommendedCalories((Double) row.get(8))
                    .recommendedCarbohydrates((Double) row.get(9))
                    .recommendedFat((Double) row.get(10))
                    .recommendedProtein((Double) row.get(11))
                    .recommendedRoughage((Double) row.get(12))
                    .accountId((Long) row.get(13))
                    .build()
            );
        }
        return members;
    }
}
