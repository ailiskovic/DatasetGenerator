package com.example.datasetgeneratiorapp.Utils.Elements;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Integer element class
 */
@Setter
@Getter
public class IntegerElement extends Element {
        private final int startValue;
        private final int unitValue;

    @Builder
    public IntegerElement(String type, String name, boolean increase, int id, int startValue, int unitValue) {
        super(type, name, increase, id);
        this.startValue = startValue;
        this.unitValue = unitValue;
    }

    @Override
    public String toString() {
        return "IntegerElement{" +
                "startValue=" + startValue +
                ", unitValue=" + unitValue +
                "} " + super.toString();
    }
}
