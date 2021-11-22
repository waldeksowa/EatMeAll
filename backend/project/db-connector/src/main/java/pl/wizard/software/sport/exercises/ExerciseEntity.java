package pl.wizard.software.sport.exercises;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.wizard.software.AbstractBaseEntity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "EXERCISES")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExerciseEntity extends AbstractBaseEntity {

    private String name;
    @ElementCollection(targetClass = MusclePart.class)
    @CollectionTable(name="muscle_part")
    @Enumerated(EnumType.ORDINAL)
    private Set<MusclePart> musclePart;

    public enum MusclePart {
        SHOULDERS("barki"), CHEST("klatka piersiowa"), BACK("plecy"), BICEPS("biceps"),
        TRICEPS("triceps"), STOMACH("brzuch"), BUTTOCK("pośladki"), QUADRICEPS("czworogłowe uda"),
        HAMSTRING("kulszowo-goleniowe"), ADDUCTOR("przywodziciele uda"), CALF("łydki");

        private String musclePart;

        MusclePart(String aMusclePart) {
            musclePart = aMusclePart;
        }

        public String getMusclePart() {
            return musclePart;
        }
    }
}
