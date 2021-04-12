package ch.ase21.salesapi.repository;

import ch.ase21.salesapi.entity.Coordinates;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoordinatesRepository extends CrudRepository<Coordinates, String> {
}
