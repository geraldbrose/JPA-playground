package com.akelius.playground.jpa.fetchtest;

import com.akelius.playground.jpa.Advert;
import com.akelius.playground.jpa.Article;
import com.akelius.playground.jpa.BlogPost;
import com.akelius.playground.jpa.Book;
import com.akelius.playground.jpa.repos.AdvertRepo;
import com.akelius.playground.jpa.repos.ArticleRepo;
import com.akelius.playground.jpa.repos.BlogPostRepo;
import com.akelius.playground.jpa.repos.BookRepo;
import com.akelius.playground.jpa.repos.fetchtest.*;
import com.google.common.base.Stopwatch;
import org.hibernate.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class LibraryTest {

    private static final int LIBRARY_COUNT = 100;

    private static final int BOOKS_PER_TYPE = 10;

    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private BlogPostRepo blogPostRepo;

    @Autowired
    private AdvertRepo advertRepo;

    @Autowired
    private LibraryEagerJoinRepo libraryEagerJoinRepo;

    @Autowired
    private LibraryEagerSubSelectRepo libraryEagerSubSelectRepo;

    @Autowired
    private LibraryEagerBatchRepo libraryEagerBatchRepo;

    @Autowired
    private LibraryLazyBatchRepo libraryLazyBatchRepo;

    @Autowired
    private LibraryLazyDefaultRepo libraryLazyDefaultRepo;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ArticleRepo articleRepo;

    @BeforeEach
    void setUp() {
        for (int i = 0; i < LIBRARY_COUNT; i++) {
            final LibraryEagerJoin libraryEagerJoin = new LibraryEagerJoin();
            libraryEagerJoin.setName("libraryEagerJoin #" + i);

            final LibraryEagerSubSelect libraryEagerSubSelect = new LibraryEagerSubSelect();
            libraryEagerSubSelect.setName("libraryEagerSubSelect #" + i);

            final LibraryEagerBatch libraryEagerBatch = new LibraryEagerBatch();
            libraryEagerBatch.setName("libraryEagerBatch #" + i);

            final LibraryLazyBatch libraryLazyBatch = new LibraryLazyBatch();
            libraryLazyBatch.setName("libraryLazyBatch #" + i);

            final LibraryLazyDefault libraryLazyDefault = new LibraryLazyDefault();
            libraryLazyDefault.setName("libraryLazyDefault #" + i);

            for (int j = 0; j < BOOKS_PER_TYPE; j++) {
                final Book book = new Book();
                book.setTitle("Book title #" + i + "." + j);
                Book bookSaved = bookRepo.save(book);
                libraryEagerJoin.getBooks().add(bookSaved);
                libraryEagerSubSelect.getBooks().add(bookSaved);
                libraryEagerBatch.getBooks().add(bookSaved);
                libraryLazyBatch.getBooks().add(bookSaved);
                libraryLazyDefault.getBooks().add(bookSaved);

                final BlogPost blogPost = new BlogPost();
                blogPost.setTitle("Blog title #" + i + "." + j);
                BlogPost postSaved = blogPostRepo.save(blogPost);
                libraryEagerJoin.getBlogPosts().add(postSaved);
                libraryEagerSubSelect.getBlogPosts().add(postSaved);
                libraryEagerBatch.getBlogPosts().add(postSaved);
                libraryLazyBatch.getBlogPosts().add(postSaved);
                libraryLazyDefault.getBlogPosts().add(postSaved);

                final Advert advert = new Advert();
                advert.setTitle("advert #" + i + "." + j);
                Advert advertSaved = advertRepo.save(advert);
                libraryEagerJoin.getAdverts().add(advertSaved);
                libraryEagerSubSelect.getAdverts().add(advertSaved);
                libraryEagerBatch.getAdverts().add(advertSaved);
                libraryLazyBatch.getAdverts().add(advertSaved);
                libraryLazyDefault.getAdverts().add(advertSaved);

                final Article article = new Article();
                article.setTitle("advert #" + i + "." + j);
                Article articleSaved = articleRepo.save(article);
                libraryEagerJoin.getArticles().add(articleSaved);
                libraryEagerSubSelect.getArticles().add(articleSaved);
                libraryEagerBatch.getArticles().add(articleSaved);
                libraryLazyBatch.getArticles().add(articleSaved);
                libraryLazyDefault.getArticles().add(articleSaved);
            }
            libraryEagerJoinRepo.save(libraryEagerJoin);
            libraryEagerSubSelectRepo.save(libraryEagerSubSelect);
            libraryEagerBatchRepo.save(libraryEagerBatch);
            libraryLazyBatchRepo.save(libraryLazyBatch);
            libraryLazyDefaultRepo.save(libraryLazyDefault);
        }
    }

    @Test
    @Transactional
    void readEagerJoin() {
        entityManager.flush();
        entityManager.clear();

        Stopwatch stopwatch = Stopwatch.createStarted();
        // looking at the queries hibernate seems to use SELECT fetching for the collections.
        // maybe because there are multiple collections
        libraryEagerJoinRepo.findAll();
        stopwatch.stop();
        System.out.println("Found eager join libraries in " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + " ms");
    }

    @Test
    @Transactional
    void readEagerSubSelect() {
        entityManager.flush();
        entityManager.clear();

        Stopwatch stopwatch = Stopwatch.createStarted();
        libraryEagerSubSelectRepo.findAll();
        stopwatch.stop();
        System.out.println("Found eager sub select libraries in " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + " ms");
    }

    @Test
    @Transactional
    void readEagerBatch() {
        entityManager.flush();
        entityManager.clear();

        Stopwatch stopwatch = Stopwatch.createStarted();
        libraryEagerBatchRepo.findAll();
        stopwatch.stop();
        System.out.println("Found eager batch libraries in " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + " ms");
    }

    @Test
    @Transactional
    void readEagerSubSelectPaginated() {
        entityManager.flush();
        entityManager.clear();

        Stopwatch stopwatch = Stopwatch.createStarted();
        // does not consider the limit in the sub-select, loads everything
        // 1258 ms for 1000 libraries, BatchSize 5 and 10 elements per collection
        libraryEagerSubSelectRepo.findAll(PageRequest.of(0, 10, Sort.Direction.ASC, "id"));
        stopwatch.stop();
        System.out.println("Found eager sub select libraries paginated in " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + " ms");
    }

    @Test
    @Transactional
    void readEagerBatchPaginated() {
        entityManager.flush();
        entityManager.clear();

        Stopwatch stopwatch = Stopwatch.createStarted();
        // 276 ms for 1000 libraries, BatchSize 5 and 10 elements per collection
        libraryEagerBatchRepo.findAll(PageRequest.of(0, 10));
        stopwatch.stop();
        System.out.println("Found eager batch libraries paginated in " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + " ms");
    }

    @Test
    @Transactional
    void readEagerSubSelectWithCustomQuery() {
        entityManager.flush();
        entityManager.clear();

        Stopwatch stopwatch = Stopwatch.createStarted();
        // uses the custom query for sub-select
        libraryEagerSubSelectRepo.querySomeLibraries();
        stopwatch.stop();
        System.out.println("Found eager sub select libraries  with custom query in " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + " ms");
    }

    @Test
    @Transactional
    void readLazyBatch() {
        entityManager.flush();
        entityManager.clear();

        Stopwatch stopwatch = Stopwatch.createStarted();
        libraryLazyBatchRepo.findAll().forEach(l -> {
            l.getAdverts().stream().findFirst();
            l.getBooks().stream().findFirst();
            l.getBlogPosts().stream().findFirst();
            l.getArticles().stream().findFirst();
        });
        stopwatch.stop();
        System.out.println("Found lazy batch libraries in " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + " ms");
    }

    @Test
    @Transactional
    void readLazyDefault() {
        entityManager.flush();
        entityManager.clear();

        Stopwatch stopwatch = Stopwatch.createStarted();
        libraryLazyDefaultRepo.findAll().forEach(l -> {
            l.getAdverts().stream().findFirst();
            l.getBooks().stream().findFirst();
            l.getBlogPosts().stream().findFirst();
            l.getArticles().stream().findFirst();
        });
        stopwatch.stop();
        System.out.println("Found lazy default libraries in " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + " ms");
    }

    @Test
    @Transactional
    void readLazyBatchWithFetchGraph() {
        entityManager.flush();
        entityManager.clear();

        Stopwatch stopwatch = Stopwatch.createStarted();
        // uses a JOIN to fetch all collections, very slow
        libraryLazyBatchRepo.findAllDeep();
        stopwatch.stop();
        System.out.println("Found lazy batch libraries in " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + " ms");
    }

    @Test
    @Transactional
    void readEagerSubSelectWithFetchOverride() {
        entityManager.flush();
        entityManager.clear();

        // uses a JOIN to fetch all collections, very slow
        entityManager.unwrap(Session.class).enableFetchProfile("libraries-lazy-batch-graph-profile");
        Stopwatch stopwatch = Stopwatch.createStarted();
        libraryLazyBatchRepo.findAllDeep();
        stopwatch.stop();
        System.out.println("Found lazy batch libraries in " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + " ms");
    }
}
