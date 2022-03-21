package com.commentservice.Controller;


import com.commentservice.Model.CommentModel;
import com.commentservice.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentModel> saveComment(@Valid @RequestBody CommentModel commentModel, @PathVariable("postId") String postId){
        return new ResponseEntity<>(commentService.saveComment(commentModel,postId), HttpStatus.ACCEPTED);
    }
}
