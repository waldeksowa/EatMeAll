package pl.wizard.software.diet.mapper;

import pl.wizard.software.diet.dto.MemberDto;
import pl.wizard.software.diet.members.MemberEntity;
import pl.wizard.software.diet.schedules.ScheduleEntity;

public class MemberDtoMapper {

    private MemberDtoMapper() {
    }

    public static MemberDto mapToMemberDto(MemberEntity memberEntity, ScheduleEntity schedule) {
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
                .schedule(ScheduleDtoMapper.mapToScheduleDto(schedule))
                .accountId(memberEntity.getAccountId())
                .build();
    }
}
