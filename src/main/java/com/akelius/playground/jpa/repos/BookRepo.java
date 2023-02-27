package com.akelius.playground.jpa.repos;

import com.akelius.playground.jpa.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepo extends JpaRepository<Book, Long> {
}
