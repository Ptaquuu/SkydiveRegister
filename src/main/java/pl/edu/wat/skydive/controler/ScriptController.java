package pl.edu.wat.skydive.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.wat.skydive.service.ScriptService;

@RestController
@CrossOrigin
@RequestMapping("/api/script")
public class ScriptController {
    private final ScriptService scriptService;

    @Autowired
    public ScriptController(ScriptService scriptService) {
        this.scriptService = scriptService;
    }

    @PutMapping()
    public ResponseEntity<String> execScript() {
        String script =
                """
                var Skydiver = Java.type('pl.edu.wat.skydive.entity.Skydiver');
                var Parachute = Java.type('pl.edu.wat.skydive.entity.Parachute');
                var Reflection = Java.type('pl.edu.wat.skydive.reflection.Reflection');
                var Set = Java.type('java.util.Set');
                function parachuteType(){
                    for(parachute of parachuteRepository.findAll()){             
                        var parachuteSize = parachute.getSize();
                        if (parseInt(parachuteSize) > 170 ){
                            var parachuteType = "Student";
                        }
                        else {
                            var parachuteType = "Sport";
                        }
                        parachute.setParachuteType(parachuteType);            
                        parachuteRepository.save(parachute);
                    }
                    return parachuteRepository.findAll();
                }
                parachuteType();
                
                """;
        return new ResponseEntity<>(scriptService.exec(script), HttpStatus.OK) ;
    }
}