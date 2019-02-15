package iait.eiv.error;

public class EntityNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 6082211902981411159L;

    private String resource;

    public EntityNotFoundException(String resource) {
        this.resource = resource;
    }

    public String getResource() {
        return resource;
    }

}
