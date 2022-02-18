package com.muller.lappli.domain;

public class AssemblyPreset implements Cloneable {

    private Long utilityComponentsCount;
    private Long completionComponentsCount;

    public AssemblyPreset() {}

    public AssemblyPreset(int utilityComponentsCount, int completionComponentsCount) {
        this(Long.valueOf(utilityComponentsCount), Long.valueOf(completionComponentsCount));
    }

    public AssemblyPreset(Long utilityComponentsCount, Long completionComponentsCount) {
        setUtilityComponentsCount(utilityComponentsCount);
        setCompletionComponentsCount(completionComponentsCount);
    }

    @Override
    public AssemblyPreset clone() throws CloneNotSupportedException {
        return new AssemblyPreset(getUtilityComponentsCount(), getCompletionComponentsCount());
    }

    public Long getTotalComponentsCount() {
        return getUtilityComponentsCount() + getCompletionComponentsCount();
    }

    public Long getUtilityComponentsCount() {
        return this.utilityComponentsCount;
    }

    public void setUtilityComponentsCount(Long utilityComponentsCount) {
        this.utilityComponentsCount = utilityComponentsCount;
    }

    public Long getCompletionComponentsCount() {
        return this.completionComponentsCount;
    }

    public void setCompletionComponentsCount(Long completionComponentsCount) {
        this.completionComponentsCount = completionComponentsCount;
    }
}
