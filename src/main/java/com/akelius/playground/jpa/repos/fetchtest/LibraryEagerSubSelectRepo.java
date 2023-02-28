package com.akelius.playground.jpa.repos.fetchtest;

import com.akelius.playground.jpa.fetchtest.LibraryEagerJoin;
import com.akelius.playground.jpa.fetchtest.LibraryEagerSubSelect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LibraryEagerSubSelectRepo extends JpaRepository<LibraryEagerSubSelect, Long>  {

    @Query("SELECT l FROM LibraryEagerSubSelect l WHERE MOD(l.id, 2) = 0")
    List<LibraryEagerSubSelect> querySomeLibraries();


}
