package seedu.address.testutil;

/**
 * InappropriateMethodCallAssertionError extends AssertionError to provide a more specific error indication
 * for scenarios where a method is invoked inappropriately. It simplifies the process
 * of throwing assertion errors with a default or custom message across the application.
 */
public class InappropriateMethodCallAssertionError extends AssertionError {
    // Static message for the error
    private static final String INAPPROPRIATE_METHOD_CALL_MESSAGE = "This method should not be called.";

    /**
     * Constructs a InappropriateMethodCallAssertionError with a default error message.
     * This default message indicates that a method, which should not have been called, was invoked.
     */
    public InappropriateMethodCallAssertionError() {
        super(INAPPROPRIATE_METHOD_CALL_MESSAGE);
    }


}


