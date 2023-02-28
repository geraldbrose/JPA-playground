package com.akelius.playground.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "Article")
public class Article extends Publication {

  @Column
  private String summary;
}
