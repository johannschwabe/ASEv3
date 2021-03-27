package ch.ase21.airbnbapi.repository;

import ch.ase21.airbnbapi.entity.Author;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends CrudRepository<Author, String> {
}
