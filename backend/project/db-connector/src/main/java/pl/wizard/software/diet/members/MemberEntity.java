package pl.wizard.software.diet.members;

import lombok.*;
import pl.wizard.software.AbstractBaseEntity;
import pl.wizard.software.diet.products.ExcludedProductEntity;
import pl.wizard.software.diet.schedules.ScheduleEntity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "MEMBERS")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberEntity extends AbstractBaseEntity {

    private String name;
    private Integer age;
    private Integer weight;
    private Integer fat;
    private Integer mussels;
    private Integer water;
    private Integer recommendedCalories;
    private Integer recommendedCarbohydrates;
    private Integer recommendedFat;
    private Integer recommendedProtein;
    private Integer recommendedRoughage;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "member_id")
    private Set<ScheduleEntity> schedules;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "member_id")
    private Set<ExcludedProductEntity> excludedProducts;
}
