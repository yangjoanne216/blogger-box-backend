package com.duaphine.blogger.repository;

import com.duaphine.blogger.models.Category;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<Category, UUID> {

  @Query("""
          SELECT c
          FROM Category c
          WHERE c.name LIKE CONCAT('%',:nameFragment,'%') 
      """)
  Optional<List<Category>> findAllByNameFragment(@Param("nameFragment") String nameFragment);

  Optional<Category> findByName(@Param("name") String name);

  boolean existsByName(String name);
}
