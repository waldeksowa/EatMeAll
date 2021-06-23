package pl.wizard.software.diet.meals;

import lombok.Data;
import pl.wizard.software.AbstractBaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "STEPS")
@Data
public class StepEntity extends AbstractBaseEntity {

    @Column(insertable = false, updatable = false, columnDefinition = "serial")
    private long position;
    private String description;
}
