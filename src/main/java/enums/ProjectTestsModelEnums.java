package enums;

public enum ProjectTestsModelEnums {
    NAME(0),
    METHOD(1),
    STATUS(2),
    START_TIME(3),
    END_TIME(4),
    DURATION(5);

    public final Integer index;

    ProjectTestsModelEnums(Integer index) {
        this.index = index;
    }
}
