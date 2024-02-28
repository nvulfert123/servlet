package ru.netology.controller;

import com.google.gson.Gson;
import ru.netology.model.Post;
import ru.netology.repository.PostRepository;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Reader;

public class PostController {
  private static final String APPLICATION_JSON = "application/json";
  private final PostRepository repository;

  public PostController(PostRepository repository) {
    this.repository = repository;
  }

  public void all(HttpServletResponse response) throws IOException {
    response.setContentType(APPLICATION_JSON);
    final var gson = new Gson();
    final var data = repository.all();
    response.getWriter().print(gson.toJson(data));
  }

  public void getById(long id, HttpServletResponse response) throws IOException {
    response.setContentType(APPLICATION_JSON);
    final var gson = new Gson();
    final var post = repository.getById(id);
    if (post.isPresent()) {
      response.getWriter().print(gson.toJson(post.get()));
    } else {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    }
  }

  public void save(Reader body, HttpServletResponse response) throws IOException {
    response.setContentType(APPLICATION_JSON);
    final var gson = new Gson();
    final var post = gson.fromJson(body, Post.class);
    final var savedPost = repository.save(post);
    response.getWriter().print(gson.toJson(savedPost));
  }

  public void removeById(long id, HttpServletResponse response) {
    repository.removeById(id);
    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
  }
}
