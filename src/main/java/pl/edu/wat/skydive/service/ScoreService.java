package pl.edu.wat.skydive.service;

import org.springframework.stereotype.Service;
import pl.edu.wat.skydive.entity.Skydiver;

import java.util.Random;

@Service
public class ScoreService {
    public Integer getScore(Skydiver skydiver) {
        return new Random().nextInt(10); //TODO
    }
}