# Blogger-Box Backend
### Author: Yang YANG (M1 Miage apprentissage)
This is the backend service for the Blogger-Box application. It is built using Spring Boot and provides APIs for managing blog posts and categories.

## Features

- Manage blog posts (create, read, update, delete)
- Manage categories (create, read, update, delete)
- Search posts by title or content
- Search categories by name fragment
- Retrieve posts ordered by creation date

## Requirements

- Java 22 
- Maven

## Getting Started
### Build the project
```bash
mvn clean install
```
### Run the application
```bash
ng serve
```
The application will start on: http://localhost:8080
## API Endpoints
### Category Endpoints
- Retrieve all categories: GET /v1/categories (If a name parameter is provided, returns categories that match the name string)
- Retrieve a category by ID: GET /v1/categories/{id}
- Create a new category: POST /v1/categories
- Update a category: PUT /v1/categories/{id}
- Delete a category: DELETE /v1/categories/{id}
### Post Endpoints
- Retrieve all posts: GET /v1/posts (If a search parameter is provided, it returns posts that contain the search string in their title or content.)
- Retrieve all posts for a category: GET /v1/posts/by-category/{categoryId}
- Retrieve a post by ID: GET /v1/posts/{id}
- Create a new post: POST /v1/posts
- Update a post: PUT /v1/posts/{id}
- Delete a post: DELETE /v1/posts/{id}
- Retrieve posts ordered by creation date: GET /v1/posts/sorted

## Error Handling
The application uses a global exception handler to manage the following exceptions:
- CategoryNotFoundByIdException
- PostNotFoundByIdException
- CategoryNameAlreadyExistsException
- NoCategoryNameContainsStringException
- NoPostTitleOrContentContainsStringException


You can also view and test endpoints on the Swagger UI.
