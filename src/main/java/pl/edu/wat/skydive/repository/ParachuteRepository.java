package pl.edu.wat.skydive.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.edu.wat.skydive.entity.Parachute;

@Repository
public interface ParachuteRepository extends MongoRepository<Parachute, String>{
}
