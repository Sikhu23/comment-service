package com.commentservice.Service;


import com.commentservice.Feign.FeignLike;
import com.commentservice.Feign.FeignUser;
import com.commentservice.Model.CommentModel;
import com.commentservice.Model.FeignRequest;
import com.commentservice.Model.FeignRequestUser;
import com.commentservice.Repository.CommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


import java.util.List;

import java.time.LocalDateTime;



@Service
public class CommentService {

    @Autowired
    FeignLike feignLike;

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    FeignUser feignUser;


    public CommentModel saveComment(CommentModel commentModel,String postId) {
        commentModel.setPostID(postId);
        commentModel.setCreatedAt(LocalDateTime.now());
        commentModel.setUpdatedAt(LocalDateTime.now());
        return this.commentRepo.save(commentModel);
    }



    public FeignRequest showCommentsByPostId(String postId) {
        FeignRequest feignRequest=new FeignRequest();
        feignRequest.setLikesCount(feignLike.likeCount(postId));
        feignRequest.setCommentModel(commentRepo.findBypostID(postId));



        return feignRequest;
    }

    public FeignRequestUser findByCommentId(String commentId) {
        FeignRequestUser feignRequest = new FeignRequestUser();
        feignRequest.setUser(feignUser.findByID(commentRepo.findById(commentId).get().getCommentedBy()));
        feignRequest.setCommentModel(this.commentRepo.findById(commentId).get());

        return feignRequest;
    }


    public CommentModel updateComment(CommentModel commentModel, String postId,String commentId) {
        commentModel.setCommentID(commentId);
        commentModel.setUpdatedAt(LocalDateTime.now());
        commentModel.setCreatedAt(commentRepo.findById(commentId).get().getCreatedAt());
        commentModel.setPostID(postId);
        return commentRepo.save(commentModel);
    }


    public String deletebyCommentId(String commentId) {
        this.commentRepo.deleteById(commentId);
        return "Delete CommentID " + commentId + " from DB";
    }

    public int commentCount(String postId){
        int count=this.commentRepo.findBypostID(postId).size();
        return count;

    }
}
