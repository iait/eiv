package iait.eiv.error;

public class InvalidInputException extends RuntimeException {

    private static final long serialVersionUID = -8040216418450999887L;

    private String description;;

    public InvalidInputException(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
