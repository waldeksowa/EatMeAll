package pl.wizard.software.diet.meals;

import lombok.Data;
import pl.wizard.software.AbstractBaseEntity;
import pl.wizard.software.diet.products.ProductEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "MEALS")
@Data
public class MealEntity extends AbstractBaseEntity {
    private String name;
    private String author;
    private String description;
    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "MEALS_PRODUCTS",
            joinColumns = { @JoinColumn(name = "meal_id") },
            inverseJoinColumns = { @JoinColumn(name = "product_id") }
    )
    private List<ProductEntity> products;
    @OneToMany
    @JoinColumn(name = "meal_id")
    private String steps;
}
