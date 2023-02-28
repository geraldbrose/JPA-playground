package com.akelius.playground.jpa.repos.fetchtest;

import com.akelius.playground.jpa.fetchtest.LibraryEagerBatch;
import com.akelius.playground.jpa.fetchtest.LibraryEagerSubSelect;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryEagerBatchRepo extends JpaRepository<LibraryEagerBatch, Long>  {
}
