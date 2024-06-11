package com.duaphine.blogger.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.duaphine.blogger.exceptions.CategoryNotFoundByIdException;
import com.duaphine.blogger.exceptions.CategoryNotFoundByNameException;
import com.duaphine.blogger.exceptions.NoPostTitleOrContentContainsStringException;
import com.duaphine.blogger.exceptions.PostNotFoundByIdException;
import com.duaphine.blogger.models.Category;
import com.duaphine.blogger.models.Post;
import com.duaphine.blogger.repository.CategoryRepository;
import com.duaphine.blogger.repository.PostRepository;
import com.duaphine.blogger.services.PostService;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class PostServiceImplTest {

  private PostRepository postRepository;
  private CategoryRepository categoryRepository;
  private PostService postService;

  @BeforeEach
  void setup() {
    postRepository = mock(PostRepository.class);
    categoryRepository = mock(CategoryRepository.class);
    postService = new PostServiceImpl(postRepository, categoryRepository);
  }

  @Test
  void shouldReturnAllPosts() {
    // Arrange
    List<Post> expectedPosts = Arrays.asList(new Post("Title1", "Content1"),
        new Post("Title2", "Content2"));
    when(postRepository.findAll()).thenReturn(expectedPosts);

    // Act
    List<Post> actualPosts = postService.getAll();

    // Assert
    assertEquals(expectedPosts, actualPosts);
  }

  @Test
  void shouldReturnPostsByCategoryId() throws CategoryNotFoundByIdException {
    // Arrange
    UUID categoryId = UUID.randomUUID();
    List<Post> expectedPosts = Arrays.asList(new Post("Title1", "Content1"),
        new Post("Title2", "Content2"));
    when(categoryRepository.existsById(categoryId)).thenReturn(true);
    when(postRepository.findAllByCategoryId(categoryId)).thenReturn(Optional.of(expectedPosts));

    // Act
    List<Post> actualPosts = postService.getAllByCategoryId(categoryId);

    // Assert
    assertEquals(expectedPosts, actualPosts);
  }

  @Test
  void shouldThrowExceptionWhenCategoryNotFoundById() {
    // Arrange
    UUID categoryId = UUID.randomUUID();
    when(categoryRepository.existsById(categoryId)).thenReturn(false);

    // Act & Assert
    CategoryNotFoundByIdException exception = assertThrows(
        CategoryNotFoundByIdException.class,
        () -> postService.getAllByCategoryId(categoryId)
    );

    assertEquals("category id: " + categoryId + " not Found", exception.getMessage());
  }

  @Test
  void shouldReturnPostsByTitleOrContent() throws NoPostTitleOrContentContainsStringException {
    // Arrange
    String search = "Content";
    List<Post> expectedPosts = Arrays.asList(new Post("Title1", "Content1"),
        new Post("Title2", "Content2"));
    when(postRepository.findAllByTitleOrContent(search)).thenReturn(Optional.of(expectedPosts));

    // Act
    List<Post> actualPosts = postService.getAllByTitleOrContent(search);

    // Assert
    assertEquals(expectedPosts, actualPosts);
  }

  @Test
  void shouldThrowExceptionWhenNoPostContainsTitleOrContent() {
    // Arrange
    String search = "NonExistentContent";
    when(postRepository.findAllByTitleOrContent(search)).thenReturn(Optional.empty());

    // Act & Assert
    NoPostTitleOrContentContainsStringException exception = assertThrows(
        NoPostTitleOrContentContainsStringException.class,
        () -> postService.getAllByTitleOrContent(search)
    );

    assertEquals(
        "Search information: " + search + "' is not contained in any post title or content.",
        exception.getMessage());
  }

  @Test
  void shouldReturnPostWhenGetById() throws PostNotFoundByIdException {
    // Arrange
    UUID id = UUID.randomUUID();
    Post expected = new Post("Title", "Content");
    when(postRepository.findById(id)).thenReturn(Optional.of(expected));

    // Act
    Post actual = postService.getById(id);

    // Assert
    assertEquals(expected, actual);
  }

  @Test
  void shouldThrowExceptionWhenPostNotFoundById() {
    // Arrange
    UUID id = UUID.randomUUID();
    when(postRepository.findById(id)).thenReturn(Optional.empty());

    // Act & Assert
    PostNotFoundByIdException exception = assertThrows(
        PostNotFoundByIdException.class,
        () -> postService.getById(id)
    );

    assertEquals("post id: " + id + " not Found", exception.getMessage());
  }

  @Test
  void shouldCreatePost() throws CategoryNotFoundByNameException {
    // Arrange
    String title = "Title";
    String content = "Content";
    String categoryName = "Category";
    Category category = new Category(categoryName);
    Post expected = new Post(title, content);
    expected.setCategory(category);
    when(categoryRepository.findByName(categoryName)).thenReturn(Optional.of(category));
    when(postRepository.save(Mockito.any(Post.class))).thenReturn(expected);

    // Act
    Post actual = postService.create(title, content, categoryName);

    // Assert
    assertEquals(expected, actual);
  }

  @Test
  void shouldThrowExceptionWhenCategoryNotFoundByName() {
    // Arrange
    String title = "Title";
    String content = "Content";
    String categoryName = "NonExistentCategory";
    when(categoryRepository.findByName(categoryName)).thenReturn(Optional.empty());

    // Act & Assert
    CategoryNotFoundByNameException exception = assertThrows(
        CategoryNotFoundByNameException.class,
        () -> postService.create(title, content, categoryName)
    );

    assertEquals("category name: " + categoryName + " not Found", exception.getMessage());
  }

  @Test
  void shouldUpdatePost() throws PostNotFoundByIdException, CategoryNotFoundByNameException {
    // Arrange
    UUID id = UUID.randomUUID();
    String title = "Updated Title";
    String content = "Updated Content";
    String categoryName = "Updated Category";
    Post existingPost = new Post("Old Title", "Old Content");
    Category category = new Category(categoryName);
    when(postRepository.findById(id)).thenReturn(Optional.of(existingPost));
    when(categoryRepository.findByName(categoryName)).thenReturn(Optional.of(category));
    when(postRepository.save(existingPost)).thenReturn(existingPost);

    // Act
    Post updatedPost = postService.update(id, title, content, categoryName);

    // Assert
    assertEquals(title, updatedPost.getTitle());
    assertEquals(content, updatedPost.getContent());
    assertEquals(category, updatedPost.getCategory());
  }

  @Test
  void shouldThrowExceptionWhenUpdatingNonExistentPost() {
    // Arrange
    UUID id = UUID.randomUUID();
    String title = "Updated Title";
    String content = "Updated Content";
    String categoryName = "Updated Category";
    when(postRepository.findById(id)).thenReturn(Optional.empty());

    // Act & Assert
    PostNotFoundByIdException exception = assertThrows(
        PostNotFoundByIdException.class,
        () -> postService.update(id, title, content, categoryName)
    );

    assertEquals("post id: " + id + " not Found", exception.getMessage());
  }

  @Test
  void shouldThrowExceptionWhenCategoryNotFoundDuringUpdate() {
    // Arrange
    UUID id = UUID.randomUUID();
    String title = "Updated Title";
    String content = "Updated Content";
    String categoryName = "NonExistentCategory";
    Post existingPost = new Post("Old Title", "Old Content");
    when(postRepository.findById(id)).thenReturn(Optional.of(existingPost));
    when(categoryRepository.findByName(categoryName)).thenReturn(Optional.empty());

    // Act & Assert
    CategoryNotFoundByNameException exception = assertThrows(
        CategoryNotFoundByNameException.class,
        () -> postService.update(id, title, content, categoryName)
    );

    assertEquals("category name: " + categoryName + " not Found", exception.getMessage());
  }

  @Test
  void shouldDeletePostById() throws PostNotFoundByIdException {
    // Arrange
    UUID id = UUID.randomUUID();
    when(postRepository.existsById(id)).thenReturn(true);

    // Act
    boolean deleted = postService.deleteById(id);

    // Assert
    assertTrue(deleted);
    verify(postRepository, times(1)).deleteById(id);
  }

  @Test
  void shouldThrowExceptionWhenDeletingNonExistentPost() {
    // Arrange
    UUID id = UUID.randomUUID();
    when(postRepository.existsById(id)).thenReturn(false);

    // Act & Assert
    PostNotFoundByIdException exception = assertThrows(
        PostNotFoundByIdException.class,
        () -> postService.deleteById(id)
    );

    assertEquals("post id: " + id + " not Found", exception.getMessage());
  }

  @Test
  void shouldReturnPostsOrderedByCreatedDate() {
    // Arrange
    List<Post> expectedPosts = Arrays.asList(new Post("Title1", "Content1"),
        new Post("Title2", "Content2"));
    when(postRepository.findAllByOrderByCreatedDateDesc()).thenReturn(expectedPosts);

    // Act
    List<Post> actualPosts = postService.findAllByOrderByCreatedDateDesc();

    // Assert
    assertEquals(expectedPosts, actualPosts);
  }
}
