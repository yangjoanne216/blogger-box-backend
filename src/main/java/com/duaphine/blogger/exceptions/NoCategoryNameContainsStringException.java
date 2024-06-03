package com.duaphine.blogger.exceptions;

public class NoCategoryNameContainsStringException extends Exception{
    public NoCategoryNameContainsStringException(String nameFragment){
        super("No catagory name contianing ：" + nameFragment);
    }
}
