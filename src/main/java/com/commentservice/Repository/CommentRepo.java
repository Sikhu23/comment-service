package com.commentservice.Repository;

import com.commentservice.Model.CommentModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CommentRepo extends MongoRepository<CommentModel,String> {


    public List<CommentModel> findBypostID(String postId);

}
