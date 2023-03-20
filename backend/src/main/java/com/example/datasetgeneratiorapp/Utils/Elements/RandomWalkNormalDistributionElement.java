package com.example.datasetgeneratiorapp.Utils.Elements;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 *  Random walk normal distribution element class
 */
@Setter
@Getter
public class RandomWalkNormalDistributionElement extends Element{

    private final float x0Value;
    private final float muValue;
    private final float sigmaValue;
    private float previousValue;

    @Builder
    public RandomWalkNormalDistributionElement(String type, String name, boolean increase, int id, float x0Value,
                                               float muValue, float sigmaValue) {
        super(type, name, increase, id);
        this.x0Value = x0Value;
        this.muValue = muValue;
        this.sigmaValue = sigmaValue;
    }

    @Override
    public String toString() {
        return "RandomWalkNormalDistributionElement{" +
                "x0Value=" + x0Value +
                ", muValue=" + muValue +
                ", sigmaValue=" + sigmaValue +
                "} " + super.toString();
    }
}
