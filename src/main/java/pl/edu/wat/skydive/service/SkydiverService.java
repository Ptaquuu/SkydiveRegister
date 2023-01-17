package pl.edu.wat.skydive.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wat.skydive.dto.SkydiverRequest;
import pl.edu.wat.skydive.dto.SkydiverResponse;
import pl.edu.wat.skydive.dto.ParachuteResponse;
import pl.edu.wat.skydive.entity.Skydiver;
import pl.edu.wat.skydive.entity.Parachute;
import pl.edu.wat.skydive.exception.EntityNotFound;
import pl.edu.wat.skydive.repository.SkydiverRepository;
import pl.edu.wat.skydive.repository.ParachuteRepository;
import pl.edu.wat.skydive.mapper.ParachuteMapper;

import java.util.List;
import java.util.Optional;

@Service
public class SkydiverService {
    private final SkydiverRepository skydiverRepository;
    private final ParachuteRepository parachuteRepository;
    private final ParachuteMapper parachuteMapper;

    @Autowired
    public SkydiverService(SkydiverRepository skydiverRepository, ParachuteRepository parachuteRepository,ParachuteMapper parachuteMapper) {
        this.skydiverRepository = skydiverRepository;
        this.parachuteRepository = parachuteRepository;
        this.parachuteMapper = parachuteMapper;
    }

    public Optional<ParachuteResponse> getParachuteById(String id) {
        return parachuteRepository.findById(id).map(parachuteMapper::map);
    }

    public SkydiverResponse getSkydiverById(String id) throws EntityNotFound {
        Skydiver skydiver = skydiverRepository.findById(id).orElseThrow(EntityNotFound::new);
        Parachute parachute = parachuteRepository.findById(skydiver.getParachuteId()).orElseThrow(EntityNotFound::new);

        return new SkydiverResponse(
                skydiver.getId(),
                skydiver.getName(),
                skydiver.getSurname(),
                parachuteMapper.map(parachute));
                //new ParachuteResponse(parachute.getId(), parachute.getName(), parachute.getSize()));
    }

    public SkydiverResponse save(SkydiverRequest skydiverRequest) throws EntityNotFound {
        Skydiver skydiver = new Skydiver();
        skydiver.setName(skydiverRequest.getName());

        Parachute parachute = parachuteRepository.findById(skydiverRequest.getParachuteId())
                .orElseThrow(EntityNotFound::new);
        skydiver.setSurname(skydiverRequest.getSurname());
        skydiver.setParachuteId(parachute.getId());
        skydiver = skydiverRepository.save(skydiver);
        System.out.println(skydiver);
        return new SkydiverResponse(
                skydiver.getId(),
                skydiver.getName(),
                skydiver.getSurname(),

                new ParachuteResponse(parachute.getId(), parachute.getName(), parachute.getSize()));
    }

    public List<SkydiverResponse> getAll() {

        return skydiverRepository.findAll()
                .stream()
                .map(this::toSkydiverResponse)
                .flatMap(Optional::stream)
                .toList();
    }

    private Optional<SkydiverResponse> toSkydiverResponse(Skydiver skydiver) {
        try {
            Parachute parachute = parachuteRepository.findById(skydiver.getParachuteId()).orElseThrow(EntityNotFound::new);
            return Optional.of(
                    new SkydiverResponse(skydiver.getId(), skydiver.getName(),skydiver.getSurname(), new ParachuteResponse(parachute.getId(), parachute.getName(), parachute.getSize()))
            );
        } catch (EntityNotFound e) {
            return Optional.empty();
        }
    }

    public SkydiverResponse update(String id, SkydiverRequest skydiverRequest ) throws EntityNotFound {
        Optional<Skydiver> skydiverOptional = skydiverRepository.findById(id);

        if (skydiverOptional.isEmpty()){
            throw new EntityNotFound();
        }

        Skydiver skydiver = skydiverOptional.get();
        skydiver.setName(skydiverRequest.getName());
        skydiver.setSurname(skydiverRequest.getSurname());

        Parachute parachute = parachuteRepository.findById(skydiverRequest.getParachuteId()).orElseThrow(EntityNotFound::new);

        skydiver.setParachuteId(parachute.getId());
        skydiver = skydiverRepository.save(skydiver);

        return new SkydiverResponse(skydiver.getId(), skydiver.getName(), skydiver.getSurname(), new ParachuteResponse(parachute.getId(), parachute.getName(), parachute.getSize()));
    }

    public void delete(String id) throws EntityNotFound {
        if(!skydiverRepository.existsById(id)) {
            throw new EntityNotFound();
        }
        skydiverRepository.deleteById(id);
    }

}
