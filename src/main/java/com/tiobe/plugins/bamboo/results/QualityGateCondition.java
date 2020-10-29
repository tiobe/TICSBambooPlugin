package com.tiobe.plugins.bamboo.results;

public class QualityGateCondition {
    boolean passed;
    String message;

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(final boolean passed) {
        this.passed = passed;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "QualityGateCondition{" +
                "passed=" + passed +
                ", message='" + message + '\'' +
                '}';
    }
}
