package com.commentservice.Service;


import com.commentservice.Model.CommentModel;
import com.commentservice.Repository.CommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CommentService {

    @Autowired
    private CommentRepo commentRepo;

    public CommentModel saveComment(CommentModel commentModel,String postId){
        commentModel.setPostID(postId);
        commentModel.setCreatedAt(LocalDateTime.now());
        commentModel.setUpdatedAt(LocalDateTime.now());
        return this.commentRepo.save(commentModel);
    }
}
