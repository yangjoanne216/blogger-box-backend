package com.duaphine.blogger.exceptions;

public class CategoryNotFoundByNameException extends Exception {

  public CategoryNotFoundByNameException(String name) {
    super("category name: " + name + " not Found");
  }

}
