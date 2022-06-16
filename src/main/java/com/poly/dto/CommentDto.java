package com.poly.dto;

import com.poly.entity.CommentDetails;
import com.poly.entity.Product;
import com.poly.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private Long commentId;
    private String content;
    private Date commentDate;
    private User user;
    private Product product;
    private List<CommentDetails> commentDetailsList;
}
