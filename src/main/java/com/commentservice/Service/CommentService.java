package com.commentservice.Service;


import com.commentservice.Model.CommentModel;
import com.commentservice.Repository.CommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepo commentRepo;


    public List<CommentModel> showCommentsByPostId(String postId){
        return commentRepo.findBypostID(postId);
    }
}
