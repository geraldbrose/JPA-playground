package com.akelius.playground.jpa.repos.fetchtest;

import com.akelius.playground.jpa.fetchtest.LibraryLazyBatchJoinColumn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryLazyBatchJoinColumnRepo extends JpaRepository<LibraryLazyBatchJoinColumn, Long>  {
}
