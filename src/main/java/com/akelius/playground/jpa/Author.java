package com.akelius.playground.jpa;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
public class Author extends  AbstractEntity{
  private String name;

  @ManyToMany
  @JoinTable(name = "Textauthor",
          inverseJoinColumns = { @JoinColumn(name = "publicationId", referencedColumnName = "id") },
          joinColumns = { @JoinColumn(name = "authorId", referencedColumnName = "id") })
  private Set<Text> publications;
}
