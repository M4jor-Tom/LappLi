package com.muller.lappli.domain;

/**
 * A class to represent presets of the amounts
 * of utility and completion components at each
 * {@link com.muller.lappli.domain.CoreAssembly}
 *  that we could find
 */
public class AssemblyPreset implements Cloneable {

    private Long utilityComponentsCount;
    private Long completionComponentsCount;

    public AssemblyPreset() {
        setUtilityComponentsCount(null);
        setCompletionComponentsCount(null);
    }

    public AssemblyPreset(int utilityComponentsCount, int completionComponentsCount) {
        this(Long.valueOf(utilityComponentsCount), Long.valueOf(completionComponentsCount));
    }

    public AssemblyPreset(Long utilityComponentsCount, Long completionComponentsCount) {
        setUtilityComponentsCount(utilityComponentsCount);
        setCompletionComponentsCount(completionComponentsCount);
    }

    public static AssemblyPreset forError() {
        return new AssemblyPreset(DomainManager.ERROR_LONG_POSITIVE_VALUE, DomainManager.ERROR_LONG_POSITIVE_VALUE);
    }

    public static AssemblyPreset forCentralUtilityComponent() {
        return new AssemblyPreset(Long.valueOf(1), Long.valueOf(0));
    }

    public static AssemblyPreset forCentralCompletionComponent() {
        return new AssemblyPreset(Long.valueOf(0), Long.valueOf(1));
    }

    @Override
    public AssemblyPreset clone() throws CloneNotSupportedException {
        return new AssemblyPreset(getUtilityComponentsCount(), getCompletionComponentsCount());
    }

    public Boolean isCentralAccordingToTotalComponentsCount() {
        return Long.valueOf(1).equals(getTotalComponentsCount());
    }

    public Boolean isCentralUtilityComponent() {
        return forCentralUtilityComponent().equals(this);
    }

    public Boolean isCentralCompletionComponent() {
        return forCentralCompletionComponent().equals(this);
    }

    public Long getTotalComponentsCount() {
        if (
            DomainManager.ERROR_LONG_POSITIVE_VALUE.equals(getUtilityComponentsCount()) ||
            DomainManager.ERROR_LONG_POSITIVE_VALUE.equals(getCompletionComponentsCount())
        ) {
            return DomainManager.ERROR_LONG_POSITIVE_VALUE;
        }
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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AssemblyPreset)) {
            return false;
        }

        if (
            this.getCompletionComponentsCount() == null ||
            this.getUtilityComponentsCount() == null ||
            ((AssemblyPreset) o).getCompletionComponentsCount() == null ||
            ((AssemblyPreset) o).getUtilityComponentsCount() == null
        ) {
            return false;
        }

        return (
            this.getCompletionComponentsCount() == ((AssemblyPreset) o).getCompletionComponentsCount() &&
            this.getUtilityComponentsCount() == ((AssemblyPreset) o).getUtilityComponentsCount()
        );
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return (
            "AssemblyPreset{" +
            "utilityComponentsCount=" +
            getUtilityComponentsCount() +
            ", completionComponentsCount=" +
            getCompletionComponentsCount() +
            "}"
        );
    }
}
