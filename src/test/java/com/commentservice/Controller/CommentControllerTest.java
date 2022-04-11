package com.commentservice.Controller;

import com.commentservice.Const.ConstFile;
import com.commentservice.Model.CommentDTO;
import com.commentservice.Model.CommentModel;
import com.commentservice.Model.User;
import com.commentservice.Service.CommentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@WebMvcTest(CommentController.class)
class CommentControllerTest {

    @MockBean
    CommentService commentService;

    @Autowired
    MockMvc mockMvc;

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void saveComment() throws Exception {
        CommentModel commentModel =createComment();
        CommentDTO commentDTO=createCommentDto();

        Mockito.when(commentService.saveComment(commentModel,"12")).thenReturn(commentDTO);
        mockMvc.perform(post("/posts/12/comments")
                        .content(asJsonString(commentModel))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.comment", Matchers.is("Nice comment")));

    }

    private CommentModel createComment(){

        return new CommentModel("1","12","1","Nice comment",
                null,  null);


    }

    private CommentDTO createCommentDto() throws ParseException {

        return new CommentDTO("1",createOneUserToPost(),"Nice comment",
                null,null,0);


    }

    private User createOneUserToPost() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date c= sdf.parse("2015-05-26");
        User user = new User();
        user.setUserID("1");
        user.setFirstName("Prabhu");
        user.setMiddleName("J");
        user.setLastName("S");
        user.setPhoneNumber("9090909090");
        user.setEmail("prabhu@mail.com");
        user.setEmployeeNumber("12345");
        user.setBloodGroup("O+");
        user.setGender("MALE");

        user.setAddress("Pune");
        user.setDateOfBirth(c);

        return user;
    }

    @Test
    void showCommentsByPostId() throws Exception {
        List<CommentDTO> userDTO = createCommentList();

        Mockito.when(commentService.showCommentsByPostId("12",1, 2)).thenReturn(userDTO);

        mockMvc.perform(get("/posts/12/comments?page=1&pageSize=2"))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].comment", Matchers.is("Nice comment 1")))
                .andExpect(jsonPath("$[1].comment", Matchers.is("Nice comment 2")));
    }

    private List<CommentDTO> createCommentList() throws ParseException {
        CommentDTO commentDTO1=new CommentDTO("1",createOneUserToPost(),"Nice comment 1",
                null,null,0);
        CommentDTO commentDTO2=new CommentDTO("2",createOneUserToPost(),"Nice comment 2",
                null,null,0);

        List<CommentDTO> commentDTOList=new ArrayList<>();
        commentDTOList.add(commentDTO1);
        commentDTOList.add(commentDTO2);
        return  commentDTOList;
    }

    @Test
    void findByCommentId() throws Exception {
        CommentDTO commentDTO =createCommentDto();
        Mockito.when(commentService.findByCommentId("1")).thenReturn(commentDTO);


        mockMvc.perform(get("/posts/1/comments/1"))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$", Matchers.aMapWithSize(6)))
                .andExpect(jsonPath("$.likesCount", Matchers.is(0)));

    }

    @Test
    void updateComment() throws Exception {
        CommentDTO commentDTO = createCommentDto();
        CommentModel commentModel =createComment() ;

        Mockito.when(commentService.updateComment(commentModel, "12","1")).thenReturn(commentDTO);

        mockMvc.perform(put("/posts/12/comments/1")
                        .content(asJsonString(commentModel))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.comment",Matchers.is("Nice comment")));


    }

    @Test
    void deletebyCommentId() throws Exception {

        Mockito.when(commentService.deletebyCommentId("1")).thenReturn(ConstFile.successCode);


        mockMvc.perform(delete("/posts/1/comments/1"))
                .andDo(print())
                .andExpect(status().isAccepted())

                .andExpect(jsonPath("$", Matchers.is(ConstFile.successCode)));

    }

    @Test
    void commentCount() throws Exception {

        Mockito.when(commentService.commentCount("1")).thenReturn(0);


        mockMvc.perform(get("/posts/1/comments/count"))
                .andDo(print())
                .andExpect(status().isAccepted())

                .andExpect(jsonPath("$", Matchers.is(0)));

    }
}