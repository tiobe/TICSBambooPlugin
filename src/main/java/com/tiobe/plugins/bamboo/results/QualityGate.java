package com.tiobe.plugins.bamboo.results;

import java.util.List;

public class QualityGate {
    boolean passed;
    String name;
    List<QualityGateCondition> conditions;

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<QualityGateCondition> getConditions() {
        return conditions;
    }

    public void setConditions(List<QualityGateCondition> conditions) {
        this.conditions = conditions;
    }

    @Override
    public String toString() {
        return "QualityGate{" +
                "passed=" + passed +
                ", name='" + name + '\'' +
                ", conditions=" + conditions +
                '}';
    }
}
