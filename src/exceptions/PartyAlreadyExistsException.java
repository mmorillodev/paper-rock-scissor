package exceptions;

public class PartyAlreadyExistsException extends Exception {
    public PartyAlreadyExistsException(String name) {
        super(name);
    }
}
