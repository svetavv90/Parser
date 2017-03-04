package pl.parser.nbp.Calculation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by valkos on 04.03.17.
 */
public class StorageInstruments {
    private static int counter = 0;
    private static List<Double> buyingIterators = new ArrayList<>();
    private static List<Double> saleIterators = new ArrayList<>();

    public static void putToSale(double sale) {
        saleIterators.add(sale);
    }

    public static void putToBuying(double buying) {
        buyingIterators.add(buying);
    }

    public static void incrementCounter() {
        counter++;
    }

    public static List<Double> getBuying() {
        return buyingIterators;
    }

    public static List<Double> getSale() {
        return saleIterators;
    }

    public static int getCounter() {
        return counter;
    }

}
