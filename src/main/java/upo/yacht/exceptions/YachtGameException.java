package upo.yacht.exceptions;

/// Custom exception for Yacht game-specific errors.
///
/// Used for invalid game states, rule violations, and input errors
/// that occur during gameplay. Extends the standard Exception class
/// to provide game-specific error handling.
public class YachtGameException extends Exception {

    /// Creates a new exception with a descriptive message.
    ///
    /// @param message the error message describing what went wrong
    public YachtGameException(String message) {
        super(message);
    }

}
