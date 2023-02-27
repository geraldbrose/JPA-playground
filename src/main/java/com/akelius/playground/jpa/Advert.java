package com.akelius.playground.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "Advert")
public class Advert extends Publication {

  @Column
  private String text;
}