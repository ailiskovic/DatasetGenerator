package com.example.datasetgeneratiorapp.Utils.Elements;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Random walk cauchy distribution element class
 */
@Setter
@Getter
public class RandomWalkCauchyDistributionElement extends Element{

    private final float x0Value;
    private final float lambdaValue;
    private float previousValue;
    @Builder
    public RandomWalkCauchyDistributionElement(String type, String name, boolean increase, int id,
                                               float x0Value, float lambdaValue) {
        super(type, name, increase, id);
        this.x0Value = x0Value;
        this.lambdaValue = lambdaValue;
    }

    @Override
    public String toString() {
        return "RandomWalkCauchyDistributionElement{" +
                ", x0Value=" + x0Value +
                ", lambdaValue=" + lambdaValue +
                ", previousValue=" + previousValue +
                "} " + super.toString();
    }
}
