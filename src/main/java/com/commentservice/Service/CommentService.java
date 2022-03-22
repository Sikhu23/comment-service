package com.commentservice.Service;


import com.commentservice.Repository.CommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    private CommentRepo commentRepo;

    public int commentCount(String postId){
        int count=this.commentRepo.findBypostID(postId).size();
        return count;

    }
}
