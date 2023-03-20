package com.example.datasetgeneratiorapp.Utils.Elements;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Binomial distribution element class
 */
@Setter
@Getter
public class BinomialDistributionElement extends Element{

    private final int nTrialsValue;
    private final float pValue;

    @Builder
    public BinomialDistributionElement(String type, String name, boolean increase, int id, int nTrialsValue,
                                       float pValue) {
        super(type, name, increase, id);
        this.nTrialsValue = nTrialsValue;
        this.pValue = pValue;
    }

    @Override
    public String toString() {
        return "BinomialDistributionElement{" +
                "nTrialsValue=" + nTrialsValue +
                ", pValue=" + pValue +
                "} " + super.toString();
    }
}
