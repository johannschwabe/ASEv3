package ch.ase21.airbnbapi.bookdetails;

import ch.ase21.airbnbapi.entity.Author;
import ch.ase21.airbnbapi.entity.Book;
import ch.ase21.airbnbapi.repository.AuthorRepository;
import ch.ase21.airbnbapi.repository.BookRepository;
import graphql.schema.DataFetcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GraphQLDataFetchers {

//  private static final List<Map<String, String>> books = Arrays.asList(
//      ImmutableMap.of("id", "book-1",
//          "name", "Harry Potter and the Philosopher's Stone",
//          "pageCount", "223",
//          "authorId", "author-1"),
//      ImmutableMap.of("id", "book-2",
//          "name", "Moby Dick",
//          "pageCount", "635",
//          "authorId", "author-2"),
//      ImmutableMap.of("id", "book-3",
//          "name", "Interview with the vampire",
//          "pageCount", "371",
//          "authorId", "author-3")
//  );
//
//  private static final List<Map<String, String>> authors = Arrays.asList(
//      ImmutableMap.of("id", "author-1",
//          "firstName", "Joanne",
//          "lastName", "Rowling"),
//      ImmutableMap.of("id", "author-2",
//          "firstName", "Herman",
//          "lastName", "Melville"),
//      ImmutableMap.of("id", "author-3",
//          "firstName", "Anne",
//          "lastName", "Rice")
//  );
  
  @Autowired
  BookRepository bookRepository;
  @Autowired
  AuthorRepository authorRepository;

  public DataFetcher<Book> getBookByIdDataFetcher() {
    return dataFetchingEnvironment -> {
      String bookId = dataFetchingEnvironment.getArgument("id");
      return bookRepository
          .findById(bookId)
          .orElse(null);
    };
  }

  public DataFetcher<Author> getAuthorDataFetcher() {
    return dataFetchingEnvironment -> {
      Book book = dataFetchingEnvironment.getSource();
      String authorId = book.getAuthorId();
      return authorRepository
          .findById(authorId)
          .orElse(null);
    };
  }

  public DataFetcher<String> getPageCountDataFetcher() {
    return dataFetchingEnvironment -> {
      Book book = dataFetchingEnvironment.getSource();
      return book.getTotalPages();
    };
  }
}
