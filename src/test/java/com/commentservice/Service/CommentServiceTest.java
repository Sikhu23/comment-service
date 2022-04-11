package com.commentservice.Service;

import com.commentservice.Exception.CommentNotFoundException;
import com.commentservice.Feign.FeignLike;
import com.commentservice.Feign.FeignUser;
import com.commentservice.Model.CommentDTO;
import com.commentservice.Model.CommentModel;
import com.commentservice.Model.User;
import com.commentservice.Repository.CommentRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.Mockito.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class CommentServiceTest {

    @InjectMocks
    CommentService service;

    @Mock
    CommentRepo commentRepo;

    @Mock
    FeignUser feignUser;

    @Mock
    FeignLike feignLike;

    private CommentModel createCommentModel(){
        return new CommentModel("1","12","123","comment",null,null);
    }
    private CommentDTO createCommentDTO() throws ParseException {
        return new CommentDTO("1",createUser(),"comment",null,null,0);
    }

    private User createUser() throws ParseException {
        User user = new User();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date c= sdf.parse("2015-05-26");
        user.setUserID("123");
        user.setFirstName("firstTest");
        user.setMiddleName("J");
        user.setLastName("S");
        user.setPhoneNumber("9090909090");
        user.setEmail("natsu@mail.com");
        user.setDateOfBirth(c);
        user.setEmployeeNumber("12345");
        user.setBloodGroup("O+");
        user.setGender("MALE");
        return user;

    }

    @Test
    void saveComment() throws ParseException {
        CommentDTO commentDTO = createCommentDTO();
        CommentModel commentModel =createCommentModel();

        Mockito.when(this.commentRepo.save(any(CommentModel.class))).thenReturn(commentModel);

        assertThat(this.service.saveComment(commentModel,"12").getComment()).isEqualTo(commentDTO.getComment());



    }

    @Test
    void showCommentsByPostId() throws ParseException {
        CommentModel commentModel = createCommentModel();
        CommentDTO commentDTO =createCommentDTO();

        List<CommentModel> list = new ArrayList<>();
        list.add(commentModel);

        PageImpl<CommentModel> pageImpl = new PageImpl<>(list);
        Pageable firstPage = PageRequest.of(0, 2);

        when(this.commentRepo.findBypostID("12",firstPage)).thenReturn( list);


        assertEquals(1,this.service.showCommentsByPostId("12",null,2).size());

    }

    @Test
    void findByCommentId() throws ParseException {

        CommentModel commentModel = createCommentModel();
        CommentDTO commentDTO =createCommentDTO();

        when(this.commentRepo.findById("1")).thenReturn(Optional.of(commentModel));
        assertThat(this.service.findByCommentId("1").getComment()).isEqualTo(commentDTO.getComment());
        assertThrows(CommentNotFoundException.class,()->this.service.findByCommentId("2"));


    }

    @Test
    void updateComment() throws ParseException {

        CommentModel commentModel = createCommentModel();
        CommentDTO commentDTO =createCommentDTO();

        when(this.commentRepo.save(commentModel)).thenReturn(commentModel);
        when(this.commentRepo.findById("1")).thenReturn(Optional.of(commentModel));

        assertThat(this.service.updateComment(commentModel,"12","1").getComment()).isEqualTo(
                commentDTO.getComment()
        );
        assertThrows(CommentNotFoundException.class,()->this.service.updateComment(commentModel,
                "12","12"));

    }

    @Test
    void deletebyCommentId() throws ParseException {


        CommentModel commentModel = createCommentModel();
        CommentDTO commentDTO =createCommentDTO();

      doNothing().when(this.commentRepo).deleteById("1");
      when(this.commentRepo.findById("1")).thenReturn(Optional.of(commentModel));

      assertThat(this.service.deletebyCommentId("1"));

        assertThrows(CommentNotFoundException.class,()->this.service.deletebyCommentId("2"));




    }

    @Test
    void commentCount() throws ParseException {
        CommentModel commentModel = createCommentModel();
        CommentDTO commentDTO =createCommentDTO();

        List<CommentModel> list = new ArrayList<>();
        list.add(commentModel);

        when(this.commentRepo.findBypostID("12")).thenReturn(list);

        assertThat(this.service.commentCount("12")).isEqualTo(list.size());


    }
}