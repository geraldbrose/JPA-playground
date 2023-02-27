package com.akelius.playground.jpa;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Text extends AbstractEntity {

  @Column
  protected String title;

  @ManyToMany
  @JoinTable(name = "Textauthor",
          joinColumns = {@JoinColumn(name = "publicationId", referencedColumnName = "id")},
          inverseJoinColumns = {@JoinColumn(name = "authorId", referencedColumnName = "id")})
  private Set<Author> authors = new HashSet<>();

}