package com.poly.service.impl;

import com.poly.entity.CommentDetails;
import com.poly.repository.CommentDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentDetailServiceImp implements com.poly.service.ICommentDetailService {
    @Autowired
    private CommentDetailRepository commentDetailRepository;

    @Override
    public CommentDetails createCommentDetail(CommentDetails commentDetails) {
        return this.commentDetailRepository.save(commentDetails);
    }

    @Override
    public List<CommentDetails> findAllByCommentId(Long id) {
        return this.commentDetailRepository.findByComment_CommentId(id);
    }
}
