package com.akelius.playground.jpa.repos.fetchtest;

import com.akelius.playground.jpa.fetchtest.LibraryLazyBatch;
import com.akelius.playground.jpa.fetchtest.LibraryLazyDefault;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LibraryLazyDefaultRepo extends JpaRepository<LibraryLazyDefault, Long>  {
}
