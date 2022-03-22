package com.commentservice.Controller;


import com.commentservice.Model.CommentModel;
import com.commentservice.Model.FeignRequest;
import com.commentservice.Model.FeignRequestUser;
import com.commentservice.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


import org.springframework.web.bind.annotation.DeleteMapping;


import org.springframework.web.bind.annotation.PathVariable;



import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.ws.rs.QueryParam;


@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;


    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentModel> saveComment(@Valid @RequestBody CommentModel commentModel, @PathVariable("postId") String postId) {
        return new ResponseEntity<>(commentService.saveComment(commentModel, postId), HttpStatus.ACCEPTED);
    }


    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity< FeignRequest> showCommentsByPostId(@PathVariable("postId") String postId,  @QueryParam("page") int page, @QueryParam("pageSize") int pageSize) {
        return new ResponseEntity<>(commentService.showCommentsByPostId(postId,page,pageSize), HttpStatus.ACCEPTED);
    }



    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<FeignRequestUser> findByCommentId(@PathVariable("postId") String postId, @PathVariable("commentId") String commentId) {
        return new ResponseEntity<>(commentService.findByCommentId(commentId), HttpStatus.ACCEPTED);
    }


    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentModel> updateComment(@RequestBody @Valid CommentModel commentModel, @PathVariable("postId") String postId, @PathVariable("commentId") String commentId) {
        return new ResponseEntity<>(commentService.updateComment(commentModel, postId, commentId), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> deletebyCommentId(@PathVariable("postId") String postId,@PathVariable("commentId") String commentId) {
        return new ResponseEntity<>(commentService.deletebyCommentId(commentId), HttpStatus.ACCEPTED);
    }

    @GetMapping("/posts/{postId}/comments/count")
    public ResponseEntity<Integer> commentCount(@PathVariable("postId") String postId) {
        return new ResponseEntity<>(commentService.commentCount(postId), HttpStatus.ACCEPTED);
    }





}
