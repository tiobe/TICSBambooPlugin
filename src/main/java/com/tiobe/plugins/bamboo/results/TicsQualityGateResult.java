package com.tiobe.plugins.bamboo.results;


import java.util.List;

public class TicsQualityGateResult {

    boolean passed;
    String message;
    List<QualityGate> gates;
    String url;

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

    public List<QualityGate> getGates() {
        return gates;
    }

    public void setGates(final List<QualityGate> gates) {
        this.gates = gates;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "TicsQualityGateResult{" +
                "passed=" + passed +
                ", message='" + message + '\'' +
                ", gates=" + gates +
                ", url='" + url + '\'' +
                '}';
    }
}
