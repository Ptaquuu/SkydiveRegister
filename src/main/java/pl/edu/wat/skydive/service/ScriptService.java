package pl.edu.wat.skydive.service;

import lombok.extern.slf4j.Slf4j;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.PolyglotException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.wat.skydive.repository.SkydiverRepository;
import pl.edu.wat.skydive.repository.ParachuteRepository;

@Service
@Slf4j
public class ScriptService {
    private final SkydiverRepository skydiverRepository;
    private final ParachuteRepository parachuteRepository;

    @Autowired
    public ScriptService(SkydiverRepository skydiverRepository, ParachuteRepository parachuteRepository) {
        this.skydiverRepository = skydiverRepository;
        this.parachuteRepository = parachuteRepository;
    }

    public String exec(String script) {
        try (Context context = Context.newBuilder("js")
                .allowAllAccess(true)
                .build()) {
            var bindings = context.getBindings("js");
            bindings.putMember("skydiverRepository", skydiverRepository);
            bindings.putMember("parachuteRepository", parachuteRepository);
            return context.eval("js", script).toString();
        } catch (PolyglotException e) {
            log.error("Error executing", e);
            return e.getMessage() + "\n" + e.getSourceLocation().toString();
        }
    }
}