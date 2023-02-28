package com.akelius.playground.jpa.repos.fetchtest;

import com.akelius.playground.jpa.fetchtest.LibraryEagerJoin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibraryEagerJoinRepo extends JpaRepository<LibraryEagerJoin, Long>  {
}
