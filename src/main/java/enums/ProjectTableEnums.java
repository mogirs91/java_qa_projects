package enums;

public enum ProjectTableEnums {
    PROJECT_NAME_PARAMETER_INDEX(1),
    ID_PARAMETER_INDEX(2);

    public final Integer index;

    ProjectTableEnums(Integer index) {
        this.index = index;
    }
}
