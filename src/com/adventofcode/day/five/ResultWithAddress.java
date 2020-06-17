package com.adventofcode.day.five;

import java.util.Objects;

public class ResultWithAddress {

    private final String result;
    private final int memoryAddress;
    private final int instructionPointerCount;

    private ResultWithAddress(ResultWithAddressBuilder builder) {
        result = builder.result;
        memoryAddress = builder.memoryAddress;
        instructionPointerCount = builder.instructionPointerCount;
    }

    public static ResultWithAddressBuilder builder() {
        return new ResultWithAddressBuilder();
    }

    public String result() {
        return result;
    }

    public int memoryAddress() {
        return memoryAddress;
    }

    public int instructionPointerCount() {
        return instructionPointerCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResultWithAddress that = (ResultWithAddress) o;
        return memoryAddress == that.memoryAddress &&
                instructionPointerCount == that.instructionPointerCount &&
                result.equals(that.result);
    }

    @Override
    public int hashCode() {
        return Objects.hash(result, memoryAddress, instructionPointerCount);
    }

    public static class ResultWithAddressBuilder {
        private String result;
        private Integer memoryAddress;
        private Integer instructionPointerCount;

        private ResultWithAddressBuilder() {

        }

        public ResultWithAddressBuilder result(Long result) {
            this.result = String.valueOf(result);
            return this;
        }

        public ResultWithAddressBuilder memoryAddress(Integer memoryAddress) {
            this.memoryAddress = memoryAddress;
            return this;
        }

        public ResultWithAddressBuilder instructionPointerCount(Integer instructionPointerCount) {
            this.instructionPointerCount = instructionPointerCount;
            return this;
        }

        public ResultWithAddress build() {
            if(result == null) {
                throw new RuntimeException(String.format(
                        "Cannot build %s, %s parameter is missing.",
                        ResultWithAddress.class.getSimpleName(),
                        "result"));
            }

            if(memoryAddress == null) {
                throw new RuntimeException(String.format(
                        "Cannot build %s, %s parameter is missing.",
                        ResultWithAddress.class.getSimpleName(),
                        "memoryAddress"));
            }

            if(instructionPointerCount == null) {
                throw new RuntimeException(String.format(
                        "Cannot build %s, %s parameter is missing.",
                        ResultWithAddress.class.getSimpleName(),
                        "instructionPointerCount"));
            }

            return new ResultWithAddress(this);
        }
    }
}
