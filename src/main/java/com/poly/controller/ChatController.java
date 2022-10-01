package com.poly.controller;

import com.poly.dto.CommentDto;
import com.poly.entity.Comment;
import com.poly.entity.CommentDetails;
import com.poly.service.ICommentDetailService;
import com.poly.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private ICommentService ICommentService;

    @Autowired
    private ICommentDetailService iCommentDetailService;

    /**
     * Sends a message to its destination channel
     *
     * @return
     */
    @MessageMapping("/messages/{pId}")
    @SendTo("/channel/chat/{pId}")
    public List<CommentDto> handleMessage(Comment message, @DestinationVariable("pId") Long pId) {
        this.ICommentService.createComment(message);
//        this.template.convertAndSend("/channel/chat/" + message.getProduct().getPId(), message);
        return this.ICommentService.commentDtoList(pId);
    }

    @MessageMapping("/message-detail/{pId}")
    @SendTo("/channel/chat/{pId}")
    public List<CommentDto> handleMessage(CommentDetails commentDetails, @DestinationVariable("pId") Long pId) {
        this.iCommentDetailService.createCommentDetail(commentDetails);
//        this.template.convertAndSend("/channel/chat/" + commentDetails.getComment().getProduct().getPId(), commentDetails);
        return this.ICommentService.commentDtoList(pId);
    }
}
