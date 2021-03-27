package ch.ase21.airbnbapi.repository;

import ch.ase21.airbnbapi.entity.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<Book, String> {
}
