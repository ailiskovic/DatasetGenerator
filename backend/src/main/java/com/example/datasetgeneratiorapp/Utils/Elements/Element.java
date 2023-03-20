package com.example.datasetgeneratiorapp.Utils.Elements;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Element class
 */
@Getter
@Setter
@AllArgsConstructor
public class Element {
    private final String type;
    private final String name;
    private final boolean increase;
    private final int id;


    @Override
    public String toString() {
        return "Element{" +
                "type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", increase=" + increase +
                ", id=" + id +
                '}';
    }
}
