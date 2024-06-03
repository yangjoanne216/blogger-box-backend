package com.duaphine.blogger.exceptions;

import java.util.UUID;

public class PostNotFoundByIdException extends Exception{
    public PostNotFoundByIdException(UUID id){
        super("post id : "+ id+ "not Found");
    }

}

