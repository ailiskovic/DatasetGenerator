package com.example.datasetgeneratiorapp.Utils.Elements;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TimeStampElement extends Element{

    private final Date dateTime;
    private final int unitValue;
    private final String timeUnit;

    @Builder
    public TimeStampElement(String type, String name, boolean increase, int id, Date dateTime, int unitValue, String timeUnit) {
        super(type, name, increase, id);
        this.dateTime = dateTime;
        this.unitValue = unitValue;
        this.timeUnit = timeUnit;
    }

    @Override
    public String toString() {
        return "TimeStampElement{" +
                "dateTime=" + dateTime +
                ", unitValue=" + unitValue +
                "} " + super.toString();
    }
}
