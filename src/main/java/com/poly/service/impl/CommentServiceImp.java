package com.poly.service.impl;

import com.poly.dto.CommentDto;
import com.poly.entity.Comment;
import com.poly.entity.CommentDetails;
import com.poly.repository.CommentRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImp implements com.poly.service.ICommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentDetailServiceImp commentDetailServiceImp;

    @Override
    public Comment createComment(Comment comment) {
        return this.commentRepository.save(comment);
    }

    @Override
    public List<CommentDto> commentDtoList(Long pid) {
        List<CommentDto> commentDtos = new ArrayList<>();
        List<Comment> comments = this.commentRepository.findByProduct_pId(pid);
        for (Comment comment : comments) {
            CommentDto commentDto = new CommentDto();
            BeanUtils.copyProperties(comment, commentDto);
            List<CommentDetails> commentDetailsList = this.commentDetailServiceImp.findAllByCommentId(comment.getCommentId());
            commentDto.setCommentDetailsList(commentDetailsList);
            commentDtos.add(commentDto);
        }
        return commentDtos;
    }
}
