package com.akelius.playground.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "BlogPost")
public class BlogPost extends Publication {

  @Column
  private String url;
}
