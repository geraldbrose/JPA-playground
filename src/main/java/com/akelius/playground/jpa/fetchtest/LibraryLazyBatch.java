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

// fetch profiles support only fetch mode JOIN
@FetchProfiles(
        @FetchProfile(
                name = "libraries-lazy-batch-graph-profile",
                fetchOverrides = {
                        @FetchProfile.FetchOverride(entity = LibraryLazyBatch.class, association = "adverts", mode = FetchMode.JOIN),
                        @FetchProfile.FetchOverride(entity = LibraryLazyBatch.class, association = "articles", mode = FetchMode.JOIN),
                        @FetchProfile.FetchOverride(entity = LibraryLazyBatch.class, association = "books", mode = FetchMode.JOIN),
                        @FetchProfile.FetchOverride(entity = LibraryLazyBatch.class, association = "blogPosts", mode = FetchMode.JOIN)
                }
        )
)

@NamedEntityGraph(
        name = "libraries-lazy-batch-graph",
        attributeNodes = {
                @NamedAttributeNode("adverts"),
                @NamedAttributeNode("articles"),
                @NamedAttributeNode("books"),
                @NamedAttributeNode("blogPosts")
        }
)

@Getter
@Setter
@Entity
public class LibraryLazyBatch extends AbstractEntity {

    private String name;

    @OneToMany(fetch = FetchType.LAZY)
    @BatchSize(size = 5)
    private Set<Advert> adverts = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY)
    @BatchSize(size = 5)
    private Set<Article> articles = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY)
    @BatchSize(size = 5)
    private Set<Book> books = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY)
    @BatchSize(size = 5)
    private Set<BlogPost> blogPosts = new HashSet<>();

}
