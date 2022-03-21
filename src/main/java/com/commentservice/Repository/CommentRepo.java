package com.commentservice.Repository;

import com.commentservice.Model.CommentModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CommentRepo extends MongoRepository<CommentModel,String> {
}
