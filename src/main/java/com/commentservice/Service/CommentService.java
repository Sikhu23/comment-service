package com.commentservice.Service;


import com.commentservice.Feign.FeignLike;
import com.commentservice.Feign.FeignUser;
import com.commentservice.Model.CommentDTO;
import com.commentservice.Model.CommentModel;
import com.commentservice.Model.FeignRequest;
import com.commentservice.Model.FeignRequestUser;
import com.commentservice.Repository.CommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


import java.util.ArrayList;
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


    public CommentDTO saveComment(CommentModel commentModel, String postId) {
        commentModel.setPostID(postId);
        commentModel.setCreatedAt(LocalDateTime.now());
        commentModel.setUpdatedAt(LocalDateTime.now());
         this.commentRepo.save(commentModel);
         CommentDTO commentDTO=new CommentDTO(commentModel.getCommentID(),
                 feignUser.findByID(commentModel.getCommentedBy()).getFirstName(),
                 commentModel.getComment(),commentModel.getCreatedAt(),commentModel.getUpdatedAt(),
                 feignLike.likeCount(commentModel.getCommentID()));

         return commentDTO;
    }



    public List<CommentDTO> showCommentsByPostId(String postId,Integer page,Integer pageSize) {
        if(page==null){
            page=1;
        }
        if(pageSize==null){
            pageSize=10;
        }
        CommentDTO commentDTO=new CommentDTO();
        Pageable firstPage = PageRequest.of(page-1, pageSize);
        List<CommentModel> commentModels  = commentRepo.findBypostID(postId,firstPage);
        List<CommentDTO> commentDTOS = new ArrayList<>();
        for(CommentModel commentModel:commentModels){
            CommentDTO commentDTO1=new CommentDTO(commentModel.getCommentID(),
                    feignUser.findByID(commentModel.getCommentedBy()).getFirstName(),
                    commentModel.getComment(),commentModel.getCreatedAt(),commentModel.getUpdatedAt(),
                    feignLike.likeCount(commentModel.getCommentID()));
            commentDTOS.add(commentDTO1);
        }
        return  commentDTOS;


    }

    public CommentDTO findByCommentId(String commentId) {
        CommentModel commentModel= commentRepo.findById(commentId).get();
        CommentDTO commentDTO = new CommentDTO(commentModel.getCommentID(),
                feignUser.findByID(commentModel.getCommentedBy()).getFirstName(),
                commentModel.getComment(),commentModel.getCreatedAt(),commentModel.getUpdatedAt(),
                feignLike.likeCount(commentModel.getCommentID()));
        return  commentDTO;
    }


    public CommentDTO updateComment(CommentModel commentModel, String postId,String commentId) {
        commentModel.setCommentID(commentId);
        commentModel.setUpdatedAt(LocalDateTime.now());
        commentModel.setCreatedAt(commentRepo.findById(commentId).get().getCreatedAt());
        commentModel.setPostID(postId);
         commentRepo.save(commentModel);
         CommentDTO commentDTO=new CommentDTO(commentModel.getCommentID(),
                 feignUser.findByID(commentModel.getCommentedBy()).getFirstName(),
                 commentModel.getComment(),commentModel.getCreatedAt(),commentModel.getUpdatedAt(),
                 feignLike.likeCount(commentModel.getCommentID()));
         return commentDTO;
    }


    public String deletebyCommentId(String commentId) {
        this.commentRepo.deleteById(commentId);
        return "Comment deleted successfully.";
    }

    public int commentCount(String postId){
        int count=this.commentRepo.findBypostID(postId).size();
        return count;

    }
}
