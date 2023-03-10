package com.akelius.playground.jpa;

import com.akelius.playground.jpa.fetchtest.LibraryLazyBatchJoinColumn;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
public abstract class Publication extends Text {

  @Column
  @Temporal(TemporalType.DATE)
  private Date publishingDate;

  @ManyToOne
  private LibraryLazyBatchJoinColumn library;
}
