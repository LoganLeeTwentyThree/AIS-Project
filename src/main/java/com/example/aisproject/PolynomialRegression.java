package com.example.aisproject;

import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoint;
import org.apache.commons.math3.fitting.WeightedObservedPoints;
import java.util.*;

public class PolynomialRegression {

    private double[] coeffs;

    public int prediction(int year) {
        double p = 0;
        for (int i = coeffs.length - 1; i >= 0; i--){
            p = coeffs[i] + (year * p);
        }

        return (int) p;
    }
    public double[] regress(int points[]){
        WeightedObservedPoints obs = new WeightedObservedPoints();

        for(int i = 0; i < Application.YEAR_RANGE + 1; i++){
            obs.add(1981 + i, points[i]);
        }




        PolynomialCurveFitter fitter = PolynomialCurveFitter.create(40);
        coeffs = fitter.fit(obs.toList());
        // Retrieve fitted parameters (coefficients of the polynomial function).
        return coeffs;
    }




}
