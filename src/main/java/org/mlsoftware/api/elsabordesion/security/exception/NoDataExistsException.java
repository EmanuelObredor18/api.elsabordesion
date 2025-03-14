package org.mlsoftware.api.elsabordesion.security.exception;

public class NoDataExistsException extends RuntimeException {
  
  public NoDataExistsException(String message) {
    super(message);
  }

  public NoDataExistsException() {}
}
