package com.akelius.playground.jpa;

import com.akelius.playground.jpa.repos.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class AuthorTest {

  private static final int AUTHOR_COUNT = 50;
  private static final int BOOK_COUNT = 1500;
  @Autowired
  private BookRepo bookRepo;

  @Autowired
  private BlogPostRepo blogPostRepo;

  @Autowired
  private AdvertRepo advertRepo;

  @Autowired
  private AuthorRepo authorRepo;

  @Autowired
  private EntityManager entityManager;

  @Autowired
  private ArticleRepo articleRepo;

  @BeforeEach
  void setUp() {
    for (int i = 0; i < AUTHOR_COUNT; i++) {
      final Author author = new Author();
      author.setName("author #" + i);

      final Set<Text> publications = new HashSet<>();
      for (int j = 0; j < BOOK_COUNT; j++) {
        final Book book = new Book();
        book.setTitle("Book title #" + i + "." + j);
        publications.add(bookRepo.save(book));

        final BlogPost blogPost = new BlogPost();
        blogPost.setTitle("Blog title #" + i + "." + j);
        publications.add(blogPostRepo.save(blogPost));

        final Advert advert = new Advert();
        advert.setTitle("advert #" + i + "." + j);
        publications.add(advertRepo.save(advert));

        final Article article = new Article();
        article.setTitle("advert #" + i + "." + j);
        publications.add(articleRepo.save(article));
      }
      author.setPublications(publications);
      authorRepo.save(author);
    }
  }

  @Test
  @Transactional
  void read() {
    entityManager.flush();
    entityManager.clear();

    final long authorstart = System.currentTimeMillis();
    final List<Author> authors = authorRepo.findAll();
    System.out.println("author time: " + (System.currentTimeMillis() - authorstart));

    assertThat(authors).hasSize(AUTHOR_COUNT);

    authors.forEach(author -> {
              final long starttime = System.currentTimeMillis();
              final Set<Text> publications = author.getPublications();
              assertThat(publications).hasSize(4 * BOOK_COUNT);
              System.out.println("query time: " + (System.currentTimeMillis() - starttime));
              for (final Text text : publications) {
                assertThat(text.title).isNotBlank();
              }
            }
    );
  }

}
