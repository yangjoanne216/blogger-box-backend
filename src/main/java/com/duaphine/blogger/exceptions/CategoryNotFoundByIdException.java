package com.duaphine.blogger.exceptions;

import java.util.UUID;

public class CategoryNotFoundByIdException extends Exception {

  public CategoryNotFoundByIdException(UUID id) {
    super("category id: " + id + " not Found");
  }
}
