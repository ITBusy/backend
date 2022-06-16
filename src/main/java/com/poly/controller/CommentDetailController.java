package com.poly.controller;

import com.poly.dto.ResponseObject;
import com.poly.entity.CommentDetails;
import com.poly.service.impl.CommentDetailServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment-detail")
@CrossOrigin("*")
public class CommentDetailController {

    @Autowired
    private CommentDetailServiceImp commentDetailServiceImp;

    @PostMapping("/")
    public ResponseEntity<ResponseObject> createCommentDetail(@RequestBody CommentDetails commentDetails) {
        ResponseEntity<ResponseObject> message = null;
        CommentDetails commentDetails1 = this.commentDetailServiceImp.createCommentDetail(commentDetails);
        try {
            if (commentDetails1 != null) {
                message = ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("Ok", "Create comment detail is success", commentDetails1)
                );
            }
        } catch (Exception e) {
            message = ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("Failed", "Create comment detail is failed", null)
            );
        }
        return message;
    }
}
