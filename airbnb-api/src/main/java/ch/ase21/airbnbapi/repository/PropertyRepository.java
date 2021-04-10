package ch.ase21.airbnbapi.repository;

import ch.ase21.airbnbapi.entity.Property;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyRepository extends CrudRepository<Property, Long> {
}
