package com.commentservice.Service;


import com.commentservice.Repository.CommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    private CommentRepo commentRepo;

    public String deletebyCommentId(String commentId){
        this.commentRepo.deleteById(commentId);
        return "Delete CommentID "+commentId+" from DB";
    }
}
