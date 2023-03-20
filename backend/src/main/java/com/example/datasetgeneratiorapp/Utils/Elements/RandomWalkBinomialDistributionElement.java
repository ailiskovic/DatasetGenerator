package com.example.datasetgeneratiorapp.Utils.Elements;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Random walk binomial distribution element class
 */
@Setter
@Getter
public class RandomWalkBinomialDistributionElement extends Element{

    private final float x0Value;
    private final int nTrialsValue;
    private final float pValue;
    private float previousValue;

    @Builder
    public RandomWalkBinomialDistributionElement(String type, String name, boolean increase, int id, float x0Value,
                                                int nTrialsValue, float pValue) {
        super(type, name, increase, id);
        this.x0Value = x0Value;
        this.nTrialsValue = nTrialsValue;
        this.pValue = pValue;
    }
}
