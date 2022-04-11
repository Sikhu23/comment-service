package com.commentservice.Exception;

public class CommentIDExistsException extends RuntimeException {
    public CommentIDExistsException(String msg){
        super(msg);
    }
}
