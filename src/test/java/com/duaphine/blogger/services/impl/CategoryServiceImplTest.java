package com.duaphine.blogger.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.duaphine.blogger.exceptions.CategoryNameAlreadyExistsException;
import com.duaphine.blogger.exceptions.CategoryNotFoundByIdException;
import com.duaphine.blogger.exceptions.NoCategoryNameContainsStringException;
import com.duaphine.blogger.models.Category;
import com.duaphine.blogger.repository.CategoryRepository;
import com.duaphine.blogger.services.CategoryService;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class CategoryServiceImplTest {

  private CategoryRepository categoryRepository;
  private CategoryService categoryService;

  @BeforeEach
  void setup() {
    categoryRepository = mock(CategoryRepository.class);
    categoryService = new CategoryServiceImpl(categoryRepository);
  }

  @Test
  void shouldReturnAllCategories() {
    // Arrange
    List<Category> expectedCategories = Arrays.asList(new Category("Category1"),
        new Category("Category2"));
    when(categoryRepository.findAll()).thenReturn(expectedCategories);

    // Act
    List<Category> actualCategories = categoryService.getAll();

    // Assert
    assertEquals(expectedCategories, actualCategories);
  }

  @Test
  void shouldReturnCategoryWhenGetById() throws CategoryNotFoundByIdException {
    // Arrange
    UUID id = UUID.randomUUID();
    Category expected = new Category("Category");
    when(categoryRepository.findById(id)).thenReturn(Optional.of(expected));

    // Act
    Category actual = categoryService.getById(id);

    // Assert
    assertEquals(expected, actual);
  }

  @Test
  void shouldThrowExceptionWhenNotFoundById() {
    // Arrange
    UUID id = UUID.randomUUID();
    when(categoryRepository.findById(id)).thenReturn(Optional.empty());

    // Act & Assert
    CategoryNotFoundByIdException exception = assertThrows(
        CategoryNotFoundByIdException.class,
        () -> categoryService.getById(id)
    );

    assertEquals("category id: " + id + " not Found", exception.getMessage());
  }

  @Test
  void shouldCreateCategory() throws CategoryNameAlreadyExistsException {
    // Arrange
    String name = "New Category";
    Category expected = new Category(name);
    when(categoryRepository.existsByName(name)).thenReturn(false);
    when(categoryRepository.save(Mockito.any(Category.class))).thenReturn(expected);

    // Act
    Category actual = categoryService.create(name);

    // Assert
    assertEquals(expected, actual);
  }

  @Test
  void shouldThrowExceptionWhenCategoryNameAlreadyExists() {
    // Arrange
    String name = "Existing Category";
    when(categoryRepository.existsByName(name)).thenReturn(true);

    // Act & Assert
    CategoryNameAlreadyExistsException exception = assertThrows(
        CategoryNameAlreadyExistsException.class,
        () -> categoryService.create(name)
    );

    assertEquals("Category name: " + name + " already exists", exception.getMessage());
  }

  @Test
  void shouldUpdateCategoryName() throws CategoryNotFoundByIdException {
    // Arrange
    UUID id = UUID.randomUUID();
    String newName = "Updated Category";
    Category existing = new Category("Old Category");
    when(categoryRepository.findById(id)).thenReturn(Optional.of(existing));
    when(categoryRepository.save(existing)).thenReturn(existing);

    // Act
    Category updatedCategory = categoryService.updateName(id, newName);

    // Assert
    assertEquals(newName, updatedCategory.getName());
  }

  @Test
  void shouldThrowExceptionWhenUpdatingNonExistentCategory() {
    // Arrange
    UUID id = UUID.randomUUID();
    String newName = "Updated Category";
    when(categoryRepository.findById(id)).thenReturn(Optional.empty());

    // Act & Assert
    CategoryNotFoundByIdException exception = assertThrows(
        CategoryNotFoundByIdException.class,
        () -> categoryService.updateName(id, newName)
    );

    assertEquals("category id: " + id + " not Found", exception.getMessage());
  }

  @Test
  void shouldDeleteCategoryById() throws CategoryNotFoundByIdException {
    // Arrange
    UUID id = UUID.randomUUID();
    when(categoryRepository.existsById(id)).thenReturn(true);

    // Act
    boolean deleted = categoryService.deleteById(id);

    // Assert
    assertTrue(deleted);
    verify(categoryRepository, times(1)).deleteById(id);
  }

  @Test
  void shouldThrowExceptionWhenDeletingNonExistentCategory() {
    // Arrange
    UUID id = UUID.randomUUID();
    when(categoryRepository.existsById(id)).thenReturn(false);

    // Act & Assert
    CategoryNotFoundByIdException exception = assertThrows(
        CategoryNotFoundByIdException.class,
        () -> categoryService.deleteById(id)
    );

    assertEquals("category id: " + id + " not Found", exception.getMessage());
  }

  @Test
  void shouldReturnCategoriesByNameFragment() throws NoCategoryNameContainsStringException {
    // Arrange
    String nameFragment = "Cat";
    List<Category> expectedCategories = Arrays.asList(new Category("Category1"),
        new Category("Category2"));
    when(categoryRepository.findAllByNameFragment(nameFragment)).thenReturn(
        Optional.of(expectedCategories));

    // Act
    List<Category> actualCategories = categoryService.getByNameFragment(nameFragment);

    // Assert
    assertEquals(expectedCategories, actualCategories);
  }

  @Test
  void shouldThrowExceptionWhenNoCategoryContainsNameFragment() {
    // Arrange
    String nameFragment = "NonExistent";
    when(categoryRepository.findAllByNameFragment(nameFragment)).thenReturn(Optional.empty());

    // Act & Assert
    NoCategoryNameContainsStringException exception = assertThrows(
        NoCategoryNameContainsStringException.class,
        () -> categoryService.getByNameFragment(nameFragment)
    );

    assertEquals("No catagory name contians: " + nameFragment, exception.getMessage());
  }
}
