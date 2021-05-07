package ch.ase21.salesapi.repository;

import ch.ase21.salesapi.entity.Property;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PropertyRepository extends CrudRepository<Property, String> {
  Optional<Property> findByNumberAndBorough(Integer number, Integer borough);
  Iterable<Property> findPropertiesByNeighborhood(String neighborhood);
}
