package com.akelius.playground.jpa.fetchtest;

import com.akelius.playground.jpa.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class LibraryEagerBatch extends AbstractEntity {

    private String name;

    @OneToMany(fetch = FetchType.EAGER)
    @BatchSize(size = 5)
    private Set<Advert> adverts = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER)
    @BatchSize(size = 5)
    private Set<Article> articles = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER)
    @BatchSize(size = 5)
    private Set<Book> books = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER)
    @BatchSize(size = 5)
    private Set<BlogPost> blogPosts = new HashSet<>();

}
