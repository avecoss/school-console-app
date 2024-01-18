package dev.alexcoss.service;

import dev.alexcoss.model.Group;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GroupsGenerator {
    private static final int GROUP_COUNT = 10;
    private static final int QUANTITY_CHARACTERS = 2;
    private static final int QUANTITY_NUMBERS = 2;
    private static final char CHAR_FIRST = 'A';
    private static final char CHAR_LAST = 'Z';

    private final Random random = new Random();

    public List<Group> generateGroupList() {
        List<Group> groupList = new ArrayList<>();

        for (int i = 0; i < GROUP_COUNT; i++) {
            Group group = generateRandomName();
            groupList.add(group);
        }

        return groupList;
    }

    private Group generateRandomName() {
        StringBuilder nameBuilder = new StringBuilder();

        for (int i = 0; i < QUANTITY_CHARACTERS; i++) {
            nameBuilder.append(generateRandomCharacter());
        }

        nameBuilder.append('-');

        for (int i = 0; i < QUANTITY_NUMBERS; i++) {
            nameBuilder.append(generateRandomDigit());
        }

        Group group = new Group();
        group.setName(nameBuilder.toString());

        return group;
    }

    private int generateRandomDigit() {
        return random.nextInt(10);
    }

    private char generateRandomCharacter() {
        return (char) (CHAR_FIRST + random.nextInt(CHAR_LAST - CHAR_FIRST + 1));
    }
}
