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
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final JwtTokenizer jwtTokenizer;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentService commentService;

    @PostMapping("/api/postcomment")
    public ResponseEntity<?> postComment(@RequestParam("commentText") String commentText,
                                         @RequestParam("postId") Long postId,
                                         HttpServletRequest request,
                                         RedirectAttributes redirectAttributes) {

        String token = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("accessToken")) {
                    token = cookie.getValue();
                    break;
                }
            }
        }
        if (token == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        Claims claims=jwtTokenizer.parseAccessToken(token);
        String name = (String)claims.get("name");
        Long userId = Long.valueOf(claims.get("userId").toString());

        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return ResponseEntity.status(404).body("User not found");
        }
        Post post = postRepository.findById(postId).orElse(null);
        if (post == null) {
            return ResponseEntity.status(404).body("Post not found");
        }

        Comment comment = new Comment();
        comment.setUser_id(user);
        comment.setPost_id(post);
        comment.setCommentText(commentText);
        comment.setCreatedAt(LocalDateTime.now());
        comment.setUpdatedAt(LocalDateTime.now());

        Comment savedComment = commentService.createComment(comment);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedComment);
    }

    @PutMapping("/api/updatecomment/{id}")
    public ResponseEntity<?> updateComment(@PathVariable Long commentId, @RequestBody CommentDTO commentDTO, HttpServletRequest request) {
        String token = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("accessToken")) {
                    token = cookie.getValue();
                    break;
                }
            }
        }
        if (token == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        Claims claims = jwtTokenizer.parseAccessToken(token);
        Long userId = Long.valueOf(claims.get("userId").toString());

        Optional<Comment> optinalComment = commentService.getCommentById(commentId);
        Comment existingComment= optinalComment.orElse(null);
        if (existingComment == null) {
            return ResponseEntity.status(404).body("Comment not found");
        }

        if (!existingComment.getUser_id().getId().equals(userId)) {
            return ResponseEntity.status(403).body("Forbidden");
        }

        existingComment.setCommentText(commentDTO.getCommentText());
        existingComment.setUpdatedAt(LocalDateTime.now());
        Comment updatedComment = commentService.updateComment(existingComment);

        return ResponseEntity.ok(updatedComment);
    }






}
