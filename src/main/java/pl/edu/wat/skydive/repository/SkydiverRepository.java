package pl.edu.wat.skydive.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.edu.wat.skydive.entity.Skydiver;
import pl.edu.wat.skydive.entity.Parachute;

@Repository
public interface SkydiverRepository extends MongoRepository<Skydiver, String> {
}
