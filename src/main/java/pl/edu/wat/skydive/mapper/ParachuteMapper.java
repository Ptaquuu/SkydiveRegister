package pl.edu.wat.skydive.mapper;

import org.springframework.stereotype.Service;
import pl.edu.wat.skydive.dto.ParachuteRequest;
import pl.edu.wat.skydive.dto.ParachuteResponse;
import pl.edu.wat.skydive.entity.Parachute;

@Service
public class ParachuteMapper {

    public Parachute map(ParachuteRequest parachuteRequest) {
        Parachute parachute = new Parachute();
        parachute.setName(parachuteRequest.getName());
        parachute.setSize(parachuteRequest.getSize());
        fillParachuteRequest(parachute, parachuteRequest);
        return parachute;
    }

    private void fillParachuteRequest(Parachute parachute, ParachuteRequest parachuteRequest) {

    }

    public ParachuteResponse map(Parachute parachute) {
        ParachuteResponse parachuteResponse = new ParachuteResponse(parachute.getId(), parachute.getName(), parachute.getSize());
        fillParachute(parachuteResponse, parachute);
        return parachuteResponse;
    }

    private void fillParachute(ParachuteResponse parachuteResponse, Parachute parachute) {

    }

}