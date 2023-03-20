package com.example.datasetgeneratiorapp.Utils.Elements;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Id element class
 */
@Getter
@Setter
public class IdElement extends Element{
    private final int startValue;

    @Builder
    public IdElement(String type, String name, boolean increase, int id, int startValue) {
        super(type, name, increase, id);
        this.startValue = startValue;
    }

    @Override
    public String toString() {
        return "IdElement{" +
                "startValue=" + startValue +
                "} " + super.toString();
    }
}
