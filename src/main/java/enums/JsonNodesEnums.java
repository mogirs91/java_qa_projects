package enums;

public enum JsonNodesEnums {
    POST_ID("response.post_id"),
    POST_0_ID("response[0].id"),
    UPLOAD_URL("response.upload_url"),
    OBJ_LIKED("response.liked");

    public final String value;

    JsonNodesEnums(String value) {
        this.value = value;
    }
}
