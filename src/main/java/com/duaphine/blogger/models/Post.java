package com.duaphine.blogger.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "post")
public class Post {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "category_id")
  private Category category;
  @Column(name = "title")
  private String title;
  @Column(name = "content")
  private String content;

  @Column(name = "created_date")
  //@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") //Make the date data format correct for frontend
  private LocalDateTime createdDate;

  public Post() {

  }

  public Post(String title, String content, UUID categoryId) {
    this.title = title;
    this.content = content;
    //this.category=categoryId;
  }

  public Post(String title, String content) {
    this.title = title;
    this.content = content;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }


  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }


  public LocalDateTime getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(LocalDateTime createdDate) {
    this.createdDate = createdDate;
  }
}
