package enums;

public enum AuthorTableEnums {
    AUTHOR_EMAIL_PARAMETER_INDEX(1),
    AUTHOR_LOGIN_PARAMETER_INDEX(2),
    AUTHOR_NAME_PARAMETER_INDEX(3),
    AUTHOR_ID_PARAMETER_INDEX(4);

    public final Integer index;

    AuthorTableEnums(Integer index) {
        this.index = index;
    }
}
