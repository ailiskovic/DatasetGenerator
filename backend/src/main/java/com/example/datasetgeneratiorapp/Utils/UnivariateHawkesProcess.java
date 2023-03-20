package com.example.datasetgeneratiorapp.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.commons.math3.optim.nonlinear.scalar.noderiv.NelderMeadSimplex;

/**
 * Univariate Hawkes Process class
 *
 * Idea from: https://javastuffnotes.blogspot.com/2016/05/hawkes-process-parameters-estimation-in.html
 */
public final class UnivariateHawkesProcess {

    public static double calculateIntensity(double[] t, double lambda0, double alpha,
                                            double beta) {
        double sum = 0.0;
        int n = t.length;
        for (int i = 0; i < n - 1; i++) {
            sum += Math.exp(-beta * (t[n - 1] - t[i]));
        }

        double intensity = lambda0 + alpha * sum;

        return Math.max(0.0, intensity); // returns 0 or more only
    }

    /**
     * @param t
     *            represents time of points occurrence.
     */
    public static double[] estimateParams(final double[] t) {
        final int maxSteps = 20;
        NelderMeadSimplex simplex = new NelderMeadSimplex(3);
        simplex.build(new double[] { 1.0, 0.5, 1.0 });

        for (int i = 0; i < maxSteps; i++) {
            simplex.iterate((params) -> logLikelihood(t, params[0], params[1], params[2]),
                    (p1, p2) -> Double.compare(p2.getValue(), p1.getValue()));
        }

        return simplex.getPoint(0).getKey();
    }

    public static double logLikelihood(double[] t, double lambda0, double alpha,
                                       double beta) {
        if (t.length <= 0) {
            return 0.0;
        }
        if (lambda0 < 0.0 || alpha < 0.0 || beta < 0.0) {
            return Double.NEGATIVE_INFINITY;
        }
        double sum1 = 0.0, sum2 = 0.0;
        int n = t.length;
        for (int i = 0; i < n; i++) {
            sum1 += Math.exp(-beta * (t[n - 1] - t[i])) - 1;
            double sum3 = 0.0;
            for (int j = 0; j < i; j++) {
                sum3 += Math.exp(-beta * (t[i] - t[j]));
            }
            sum2 += Math.log(lambda0 + alpha * sum3);
        }
        sum1 *= alpha / beta;

        return -lambda0 * t[n - 1] + sum1 + sum2;
    }

    /**
     * @return an array with 2 items, points and corresponding lambda values. Both are in form of a double to double map, where key is the time of point occurrence.
     */
    @SuppressWarnings("unchecked")
    public static Map<Double, Double>[] simulate(double lambda0, double alpha,
                                                 double beta, double horizon) {
        Map<Double, Double> lambdaHistory = new HashMap<>();
        Map<Double, Double> pointInTimeHistory = new HashMap<>();
        double lambdaStar = lambda0;
        double lambda = alpha;
        Random random = new Random();
        double U = random.nextDouble();
        double s = -1.0 / lambdaStar * Math.log(U);
        double t = s;

        if (s <= horizon) {
            lambdaHistory.put(t, lambda);
            while (true) {
                lambdaStar = lambda0 + lambda * Math.exp(-beta * (s - t));
                U = random.nextDouble();
                s = s - (1.0 / lambdaStar) * Math.log(U);
                if (s > horizon) {
                    break;
                }
                double D = random.nextDouble();
                if (D <= (lambda0 + lambda * Math.exp(-beta * (s - t))) / lambdaStar) {
                    lambda = lambda * Math.exp(-beta * (s - t)) + alpha;
                    t = s;
                    pointInTimeHistory.put(t, 0.0);
                    lambdaHistory.put(t, lambda0 + lambda * Math.exp(-beta * (s - t)));
                }
            }
        }
        return new Map[] { lambdaHistory, pointInTimeHistory };
    } /// simulate

}