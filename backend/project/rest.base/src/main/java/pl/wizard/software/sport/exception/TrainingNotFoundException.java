package pl.wizard.software.sport.exception;

public class TrainingNotFoundException extends RuntimeException {

    public TrainingNotFoundException(Long id) {
        super("Could not find training with id " + id);
    }
}
