package dev.alexcoss.service.generator;

import dev.alexcoss.dto.GroupDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class GroupsGenerator {
    @Value("${data.groups.count}")
    private int groupCount;
    @Value("${data.groups.quantity_characters}")
    private int quantityCharacters;
    @Value("${data.groups.quantity_numbers}")
    private int quantityNumbers;
    @Value("${data.groups.char_first}")
    private char charFirst;
    @Value("${data.groups.char_last}")
    private char charLast;

    private final Random random = new Random();

    public List<GroupDTO> generateGroupList() {
        List<GroupDTO> groupList = new ArrayList<>();

        for (int i = 0; i < groupCount; i++) {
            GroupDTO group = generateRandomName();
            groupList.add(group);
        }

        return groupList;
    }

    private GroupDTO generateRandomName() {
        StringBuilder nameBuilder = new StringBuilder();

        for (int i = 0; i < quantityCharacters; i++) {
            nameBuilder.append(generateRandomCharacter());
        }

        nameBuilder.append('-');

        for (int i = 0; i < quantityNumbers; i++) {
            nameBuilder.append(generateRandomDigit());
        }

        GroupDTO group = new GroupDTO();
        group.setName(nameBuilder.toString());

        return group;
    }

    private int generateRandomDigit() {
        return random.nextInt(10);
    }

    private char generateRandomCharacter() {
        return (char) (charFirst + random.nextInt(charLast - charFirst + 1));
    }
}
