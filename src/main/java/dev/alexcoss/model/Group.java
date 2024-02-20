package dev.alexcoss.model;

import lombok.Data;

@Data
public class Group {

    private int id;
    private String name;

    @Override
    public String toString() {
        return id + " " + name;
    }
}
