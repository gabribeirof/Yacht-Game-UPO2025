package upo.yacht.exceptions;

/**
 * Custom exception for Yacht game-specific errors.
 * Used for invalid game states, rule violations, and input errors.
 */
public class YachtGameException extends Exception {

    /**
     * Creates a new exception with a descriptive message.
     *
     * @param message the error message
     */
    public YachtGameException(String message) {
        super(message);
    }

    /**
     * Creates a new exception with a message and a cause.
     *
     * @param message the error message
     * @param cause the underlying exception that caused this error
     */
    public YachtGameException(String message, Throwable cause) {
        super(message, cause);
    }
}