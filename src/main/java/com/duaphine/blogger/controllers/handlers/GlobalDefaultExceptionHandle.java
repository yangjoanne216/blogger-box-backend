package com.duaphine.blogger.controllers.handlers;

import com.duaphine.blogger.exceptions.CategoryNameAlreadyExistsException;
import com.duaphine.blogger.exceptions.CategoryNotFoundByIdException;
import com.duaphine.blogger.exceptions.PostNotFoundByIdException;
import com.duaphine.blogger.models.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class GlobalDefaultExceptionHandle {
    public static final Logger logger = LoggerFactory.getLogger(GlobalDefaultExceptionHandle.class);

    @ExceptionHandler({
        CategoryNotFoundByIdException.class,
            PostNotFoundByIdException.class
    })
    public ResponseEntity<String> handleNotFoundException(Exception ex){
        logger.warn("Not FOUND {}",ex.getMessage());
        return ResponseEntity.status(404).body(ex.getMessage());
    }

    @ExceptionHandler({
            CategoryNameAlreadyExistsException.class
    })
    public ResponseEntity<String> handleBasRequestException(Exception ex){
        logger.warn("Bad request{}",ex.getMessage());
        return ResponseEntity.status(404).body(ex.getMessage());
    }


}
