package com.akelius.playground.jpa.repos;

import com.akelius.playground.jpa.Advert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdvertRepo extends JpaRepository<Advert, Long> {
}
