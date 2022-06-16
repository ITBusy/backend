package com.poly.controller;

import com.poly.dto.ResponseObject;
import com.poly.entity.Comment;
import com.poly.service.impl.CommentServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@CrossOrigin("*")
public class CommentController {
    @Autowired
    private CommentServiceImp commentServiceImp;

    @PostMapping("/")
    public ResponseEntity<ResponseObject> createComment(@RequestBody Comment comment) {
        ResponseEntity<ResponseObject> message = null;
        Comment comment1 = this.commentServiceImp.createComment(comment);
        try {
            if (comment1 != null) {
                message = ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("Ok", "Create comment is success", comment)
                );
            }
        } catch (Exception e) {
            message = ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("Failed", "Create comment is failed", null)
            );
        }
        return message;
    }

    @GetMapping("/product/{pid}")
    public ResponseEntity<ResponseObject> findAllComments(@PathVariable("pid") Long pid) {
        return ResponseEntity.ok(new ResponseObject("Ok", "have data", this.commentServiceImp.commentDtoList(pid)));
    }
}
