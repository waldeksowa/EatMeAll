package pl.wizard.software.diet.meals;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.wizard.software.diet.members.MemberEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "MEMBER_MEALS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberMealEntity extends MealEntity {

    @OneToOne
    @JoinColumn(name = "member_id")
    private MemberEntity member;
}
