package pl.wizard.software.diet.meals;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.wizard.software.diet.members.MemberEntity;

import javax.persistence.*;

@Entity
@Table(name = "MEMBER_MEALS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@PrimaryKeyJoinColumn(foreignKey=@ForeignKey(name = "meal_id"))
public class MemberMealEntity extends MealEntity {

    @OneToOne
    @JoinColumn(name = "member_id")
    private MemberEntity member;
    @OneToOne
    @JoinColumn(name = "parent_meal_id")
    private MealEntity meal;
}
