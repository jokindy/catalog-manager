package com.example.catalogmanager.error.exception;

public class GeneralException extends RuntimeException {
  private final int status;
  private final String message;
  private final String reason;

  public GeneralException(int status, String message, String reason) {
    super();
    this.status = status;
    this.message = message;
    this.reason = reason;
  }

  public int getStatus() {
    return status;
  }

  @Override
  public String getMessage() {
    return message;
  }

  public String getReason() {
    return reason;
  }
}
