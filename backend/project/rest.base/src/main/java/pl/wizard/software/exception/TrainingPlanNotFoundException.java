package pl.wizard.software.exception;

public class TrainingPlanNotFoundException extends RuntimeException {

    public TrainingPlanNotFoundException() {
        super("Could not find training plan");
    }
}
