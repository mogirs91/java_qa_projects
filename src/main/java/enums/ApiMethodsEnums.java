package enums;

public enum ApiMethodsEnums {
    TOKEN_GET("token/get"),
    TEST_GET("test/get/"),
    TEST_PUT("test/put"),
    TEST_PUT_LOG("test/put/log"),
    TEST_PUT_ATTACHMENT("test/put/attachment");

    public final String value;

    ApiMethodsEnums(String value) {
        this.value = value;
    }
}
