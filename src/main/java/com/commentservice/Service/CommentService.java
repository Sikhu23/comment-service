package com.commentservice.Service;


import com.commentservice.Model.CommentModel;
import com.commentservice.Repository.CommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


import java.util.List;

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



    public List<CommentModel> showCommentsByPostId(String postId){
        return commentRepo.findBypostID(postId);

    public CommentModel findByCommentId(String commentId){
        return this.commentRepo.findById(commentId).get();


    public CommentModel updateComment(CommentModel commentModel, String postId,String commentId){
        commentModel.setCommentID(commentId);
        commentModel.setUpdatedAt(LocalDateTime.now());
        commentModel.setCreatedAt(commentRepo.findById(commentId).get().getCreatedAt());
        commentModel.setPostID(postId);
        return commentRepo.save(commentModel);


    public String deletebyCommentId(String commentId){
        this.commentRepo.deleteById(commentId);
        return "Delete CommentID "+commentId+" from DB";

    public int commentCount(String postId){
        int count=this.commentRepo.findBypostID(postId).size();
        return count;






    }
}
