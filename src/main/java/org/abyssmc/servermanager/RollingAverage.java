package org.abyssmc.servermanager;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayDeque;
import java.util.Queue;

public class RollingAverage {
    private final Queue<BigDecimal> samples;
    private final int size;
    private BigDecimal total = BigDecimal.ZERO;

    public RollingAverage(int size) {
        this.size = size;
        this.samples = new ArrayDeque<>(this.size);
    }

    public void add(BigDecimal num) {
        synchronized (this) {
            this.total = this.total.add(num);
            this.samples.add(num);
            if (this.samples.size() > this.size) {
                this.total = this.total.subtract(this.samples.remove());
            }
        }
    }

    public double getAverage() {
        synchronized (this) {
            if (this.samples.isEmpty()) {
                return 0;
            }
            return this.total.divide(BigDecimal.valueOf(this.samples.size()), 30, RoundingMode.HALF_UP).doubleValue();
        }
    }

    public double getMax() {
        synchronized (this) {
            BigDecimal max = BigDecimal.ZERO;
            for (BigDecimal sample : this.samples) {
                if (sample.compareTo(max) > 0) {
                    max = sample;
                }
            }
            return max.doubleValue();
        }
    }

    public double getMin() {
        synchronized (this) {
            BigDecimal min = BigDecimal.ZERO;
            for (BigDecimal sample : this.samples) {
                if (min == BigDecimal.ZERO || sample.compareTo(min) < 0) {
                    min = sample;
                }
            }
            return min.doubleValue();
        }
    }

}