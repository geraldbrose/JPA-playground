package com.akelius.playground.jpa.repos;

import com.akelius.playground.jpa.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepo extends JpaRepository<Author, Long>  {
}
