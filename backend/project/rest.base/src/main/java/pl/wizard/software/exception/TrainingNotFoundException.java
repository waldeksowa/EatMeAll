package pl.wizard.software.exception;

public class TrainingNotFoundException extends RuntimeException {

    public TrainingNotFoundException(Long id) {
        super("Could not find training with id " + id);
    }
}
