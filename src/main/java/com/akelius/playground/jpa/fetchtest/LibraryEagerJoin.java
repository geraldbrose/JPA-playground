package com.akelius.playground.jpa.fetchtest;

import com.akelius.playground.jpa.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class LibraryEagerJoin extends AbstractEntity {

    private String name;

    @OneToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    private Set<Advert> adverts = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    private Set<Article> articles = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    private Set<Book> books = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER)
    @Fetch(FetchMode.JOIN)
    private Set<BlogPost> blogPosts = new HashSet<>();

}
