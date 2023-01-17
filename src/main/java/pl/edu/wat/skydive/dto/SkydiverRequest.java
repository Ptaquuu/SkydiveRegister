package pl.edu.wat.skydive.dto;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.MongoId;
import pl.edu.wat.skydive.entity.Parachute;

@Data
public class SkydiverRequest {
    private String name;
    private String surname;
    private String parachuteId;
}

