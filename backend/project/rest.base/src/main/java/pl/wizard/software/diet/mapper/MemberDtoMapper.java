package pl.wizard.software.diet.mapper;

import pl.wizard.software.diet.dto.MemberDto;
import pl.wizard.software.diet.members.MemberEntity;

import java.util.List;
import java.util.stream.Collectors;

public class MemberDtoMapper {

    private MemberDtoMapper() {
    }

    public static List<MemberDto> mapToMemberDtos(List<MemberEntity> members) {
        return members.stream()
                .map(member -> mapToMemberDto(member))
                .collect(Collectors.toList());
    }

    public static MemberDto mapToMemberDto(MemberEntity memberEntity) {
        return MemberDto.builder()
                .id(memberEntity.getId())
                .name(memberEntity.getName())
                .age(memberEntity.getAge())
                .currentWeight(memberEntity.getCurrentWeight())
                .currentFat(memberEntity.getCurrentFat())
                .currentMussels(memberEntity.getCurrentMussels())
                .currentWater(memberEntity.getCurrentWater())
                .recommendedCalories(memberEntity.getRecommendedCalories())
                .recommendedCarbohydrates(memberEntity.getRecommendedCarbohydrates())
                .recommendedFat(memberEntity.getRecommendedFat())
                .recommendedProtein(memberEntity.getRecommendedProtein())
                .recommendedRoughage(memberEntity.getRecommendedRoughage())
                .schedules(ScheduleDtoMapper.mapToScheduleDtos(memberEntity.getSchedules()))
                .excludedProducts(memberEntity.getExcludedProducts())
                .accountId(memberEntity.getAccountId())
                .build();
    }
}
