package pl.edu.wat.skydive.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
public class Parachute {
    @MongoId
    private String id;
    private String name;
    private String size;

    /*public Parachute (String name, String size){
        this.name = name;
        this.size = size;
    }*/
}

