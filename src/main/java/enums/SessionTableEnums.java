package enums;

public enum SessionTableEnums {
    SESSION_BUILD_PARAMETER_INDEX(1),
    SESSION_CREATED_PARAMETER_INDEX(2),
    SESSION_KEY_PARAMETER_INDEX(3),
    SESSION_ID_PARAMETER_INDEX(4);

    public final Integer index;

    SessionTableEnums(Integer index) {
        this.index = index;
    }
}
