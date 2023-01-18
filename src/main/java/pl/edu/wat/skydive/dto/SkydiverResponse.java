package pl.edu.wat.skydive.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SkydiverResponse {
    private String id;
    private String name;
    private String surname;
    private ParachuteResponse parachute;
}
