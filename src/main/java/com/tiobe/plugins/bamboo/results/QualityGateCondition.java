package com.tiobe.plugins.bamboo.results;

public class QualityGateCondition {
    boolean passed;
    String message;

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
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
