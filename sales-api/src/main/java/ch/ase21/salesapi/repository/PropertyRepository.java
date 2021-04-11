package ch.ase21.salesapi.repository;

import ch.ase21.salesapi.entity.Property;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyRepository extends CrudRepository<Property, String> {
}
