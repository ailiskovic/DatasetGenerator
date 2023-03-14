package com.example.datasetgeneratiorapp.Utils.Elements;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class NormalDistributionElement extends Element{

    private final float muValue;
    private final float sigmaValue;

    @Builder
    public NormalDistributionElement(String type, String name, boolean increase, int id, float muValue,
                                     float sigmaValue)
    {
        super(type, name, increase, id);
        this.muValue = muValue;
        this.sigmaValue = sigmaValue;
    }

    @Override
    public String toString() {
        return "NormalDistributionElement{" +
                "muValue=" + muValue +
                ", sigmaValue=" + sigmaValue +
                "} " + super.toString();
    }
}
