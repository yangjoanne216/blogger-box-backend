package com.duaphine.blogger.exceptions;

import java.util.UUID;

public class CategoryNameAlreadyExistsException extends Exception{

    public CategoryNameAlreadyExistsException(String name){

        super("Category name" + name + "not Found");
    }
}
