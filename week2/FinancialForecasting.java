/*
 * Exercise 7: Financial Forecasting
 * ===================================
 * Topic: Data Structures and Algorithms
 *
 * Goal: Given a historical series of prices (e.g., daily closing prices
 * of a stock), forecast future values using classic time-series
 * techniques built from simple array/list-based algorithms:
 *
 *   1. Simple Moving Average (SMA)
 *      - Smooths the series by averaging the last `window` data points.
 *      - Used here as a baseline "next value" predictor.
 *
 *   2. Exponential Moving Average (EMA)
 *      - Like SMA, but weights recent observations more heavily using a
 *        smoothing factor alpha, so it reacts faster to recent trends.
 *
 *   3. Linear Regression (Least Squares) Trend Forecast
 *      - Fits a straight line y = m*x + b through the historical data
 *        and extrapolates it to forecast N future periods.
 *
 * To compile and run:
 *   javac FinancialForecasting.java
 *   java FinancialForecasting
 */

import java.util.*;

class FinancialForecaster {

    private final List<Double> history;

    FinancialForecaster(List<Double> history) {
        if (history == null || history.isEmpty()) {
            throw new IllegalArgumentException("History must not be empty.");
        }
        this.history = history;
    }

    /**
     * Simple Moving Average over the last `window` points.
     * Time complexity: O(window) per call.
     */
    double simpleMovingAverage(int window) {
        int n = history.size();
        int effectiveWindow = Math.min(window, n);
        double sum = 0.0;
        for (int i = n - effectiveWindow; i < n; i++) {
            sum += history.get(i);
        }
        return sum / effectiveWindow;
    }

    /**
     * Exponential Moving Average across the whole series.
     * alpha in (0, 1]: higher alpha => more weight on recent data.
     * Time complexity: O(n), single pass.
     */
    double exponentialMovingAverage(double alpha) {
        double ema = history.get(0);
        for (int i = 1; i < history.size(); i++) {
            ema = alpha * history.get(i) + (1 - alpha) * ema;
        }
        return ema;
    }

    /**
     * Fits a least-squares linear trend line to the history and forecasts
     * `periodsAhead` future values.
     * Time complexity: O(n) to fit, O(periodsAhead) to forecast.
     */
    double[] linearRegressionForecast(int periodsAhead) {
        int n = history.size();
        double sumX = 0, sumY = 0, sumXY = 0, sumXX = 0;

        for (int i = 0; i < n; i++) {
            double x = i;
            double y = history.get(i);
            sumX += x;
            sumY += y;
            sumXY += x * y;
            sumXX += x * x;
        }

        // Slope (m) and intercept (b) of the best-fit line y = m*x + b.
        double m = (n * sumXY - sumX * sumY) / (n * sumXX - sumX * sumX);
        double b = (sumY - m * sumX) / n;

        double[] forecast = new double[periodsAhead];
        for (int i = 0; i < periodsAhead; i++) {
            double x = n + i; // future time steps continue from the last index
            forecast[i] = m * x + b;
        }
        return forecast;
    }

    /** Root Mean Squared Error of a simple moving-average model, useful
     *  for comparing forecasting approaches on historical data. */
    double smaBacktestRMSE(int window) {
        int n = history.size();
        if (n <= window) return Double.NaN;

        double sumSquaredError = 0;
        int count = 0;
        for (int i = window; i < n; i++) {
            double sum = 0;
            for (int j = i - window; j < i; j++) sum += history.get(j);
            double predicted = sum / window;
            double actual = history.get(i);
            sumSquaredError += Math.pow(actual - predicted, 2);
            count++;
        }
        return Math.sqrt(sumSquaredError / count);
    }
}

// ---------------------------------------------------------------------
// Client code / demo
// ---------------------------------------------------------------------
public class FinancialForecasting {
    public static void main(String[] args) {
        // Simulated 14 days of a stock's closing prices.
        List<Double> closingPrices = Arrays.asList(
                100.0, 102.5, 101.0, 103.0, 105.5, 107.0, 106.0,
                108.5, 110.0, 109.5, 111.0, 113.0, 114.5, 116.0
        );

        FinancialForecaster forecaster = new FinancialForecaster(closingPrices);

        System.out.println("Historical closing prices: " + closingPrices);

        double sma5 = forecaster.simpleMovingAverage(5);
        System.out.printf("%n5-day Simple Moving Average (next-day estimate): %.2f%n", sma5);

        double ema = forecaster.exponentialMovingAverage(0.3);
        System.out.printf("Exponential Moving Average (alpha=0.3): %.2f%n", ema);

        double[] forecast = forecaster.linearRegressionForecast(5);
        System.out.println("\nLinear Regression Trend Forecast (next 5 days):");
        for (int i = 0; i < forecast.length; i++) {
            System.out.printf("  Day +%d: %.2f%n", i + 1, forecast[i]);
        }

        double rmse = forecaster.smaBacktestRMSE(5);
        System.out.printf("%nBacktested RMSE of the 5-day SMA model: %.3f%n", rmse);
    }
}