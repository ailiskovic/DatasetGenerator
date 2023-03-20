package com.example.datasetgeneratiorapp.Utils.Elements;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Cauchy distribution element class
 */
@Setter
@Getter
public class CauchyDistributionElement extends Element{

    private final float x0Value;
    private final float lambdaValue;
    @Builder
    public CauchyDistributionElement(String type, String name, boolean increase, int id, float x0Value, float lambdaValue)
    {
        super(type, name, increase, id);
        this.x0Value = x0Value;
        this.lambdaValue = lambdaValue;
    }

    @Override
    public String toString() {
        return "CauchyDistributionElement{" +
                "x0Value=" + x0Value +
                ", lambdaValue=" + lambdaValue +
                "} " + super.toString();
    }
}
