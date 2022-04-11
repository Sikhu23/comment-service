package com.commentservice.Service;


import com.commentservice.Const.ConstFile;
import com.commentservice.Exception.CommentIDExistsException;
import com.commentservice.Exception.CommentNotFoundException;
import com.commentservice.Exception.UserNotFoundException;
import com.commentservice.Feign.FeignLike;
import com.commentservice.Feign.FeignUser;
import com.commentservice.Model.CommentDTO;
import com.commentservice.Model.CommentModel;
import com.commentservice.Model.FeignRequest;
import com.commentservice.Model.FeignRequestUser;
import com.commentservice.Repository.CommentRepo;
import feign.FeignException;
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
        try{
            CommentDTO commentDTO=new CommentDTO(commentModel.getCommentID(),
                    feignUser.findByID(commentModel.getCommentedBy()),
                    commentModel.getComment(),commentModel.getCreatedAt(),commentModel.getUpdatedAt(),
                    feignLike.likeCount(commentModel.getCommentID()));

            return commentDTO;
        }
        catch (FeignException e){
            throw new UserNotFoundException("No User Found for this commentedBy ID");
        }
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
        if(commentModels.isEmpty()){
            throw  new CommentNotFoundException(ConstFile.errorCode);
        }
        List<CommentDTO> commentDTOS = new ArrayList<>();
        try{
            for(CommentModel commentModel:commentModels){
                CommentDTO commentDTO1=new CommentDTO(commentModel.getCommentID(),
                        feignUser.findByID(commentModel.getCommentedBy()),
                        commentModel.getComment(),commentModel.getCreatedAt(),commentModel.getUpdatedAt(),
                        feignLike.likeCount(commentModel.getCommentID()));
                commentDTOS.add(commentDTO1);
            }
            return  commentDTOS;
        }

        catch (FeignException e){
            throw new UserNotFoundException("No User Found for this commentedBy ID");
        }


    }

    public CommentDTO findByCommentId(String commentId) {
        try{
            CommentModel commentModel= commentRepo.findById(commentId).get();
            CommentDTO commentDTO = new CommentDTO(commentModel.getCommentID(),
                    feignUser.findByID(commentModel.getCommentedBy()),
                    commentModel.getComment(),commentModel.getCreatedAt(),commentModel.getUpdatedAt(),
                    feignLike.likeCount(commentModel.getCommentID()));
            return  commentDTO;

        }
        catch (FeignException e){
            throw new UserNotFoundException("No User Found for this commentedBy ID");
        }

        catch(Exception e){
            throw  new CommentNotFoundException(ConstFile.errorCode);
        }
    }


    public CommentDTO updateComment(CommentModel commentModel, String postId,String commentId) {
       if(commentModel.getCommentID()==null || commentModel.getCommentID().equals(commentId)){
           commentModel.setCommentID(commentId);
           commentModel.setUpdatedAt(LocalDateTime.now());
           commentModel.setCreatedAt(commentRepo.findById(commentId).get().getCreatedAt());
           commentModel.setPostID(postId);

           try{
               CommentDTO commentDTO=new CommentDTO(commentModel.getCommentID(),
                       feignUser.findByID(commentModel.getCommentedBy()),
                       commentModel.getComment(),commentModel.getCreatedAt(),commentModel.getUpdatedAt(),
                       feignLike.likeCount(commentModel.getCommentID()));

               commentRepo.save(commentModel);
               return commentDTO;
           }
           catch (FeignException e){
               throw new UserNotFoundException("No User Found for this commentedBy ID");
           }
       }
       else{
           if(commentRepo.findById(commentModel.getCommentID()).isPresent() && commentRepo.findById(commentId).isPresent()){
               throw new CommentIDExistsException("CommentID already exists in DB");
           }
           else if(commentRepo.findById(commentModel.getCommentID()).isPresent() && !commentRepo.findById(commentId).isPresent()){
               throw new CommentNotFoundException("CommentID not exists");
           }
           else{
               commentModel.setCommentID(commentModel.getCommentID());
               commentModel.setUpdatedAt(LocalDateTime.now());
               commentModel.setCreatedAt(commentRepo.findById(commentId).get().getCreatedAt());
               commentModel.setPostID(postId);

               try{
                   CommentDTO commentDTO=new CommentDTO(commentModel.getCommentID(),
                           feignUser.findByID(commentModel.getCommentedBy()),
                           commentModel.getComment(),commentModel.getCreatedAt(),commentModel.getUpdatedAt(),
                           feignLike.likeCount(commentModel.getCommentID()));

                   commentRepo.save(commentModel);
                   commentRepo.deleteById(commentId);
                   return commentDTO;
               }
               catch (FeignException e){
                   throw new UserNotFoundException("No User Found for this commentedBy ID");
               }
           }
       }

    }


    public String deletebyCommentId(String commentId) {
       if(commentRepo.findById(commentId).isPresent()){
           this.commentRepo.deleteById(commentId);
           return ConstFile.successCode;
       }
       else{
           throw new CommentNotFoundException(ConstFile.errorCode);
       }
    }

    public int commentCount(String postId){
        int count=this.commentRepo.findBypostID(postId).size();
        return count;

    }
}
