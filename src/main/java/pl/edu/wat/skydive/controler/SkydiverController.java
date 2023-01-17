package pl.edu.wat.skydive.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.wat.skydive.dto.ParachuteRequest;
import pl.edu.wat.skydive.dto.ParachuteResponse;
import pl.edu.wat.skydive.dto.SkydiverRequest;
import pl.edu.wat.skydive.dto.SkydiverResponse;
import pl.edu.wat.skydive.exception.EntityNotFound;
import pl.edu.wat.skydive.service.SkydiverService;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/skydiver")
public class SkydiverController {
    private final SkydiverService skydiverService;

    @Autowired
    public SkydiverController(SkydiverService skydiverService) {
        this.skydiverService = skydiverService;
    }

    @GetMapping()
    public ResponseEntity<List<SkydiverResponse>> fetchAllSkydiver(){
        List<SkydiverResponse> parachuteOptional = skydiverService.getAll();
        return new ResponseEntity<>(parachuteOptional, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<String> createSkydiver(@RequestBody SkydiverRequest parachuteRequest) {
        try {
            return new ResponseEntity<>(skydiverService.save(parachuteRequest).getId(), HttpStatus.CREATED);
        } catch (EntityNotFound e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<SkydiverResponse> updateSkydiver(@PathVariable String id,@RequestBody SkydiverRequest skydiverRequest) {
        try {
            return new ResponseEntity<>(skydiverService.update(id, skydiverRequest), HttpStatus.OK);
        } catch (EntityNotFound e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSkydiver(@PathVariable String id) {
        try {
            skydiverService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFound e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
