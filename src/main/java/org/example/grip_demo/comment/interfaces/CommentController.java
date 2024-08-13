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

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    public final JwtTokenizer jwtTokenizer;

    @PostMapping("/postcomment")
    public ResponseEntity<Comment> addComment(@RequestParam Long postId,
                                              @RequestParam Long userId,
                                              @RequestParam String commentText,
                                              HttpServletRequest request) {
        Post post = postRepository.findById(postId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);

        Comment comment= Comment.builder()
                .post_id(post)
                .user_id(user)
                .commentText(commentText)
                .createdAt(LocalDateTime.now())
                .build();

        Comment savedComment = commentService.createComment(comment);

        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(savedComment.getId());
        commentDTO.setCommentText(savedComment.getCommentText());
        commentDTO.setCreatedAt(savedComment.getCreatedAt());
        commentDTO.setUpdatedAt(savedComment.getUpdatedAt());
        commentDTO.setUserId(savedComment.getUser_id().getId());
        commentDTO.setPostId(savedComment.getPost_id().getId());

        return ResponseEntity.ok(savedComment);

    }


//    @PostMapping("/updatecomment/{commentId}")
//    public ResponseEntity<Comment> updateComment(@PathVariable Long commentId, @RequestBody Comment comment) {
//        // 댓글 수정 로직
//    }
//
//    @DeleteMapping("/deletecomment/{commentId}")
//    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
//        // 댓글 삭제 로직
//    }
}
