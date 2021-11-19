package pl.wizard.software.exception;

public class MemberNotFoundException extends RuntimeException {

    public MemberNotFoundException(Long memberId) {
        super("Could not find member with id " + memberId);
    }
}
