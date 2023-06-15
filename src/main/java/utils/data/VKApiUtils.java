package utils.data;

import constants.ApiMethodsKeysConstants;
import constants.ConfigDataConstants;
import constants.PathConstants;
import enums.ApiMethodsEnums;
import enums.JsonNodesEnums;
import io.restassured.response.Response;
import models.Photo;
import org.testng.Reporter;

import java.net.URLEncoder;
import java.util.HashMap;

public class VKApiUtils {

    private static final String MESSAGE = "message";
    private static final String POST_ID = "post_id";
    private static final String PHOTO = "photo";
    private static final String SERVER = "server";
    private static final String HASH = "hash";
    private static final String ATTACHMENTS = "attachments";
    private static final String ITEM_ID = "item_id";
    private static final String LIKE_OBJ_TYPE = "type";
    private static final String POST_IS_LIKED_VALUE = "post";
    private static final String EDIT_WALL_POST_PHOTO_ATTACHMENTS = "photo%s_%s";
    private static final String MULTIPART_EXPRESSION = "photo";
    private static final String MULTIPART_TYPE = "multipart/form-data";
    private final ApiUtils apiUtils = new ApiUtils();

    public Response postWallRecord(String message) {
        HashMap<String, Object> params = apiUtils.commonParams();
        params.put(MESSAGE, message);
        return apiUtils
                .post(ApiMethodsEnums.WALL_POST.value, params);
    }

    public Response uploadPhoto(String pathName) {
        HashMap<String, Object> params = apiUtils.commonParams();
        Response uploadUrlResponse = apiUtils
                .post(ApiMethodsEnums.PHOTOS_UPLOAD_SERVER.value, params);
        return apiUtils.upload(MULTIPART_EXPRESSION,MULTIPART_TYPE,DataUtils.getJsonNode(uploadUrlResponse, JsonNodesEnums.UPLOAD_URL.value), pathName);
    }

    public Response savePhotoToWall(Photo photo) {
        try {
            HashMap<String, Object> params = apiUtils.commonParams();
            params.put(PHOTO, URLEncoder.encode(photo.getPhoto(), (String) ConfigUtils.getData(PathConstants.PATH_TO_CONFIG_DATA, ConfigDataConstants.ENCODING)));
            params.put(SERVER, photo.getServer());
            params.put(HASH, photo.getHash());
            return apiUtils
                    .post(ApiMethodsEnums.PHOTOS_SAVE_TO_WALL.value, params);
        } catch (Exception e) {
            Reporter.log("Failed to save photo to the wall", true);
        }
        return null;
    }

    public Response editWallPostPhoto(String postId, String message, String photoId) {
        HashMap<String, Object> params = apiUtils.commonParams();
        params.put(POST_ID, postId);
        params.put(MESSAGE, message);
        params.put(ATTACHMENTS, String.format(EDIT_WALL_POST_PHOTO_ATTACHMENTS, params.get(ApiMethodsKeysConstants.OWNER_ID), photoId));
        return apiUtils
                .post(ApiMethodsEnums.WALL_EDIT.value, params);
    }

    public Response addCommentToWallPost(String postId, String message) {
        HashMap<String, Object> params = apiUtils.commonParams();
        params.put(POST_ID, postId);
        params.put(MESSAGE, message);
        return apiUtils
                .post(ApiMethodsEnums.WALL_POST_ADD_COMMENT.value, params);
    }

    public Response postIsLiked(String itemId) {
        HashMap<String, Object> params = apiUtils.commonParams();
        params.put(LIKE_OBJ_TYPE, POST_IS_LIKED_VALUE);
        params.put(ITEM_ID, itemId);
        return apiUtils
                .post(ApiMethodsEnums.LIKES_IS_LIKED.value, params);
    }

    public Response deleteWallRecord(String postId) {
        HashMap<String, Object> params = apiUtils.commonParams();
        params.put(POST_ID, postId);
        return apiUtils
                .post(ApiMethodsEnums.DELETE_POST.value, params);
    }

}