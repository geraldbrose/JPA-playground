package com.akelius.playground.jpa.fetchtest;

import com.akelius.playground.jpa.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.FetchProfile;
import org.hibernate.annotations.FetchProfiles;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@Entity
public class LibraryLazyDefault extends AbstractEntity {

    private String name;

    @OneToMany(fetch = FetchType.LAZY)
    private Set<Advert> adverts = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY)
    private Set<Article> articles = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY)
    private Set<Book> books = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY)
    private Set<BlogPost> blogPosts = new HashSet<>();

}
