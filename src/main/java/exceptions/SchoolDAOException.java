package exceptions;

public class SchoolDAOException extends RuntimeException {

    public SchoolDAOException() {
        super();
    }

    public SchoolDAOException(String message, Throwable cause) {
        super(message, cause);
    }

    public SchoolDAOException(String message) {
        super(message);
    }
}
