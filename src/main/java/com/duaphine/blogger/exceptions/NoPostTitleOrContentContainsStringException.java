package com.duaphine.blogger.exceptions;

public class NoPostTitleOrContentContainsStringException extends Exception {

  public NoPostTitleOrContentContainsStringException(String search) {
    super("Search information: " + search + "' is not contained in any post title or content.");

  }
}
