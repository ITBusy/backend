package com.poly.service;

import com.poly.entity.CommentDetails;

import java.util.List;

public interface ICommentDetailService {
    CommentDetails createCommentDetail(CommentDetails commentDetails);

    List<CommentDetails> findAllByCommentId(Long id);
}
