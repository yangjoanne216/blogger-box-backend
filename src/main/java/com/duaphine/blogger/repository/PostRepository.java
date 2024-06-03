package com.duaphine.blogger.repository;

import com.duaphine.blogger.models.Category;
import com.duaphine.blogger.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {
    @Query("""
            SELECT p
            FROM Post p
            WHERE p.title LIKE CONCAT('%', :search, '%') OR p.content LIKE CONCAT('%', :search, '%')
            """)
    Optional<List<Post>> findAllByTitleOrContent(@Param("search") String search);
    Optional<List<Post>> findAllByCategoryId(UUID categoryId);
    List<Post> findAllByOrderByCreatedDateDesc();

}
