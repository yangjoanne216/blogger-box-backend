package com.duaphine.blogger.repository;

import com.duaphine.blogger.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    @Query("""
            SELECT c
            FROM Category c
            WHERE c.name LIKE CONCAT('%',:name,'%') 
""")
    Optional<List<Category>> findAllByName(@Param("name")String name);


    boolean existsByName(String name);
}
