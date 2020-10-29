package com.tiobe.plugins.bamboo.results;

import java.util.List;

public class QualityGate {
    boolean passed;
    String name;
    List<QualityGateCondition> conditions;

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(final boolean passed) {
        this.passed = passed;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public List<QualityGateCondition> getConditions() {
        return conditions;
    }

    public void setConditions(final List<QualityGateCondition> conditions) {
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
