package domain;

public class PoulaillerDejaExistantException extends RuntimeException {

  public PoulaillerDejaExistantException() {
    super();
  }

  public PoulaillerDejaExistantException(String message) {
    super(message);
  }
}
