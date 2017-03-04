package pl.parser.nbp.Calculation;

import java.util.List;

import static pl.parser.nbp.Calculation.StorageInstruments.getBuying;
import static pl.parser.nbp.Calculation.StorageInstruments.getCounter;
import static pl.parser.nbp.Calculation.StorageInstruments.getSale;

/**
 * Created by valkos on 05.03.17.
 */
public class CalculationLogic {
    public double getSaleAverage() {
        return averageOperation(calculationSum(getSale()));
    }

    public double getAverageDeviationForBuying() {
        return calculationOfDeviation(getBuying());
    }

    private double calculationSum(List<Double> elements) {
        double sum = 0;
        for (Double element : elements) {
            sum += element;
        }
        return sum;
    }

    private double calculationOfDeviation(List<Double> elements) {
        int counter = getCounter();
        if (counter > 0) {
            return Math.sqrt(poweredElements(elements)/counter);
        }
        return 0;
    }

    private double poweredElements(List<Double> elements) {
        double result = 0;
        for (Double element : elements) {
            result += Math.pow((element - getBuyingAverage()), 2);
        }
        return result;
    }

    private double getBuyingAverage() {
        return averageOperation(calculationSum(getBuying()));
    }

    private double averageOperation(double operator) {
        int counter = getCounter();
        if (counter > 0) {
            return operator/counter;
        }
        return 0;
    }
}

