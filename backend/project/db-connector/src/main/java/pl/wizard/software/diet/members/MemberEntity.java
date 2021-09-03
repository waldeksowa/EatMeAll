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
    private int age;
    private int weight;
    private int fat;
    private int mussels;
    private int water;
    private int recommendedCalories;
    private int recommendedCarbohydrates;
    private int recommendedFat;
    private int recommendedProtein;
    private int recommendedRoughage;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "member_id")
    private Set<ScheduleEntity> schedules;
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "member_id")
    private Set<ExcludedProductEntity> excludedProducts;
}
