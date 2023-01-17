package pl.edu.wat.skydive.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.edu.wat.skydive.dto.ParachuteRequest;
import pl.edu.wat.skydive.dto.ParachuteResponse;
import pl.edu.wat.skydive.entity.Parachute;
import pl.edu.wat.skydive.exception.EntityNotFound;
import pl.edu.wat.skydive.mapper.ParachuteMapper;
import pl.edu.wat.skydive.repository.ParachuteRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ParachuteService {
    private final ParachuteRepository parachuteRepository;
    private final ParachuteMapper parachuteMapper;

    @Autowired
    public ParachuteService(ParachuteRepository parachuteRepository, ParachuteMapper parachuteMapper) {
        this.parachuteRepository = parachuteRepository;
        this.parachuteMapper = parachuteMapper;
    }

    public Optional<ParachuteResponse> getParachuteById(String id) {
        return parachuteRepository.findById(id).map(parachuteMapper::map);
    }

    public ParachuteResponse save(ParachuteRequest parachuteRequest) {
        Parachute parachute = parachuteMapper.map(parachuteRequest);
        parachute = parachuteRepository.save(parachute);
        return parachuteMapper.map(parachute);
    }

    public List<ParachuteResponse> getAll() {
        return parachuteRepository.findAll()
                .stream()
                .map(parachuteMapper::map)
                .toList();
    }

    public ParachuteResponse update(String id, String name, String size) throws EntityNotFound {
        Parachute parachute = parachuteRepository.findById(id).orElseThrow(EntityNotFound::new);
        if(StringUtils.isNotBlank(name)) {
            parachute.setName(name);
        }

        if(StringUtils.isNotBlank(size)) {
            parachute.setSize(size);
        }

        parachute = parachuteRepository.save(parachute);
        return parachuteMapper.map(parachute);
    }
    public void delete(String id) throws EntityNotFound {
        if(!parachuteRepository.existsById(id)) {
            throw new EntityNotFound();
        }
        parachuteRepository.deleteById(id);
    }
}
