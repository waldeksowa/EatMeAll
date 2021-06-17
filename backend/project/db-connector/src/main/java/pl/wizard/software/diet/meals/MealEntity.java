package pl.wizard.software.diet.meals;

import lombok.Data;
import pl.wizard.software.AbstractBaseEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "MEALS")
@Data
public class MealEntity extends AbstractBaseEntity {
    private String name;
    private String author;
    private String description;
    @OneToMany
    @JoinColumn(name = "meal_id")
    private Set<MealProductEntity> products;
}
