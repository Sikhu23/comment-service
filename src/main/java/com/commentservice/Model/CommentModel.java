package com.commentservice.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;

import java.time.LocalDateTime;
import java.util.Date;


@Document(collection = "Comment")
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class CommentModel {
    @Id
    private String commentID;

    @NotEmpty(message = "user ID is required")
    private String userID;




    private String postID;

    @NotEmpty(message = "commentedBy is required")
    private String commentedBy;

    @NotEmpty(message = "comment is required")
    private String comment;


    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
