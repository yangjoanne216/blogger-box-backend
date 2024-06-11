package com.duaphine.blogger.controllers.handlers;

import com.duaphine.blogger.exceptions.CategoryNameAlreadyExistsException;
import com.duaphine.blogger.exceptions.CategoryNotFoundByIdException;
import com.duaphine.blogger.exceptions.CategoryNotFoundByNameException;
import com.duaphine.blogger.exceptions.NoCategoryNameContainsStringException;
import com.duaphine.blogger.exceptions.NoPostTitleOrContentContainsStringException;
import com.duaphine.blogger.exceptions.PostNotFoundByIdException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalDefaultExceptionHandle {

  public static final Logger logger = LoggerFactory.getLogger(GlobalDefaultExceptionHandle.class);

  @ExceptionHandler({
      CategoryNotFoundByIdException.class,
      CategoryNotFoundByNameException.class,
      PostNotFoundByIdException.class,
      NoCategoryNameContainsStringException.class,
      NoPostTitleOrContentContainsStringException.class
  })
  public ResponseEntity<String> handleNotFoundException(Exception ex) {
    logger.warn("Not FOUND {}", ex.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
  }

  @ExceptionHandler({
      CategoryNameAlreadyExistsException.class,
  })
  public ResponseEntity<String> handleBasRequestException(Exception ex) {
    logger.warn("Bad request{}", ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleGeneralException(Exception ex) {
    logger.error("Internal server error: {}", ex.getMessage());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body("An unexpected error occurred");
  }

}
