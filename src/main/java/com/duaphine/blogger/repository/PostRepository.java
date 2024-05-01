package com.duaphine.blogger.repository;

import com.duaphine.blogger.models.Category;
import com.duaphine.blogger.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {
    List<Post> findAllByCategoryId(UUID categoryId);

}
