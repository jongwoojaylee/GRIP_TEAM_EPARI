package org.example.grip_demo.comment.infrastructure;


import org.example.grip_demo.comment.domain.Comment;
import org.example.grip_demo.comment.domain.CommentRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepositoryImpl extends JpaRepository<Comment, Long>, CommentRepository {

}
