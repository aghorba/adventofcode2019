package com.adventofcode.day.three;

import java.util.Objects;

public class WirePoint {

    private final int x;
    private final int y;
    private final int stepCount;

    private WirePoint(WirePointBuilder builder) {
        this.x = builder.x;
        this.y = builder.y;
        this.stepCount = builder.stepCount;
    }

    public static WirePointBuilder builder() {
        return new WirePointBuilder();
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    public int stepCount() {
        return stepCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WirePoint wirePoint = (WirePoint) o;
        return x == wirePoint.x &&
                y == wirePoint.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public static class WirePointBuilder {
        private Integer x;
        private Integer y;
        private Integer stepCount;

        private WirePointBuilder() {

        }

        public WirePointBuilder x(int x) {
            this.x = x;
            return this;
        }

        public WirePointBuilder y(int y) {
            this.y = y;
            return this;
        }

        public WirePointBuilder stepCount(int stepCount) {
            this.stepCount = stepCount;
            return this;
        }

        public WirePoint build() {
            if(x == null) {
                throw new RuntimeException("Cannot build WirePoint, x value is missing");
            }

            if(y == null) {
                throw new RuntimeException("Cannot build WirePoint, y value is missing");
            }

            if(stepCount == null) {
                throw new RuntimeException("Cannot build WirePoint, stepCount value is missing");
            }

            return new WirePoint(this);
        }
    }

}
