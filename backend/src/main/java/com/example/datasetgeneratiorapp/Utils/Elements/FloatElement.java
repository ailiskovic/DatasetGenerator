package com.example.datasetgeneratiorapp.Utils.Elements;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Float element class
 */
@Getter
@Setter
public class FloatElement extends Element{
    private final float startValue;
    private final float unitValue;

    @Builder
    public FloatElement(String type, String name, boolean increase, int id, float startValue, float unitValue) {
        super(type, name, increase, id);
        this.startValue = startValue;
        this.unitValue = unitValue;
    }

    @Override
    public String toString() {
        return "FloatElement{" +
                "startValue=" + startValue +
                ", unitValue=" + unitValue +
                "} " + super.toString();
    }
}
