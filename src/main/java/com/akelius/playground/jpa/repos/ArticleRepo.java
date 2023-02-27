package com.akelius.playground.jpa.repos;

import com.akelius.playground.jpa.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepo extends JpaRepository<Article, Long> {
}
