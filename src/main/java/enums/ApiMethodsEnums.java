package enums;

public enum ApiMethodsEnums {

    WALL_POST("wall.post"),
    WALL_EDIT("wall.edit"),
    PHOTOS_UPLOAD_SERVER("photos.getWallUploadServer"),
    PHOTOS_SAVE_TO_WALL("photos.saveWallPhoto"),
    WALL_POST_ADD_COMMENT("wall.createComment"),
    LIKES_IS_LIKED("likes.isLiked"),
    DELETE_POST("wall.delete");

    public final String value;

    ApiMethodsEnums(String value) {
        this.value = value;
    }
}
