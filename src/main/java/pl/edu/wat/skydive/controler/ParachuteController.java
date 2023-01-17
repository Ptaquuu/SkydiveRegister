package pl.edu.wat.skydive.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.skydive.dto.ParachuteRequest;
import pl.edu.wat.skydive.dto.ParachuteResponse;
import pl.edu.wat.skydive.entity.Parachute;
import pl.edu.wat.skydive.exception.EntityNotFound;
import pl.edu.wat.skydive.service.ParachuteService;
import pl.edu.wat.skydive.service.SkydiverService;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/parachute")
public class ParachuteController {

    private final ParachuteService parachuteService;

    @Autowired
    public ParachuteController(ParachuteService parachuteService, SkydiverService skydiverService) {
        this.parachuteService = parachuteService;
    }

    @GetMapping
    public ResponseEntity <List<ParachuteResponse>> getAllParachute() {
        List<ParachuteResponse> parachuteOptional = parachuteService.getAll();
        return new ResponseEntity<>(parachuteOptional, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<ParachuteResponse> getParachuteByIdVar(@PathVariable String id) {
        Optional<ParachuteResponse> parachuteOptional = parachuteService.getParachuteById(id);
        if (parachuteOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(parachuteOptional.get(), HttpStatus.OK);
    }

    @GetMapping("{id}/size")
    public ResponseEntity<String> getParachuteSurnameById(@PathVariable String id) {
        Optional<ParachuteResponse> parachuteOptional = parachuteService.getParachuteById(id);
        if (parachuteOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(parachuteOptional.get().getSize(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<String> createParachute(@RequestBody ParachuteRequest parachuteRequest) {
        return new ResponseEntity<>(parachuteService.save(parachuteRequest).getId(), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<ParachuteResponse> updateParachute(@PathVariable String id, @RequestParam(required = false) String name, @RequestParam(required = false) String size) {
        try {
            return new ResponseEntity<>(parachuteService.update(id, name, size), HttpStatus.OK);
        } catch (EntityNotFound e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteParachute(@PathVariable String id) {
        try {
            parachuteService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFound e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}