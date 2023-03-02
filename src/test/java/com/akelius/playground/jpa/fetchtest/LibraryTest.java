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

import javax.persistence.EntityManager;
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
    private LibraryLazyBatchJoinColumnRepo libraryLazyBatchJoinColumnRepo;

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

            final LibraryLazyBatchJoinColumn libraryLazyBatchJoinColumn = new LibraryLazyBatchJoinColumn();
            libraryLazyBatchJoinColumn.setName("libraryLazyBatchJoinColumn #" + i);

            for (int j = 0; j < BOOKS_PER_TYPE; j++) {
                final Book book = new Book();
                book.setTitle("Book title #" + i + "." + j);
                Book bookSaved = bookRepo.save(book);
                libraryEagerJoin.getBooks().add(bookSaved);
                libraryEagerSubSelect.getBooks().add(bookSaved);
                libraryEagerBatch.getBooks().add(bookSaved);
                libraryLazyBatchJoinColumn.getBooks().add(bookSaved);
                libraryLazyDefault.getBooks().add(bookSaved);
                libraryLazyBatch.getBooks().add(bookSaved);

                final BlogPost blogPost = new BlogPost();
                blogPost.setTitle("Blog title #" + i + "." + j);
                BlogPost postSaved = blogPostRepo.save(blogPost);
                libraryEagerJoin.getBlogPosts().add(postSaved);
                libraryEagerSubSelect.getBlogPosts().add(postSaved);
                libraryEagerBatch.getBlogPosts().add(postSaved);
                libraryLazyBatchJoinColumn.getBlogPosts().add(postSaved);
                libraryLazyDefault.getBlogPosts().add(postSaved);
                libraryLazyBatch.getBlogPosts().add(postSaved);

                final Advert advert = new Advert();
                advert.setTitle("advert #" + i + "." + j);
                Advert advertSaved = advertRepo.save(advert);
                libraryEagerJoin.getAdverts().add(advertSaved);
                libraryEagerSubSelect.getAdverts().add(advertSaved);
                libraryEagerBatch.getAdverts().add(advertSaved);
                libraryLazyBatchJoinColumn.getAdverts().add(advertSaved);
                libraryLazyDefault.getAdverts().add(advertSaved);
                libraryLazyBatch.getAdverts().add(advertSaved);

                final Article article = new Article();
                article.setTitle("advert #" + i + "." + j);
                Article articleSaved = articleRepo.save(article);
                libraryEagerJoin.getArticles().add(articleSaved);
                libraryEagerSubSelect.getArticles().add(articleSaved);
                libraryEagerBatch.getArticles().add(articleSaved);
                libraryLazyBatchJoinColumn.getArticles().add(articleSaved);
                libraryLazyDefault.getArticles().add(articleSaved);
                libraryLazyBatch.getArticles().add(articleSaved);
            }
            libraryEagerJoinRepo.save(libraryEagerJoin);
            libraryEagerSubSelectRepo.save(libraryEagerSubSelect);
            libraryEagerBatchRepo.save(libraryEagerBatch);
            libraryLazyBatchRepo.save(libraryLazyBatch);
            libraryLazyDefaultRepo.save(libraryLazyDefault);
            libraryLazyBatchJoinColumnRepo.save(libraryLazyBatchJoinColumn);
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
        // 1346 ms, 847 ms, 1067 ms, 837 ms for 100 libraries with 10 elements per collection
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
        // 131 ms, 181 ms, 176 ms, 130 ms for 100 libraries with 10 elements per collection
        libraryEagerSubSelectRepo.findAll();
        stopwatch.stop();
        System.out.println("Found eager sub select libraries in " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + " ms");
    }

    @Test
    @Transactional
    void readEagerBatch() {
        entityManager.flush();
        entityManager.clear();

        // 226 ms, 243 ms, 199 ms, 208 ms for 100 libraries with 10 elements per collection, batch size 20
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
        // 1258 ms for 1000 libraries
        // 397 ms, 467 ms, 498 ms, 462 ms for 10 libraries
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
        // 135 ms, 98 ms, 105 ms, 168 ms for 100 libraries with 10 elements per collection
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
        // 183 ms, 239 ms, 337 ms, 225 ms
        libraryEagerSubSelectRepo.querySomeLibraries();
        stopwatch.stop();
        System.out.println("Found eager sub select libraries  with custom query in " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + " ms");
    }

    @Test
    @Transactional
    void readLazyBatch() {
        entityManager.flush();
        entityManager.clear();

        // 194 ms, 193 ms, 302 ms, 230 ms for 100 libraries with 10 elements per collection, batch size 20
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
    void readLazyBatchJoinColumn() {
        entityManager.flush();
        entityManager.clear();

        // 386 ms, 381 ms, 298 ms, 200 ms for 100 libraries with 10 elements per collection, batch size 20
        Stopwatch stopwatch = Stopwatch.createStarted();
        libraryLazyBatchJoinColumnRepo.findAll().forEach(l -> {
            l.getAdverts().stream().findFirst();
            l.getBooks().stream().findFirst();
            l.getBlogPosts().stream().findFirst();
            l.getArticles().stream().findFirst();
        });
        stopwatch.stop();
        System.out.println("Found lazy batch libraries using join column in " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + " ms");
    }

    @Test
    @Transactional
    void readLazyDefault() {
        entityManager.flush();
        entityManager.clear();

        // 1138 ms, 950 ms, 967 ms, 1355 ms for 100 libraries with 10 elements per collection
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
        // 7594 ms, 8523 ms, 8843 ms, 8025 ms for 100 libraries with 10 elements per collection
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
        // 9049 ms, 8621 ms for 100 libraries with 10 elements per collection
        entityManager.unwrap(Session.class).enableFetchProfile("libraries-lazy-batch-graph-profile");
        Stopwatch stopwatch = Stopwatch.createStarted();
        libraryLazyBatchRepo.findAllDeep();
        stopwatch.stop();
        System.out.println("Found lazy batch libraries in " + stopwatch.elapsed(TimeUnit.MILLISECONDS) + " ms");
    }
}
