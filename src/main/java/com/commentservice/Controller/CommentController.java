package com.commentservice.Controller;


import com.commentservice.Model.CommentDTO;
import com.commentservice.Model.CommentModel;
import com.commentservice.Model.FeignRequest;
import com.commentservice.Model.FeignRequestUser;
import com.commentservice.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;


import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;


import org.springframework.web.bind.annotation.PathVariable;



import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.ws.rs.QueryParam;


@RestController
@RequestMapping("/posts/{postId}/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;


    @PostMapping()
    public ResponseEntity<CommentDTO> saveComment(@Valid @RequestBody CommentModel commentModel, @PathVariable("postId") String postId) {
        return new ResponseEntity<>(commentService.saveComment(commentModel, postId), HttpStatus.ACCEPTED);
    }


    @GetMapping()
    public ResponseEntity< List<CommentDTO>> showCommentsByPostId(@PathVariable("postId") String postId,  @QueryParam("page") Integer page, @QueryParam("pageSize") Integer pageSize) {
        return new ResponseEntity<>(commentService.showCommentsByPostId(postId,page,pageSize), HttpStatus.ACCEPTED);
    }



    @GetMapping("/{commentId}")
    public ResponseEntity<CommentDTO> findByCommentId(@PathVariable("postId") String postId, @PathVariable("commentId") String commentId) {
        return new ResponseEntity<>(commentService.findByCommentId(commentId), HttpStatus.ACCEPTED);
    }


    @PutMapping("/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(@RequestBody @Valid CommentModel commentModel, @PathVariable("postId") String postId, @PathVariable("commentId") String commentId) {
        return new ResponseEntity<>(commentService.updateComment(commentModel, postId, commentId), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deletebyCommentId(@PathVariable("postId") String postId,@PathVariable("commentId") String commentId) {
        return new ResponseEntity<>(commentService.deletebyCommentId(commentId), HttpStatus.ACCEPTED);
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> commentCount(@PathVariable("postId") String postId) {
        return new ResponseEntity<>(commentService.commentCount(postId), HttpStatus.ACCEPTED);
    }





}
