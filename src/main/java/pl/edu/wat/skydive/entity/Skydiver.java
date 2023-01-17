package pl.edu.wat.skydive.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
public class Skydiver {
    @MongoId
    private String id;
    private String name;
    private String surname;
    private String parachuteId;
}
