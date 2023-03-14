package com.example.datasetgeneratiorapp.Utils.Elements;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MathElement extends Element{

    private final String formulaValue;
    @Builder
    public MathElement(String type, String name, boolean increase, int id, String formulaValue) {
        super(type, name, increase, id);
        this.formulaValue = formulaValue;
    }

    @Override
    public String toString() {
        return "MathElement{" +
                "formulaValue='" + formulaValue + '\'' +
                "} " + super.toString();
    }
}
