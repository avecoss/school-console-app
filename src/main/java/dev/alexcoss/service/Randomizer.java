package dev.alexcoss.service;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public abstract class Randomizer {

    private final Random random = new Random();

    int getRandomInteger(int max, int min) {
        return random.nextInt(max - min + 1) + min;
    }

    void shuffleCollections(List<?>... collections) {
        for (List<?> collection : collections) {
            Collections.shuffle(collection);
        }
    }
}
