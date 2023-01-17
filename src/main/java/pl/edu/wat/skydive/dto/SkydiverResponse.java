package pl.edu.wat.skydive.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.MongoId;
import pl.edu.wat.skydive.entity.Parachute;

@Data
@AllArgsConstructor
public class SkydiverResponse {
    private String id;
    private String name;
    private String surname;
    private ParachuteResponse parachute;
}
