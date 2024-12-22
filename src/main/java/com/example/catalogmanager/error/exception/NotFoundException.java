package com.example.catalogmanager.error.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends GeneralException {
  public NotFoundException() {
    super(HttpStatus.NOT_FOUND.value(), "Entity not found", "Wrong id");
  }
}
