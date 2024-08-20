package org.example.grip_demo.comment.interfaces;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.grip_demo.comment.application.CommentService;
import org.example.grip_demo.comment.domain.Comment;
import org.example.grip_demo.demo.JwtTokenizer;
import org.example.grip_demo.post.domain.Post;
import org.example.grip_demo.post.domain.PostRepository;
import org.example.grip_demo.user.domain.User;
import org.example.grip_demo.user.infrastructure.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final CommentService commentService;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    public final JwtTokenizer jwtTokenizer;

    @PostMapping("/postcomment")
    public ResponseEntity<CommentDTO> addComment(@RequestParam Long postId,
                                              @RequestParam Long userId,
                                              @RequestParam String commentText,
                                              HttpServletRequest request) {

        Post post = postRepository.findById(postId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);

        Comment comment= Comment.builder()
                .post(post)
                .user(user)
                .commentText(commentText)
                .createdAt(LocalDateTime.now())
                .build();

        Comment savedComment = commentService.createComment(comment);

        CommentDTO commentDTO =
                new CommentDTO(savedComment.getId(),savedComment.getCommentText(),
                savedComment.getCreatedAt(), savedComment.getUpdatedAt(),savedComment.getUser().getName(),
                savedComment.getUser().getId(),savedComment.getPost().getId());

        log.info(commentDTO.toString());

        return ResponseEntity.ok(commentDTO);

    }

    @GetMapping("/getcomments")
    public ResponseEntity<?> getComments(Long postId) {
        Post post= postRepository.findById(postId).orElse(null);
        List<Comment> commentlist=commentService.getCommentsByPost(post);

        List<CommentDTO> commentDTOs = commentlist.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            commentDTO.setId(comment.getId());
            commentDTO.setCommentText(comment.getCommentText());
            commentDTO.setCreatedAt(comment.getCreatedAt());
            commentDTO.setUpdatedAt(comment.getUpdatedAt());
            commentDTO.setName(comment.getUser().getName());
            commentDTO.setUserId(comment.getUser().getId());
            commentDTO.setPostId(comment.getPost().getId());
            return commentDTO;
        }).collect(Collectors.toList());

        log.info("CommentDTOs: " + commentDTOs.toString());

        return ResponseEntity.ok(commentDTOs);

    }

    @PostMapping("/updatecomment/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable Long commentId, @RequestBody CommentDTO forCommentText) {
        CommentDTO commentDTO=new CommentDTO();
        Comment fuckingNewComment=commentService.updateComment(commentId,forCommentText.getCommentText());
        commentDTO.setId(fuckingNewComment.getId());
        commentDTO.setCommentText(fuckingNewComment.getCommentText());
        commentDTO.setCreatedAt(fuckingNewComment.getCreatedAt());
        commentDTO.setUpdatedAt(fuckingNewComment.getUpdatedAt());
        commentDTO.setName(fuckingNewComment.getUser().getName());
        commentDTO.setUserId(fuckingNewComment.getUser().getId());
        commentDTO.setPostId(fuckingNewComment.getPost().getId());

        return  ResponseEntity.ok(commentDTO);

    }

    @DeleteMapping("/deletecomment/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok().build();
    }
}
