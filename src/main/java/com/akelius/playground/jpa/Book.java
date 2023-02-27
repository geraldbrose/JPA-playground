package com.akelius.playground.jpa;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "Book")
@Setter
@Getter
public class Book extends Publication {

  @Column
  private int pages;
}
