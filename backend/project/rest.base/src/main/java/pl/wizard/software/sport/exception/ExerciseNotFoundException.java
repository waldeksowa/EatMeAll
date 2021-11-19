package pl.wizard.software.sport.exception;

public class ExerciseNotFoundException extends RuntimeException {

    public ExerciseNotFoundException(Long id) {
        super("Could not find book with id " + id);
    }
}
