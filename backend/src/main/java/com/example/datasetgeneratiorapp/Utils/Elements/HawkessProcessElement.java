package com.example.datasetgeneratiorapp.Utils.Elements;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Hawkess process element class
 */
@Setter
@Getter
public class HawkessProcessElement extends Element{

    private final double lambdaValue;
    private final double alphaValue;
    private final double betaValue;
    private List<Double> previousValues;
    @Builder
    public HawkessProcessElement(String type, String name, boolean increase, int id, double lambdaValue, double alphaValue,
                                 double betaValue) {
        super(type, name, increase, id);
        this.lambdaValue = lambdaValue;
        this.alphaValue = alphaValue;
        this.betaValue = betaValue;
        this.previousValues = new ArrayList<>();;
    }

    @Override
    public String toString() {
        return "HawkessProcessElement{" +
                "lambdaValue=" + lambdaValue +
                ", alphaValue=" + alphaValue +
                ", betaValue=" + betaValue +
                ", previousValues=" + previousValues +
                "} " + super.toString();
    }

    public void addPreviousValue(Double value)
    {
        this.previousValues.add(value);
    }

    public List<Double> getPreviousValues()
    {
        return this.previousValues;
    }
}
