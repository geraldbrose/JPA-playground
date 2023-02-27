package com.akelius.playground.jpa.repos;

import com.akelius.playground.jpa.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogPostRepo extends JpaRepository<BlogPost, Long> {
}
