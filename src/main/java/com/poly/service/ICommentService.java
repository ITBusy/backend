package com.poly.service;

import com.poly.dto.CommentDto;
import com.poly.entity.Comment;

import java.util.List;

public interface ICommentService {
    Comment createComment(Comment comment);

    List<CommentDto> commentDtoList(Long pid);
}
