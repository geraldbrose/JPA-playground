package com.akelius.playground.jpa.repos.fetchtest;

import com.akelius.playground.jpa.fetchtest.LibraryEagerBatch;
import com.akelius.playground.jpa.fetchtest.LibraryLazyBatch;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LibraryLazyBatchRepo extends JpaRepository<LibraryLazyBatch, Long>  {

    @EntityGraph("libraries-lazy-batch-graph")
    @Query("SELECT l FROM LibraryLazyBatch l")
    List<LibraryLazyBatch> findAllDeep();
}
