package com.example.datasetgeneratiorapp.Utils;

import java.util.Arrays;
import java.util.List;

/**
 * Helper class which contains the information about types of elements and time units
 */
public final class Util {

    public static final List<String> typeOfElements = Arrays.asList("ID","Integer","Float","Timestamp", "Normal_Distribution",
            "Binomial_Distribution", "Cauchy_Distribution", "Random_Walk", "Hawkess_Process", "Math", "Random_Walk_Normal_Distribution",
            "Random_Walk_Binomial_Distribution", "Random_Walk_Cauchy_Distribution");
    public static final List<String> timeUnitValues =Arrays.asList("year", "month", "day", "hour", "minute", "second", "milisecond");

}
