package tests;

import constants.*;
import enums.JsonNodesEnums;
import forms.AuthorizationPage;
import forms.LoginForm;
import forms.NewsFeedPage;
import forms.UserPage;
import io.restassured.response.Response;
import models.Photo;
import org.testng.Assert;
import org.testng.annotations.Test;
import tests.basetest.BaseTest;
import utils.data.VKApiUtils;
import utils.data.ConfigUtils;
import utils.data.DataUtils;
import utils.random.RandomUtils;

public class VkAPITest extends BaseTest {

    @Test
    public void VkAPIQueriesTest() {
        LoginForm loginForm = new LoginForm();
        AuthorizationPage authorizationPage = new AuthorizationPage();
        UserPage userPage = new UserPage();
        NewsFeedPage newsFeedPage = new NewsFeedPage();
        VKApiUtils VKApiUtils = new VKApiUtils();
        browser.goTo((String) ConfigUtils.getData(PathConstants.PATH_TO_CONFIG_DATA, ConfigDataConstants.MAIN_PAGE_URL));
        loginForm.enterLogin((String) ConfigUtils.getData(PathConstants.PATH_TO_TEST_DATA, TestDataConstants.LOGIN));
        loginForm.signInButtonClick();
        authorizationPage.enterPassword((String) ConfigUtils.getData(PathConstants.PATH_TO_TEST_DATA, TestDataConstants.PASSWORD));
        authorizationPage.submitButtonClick();
        newsFeedPage.state().waitForDisplayed();
        browser.goTo(ConfigUtils.getData(PathConstants.PATH_TO_CONFIG_DATA, ConfigDataConstants.MAIN_PAGE_URL) +
                (String) ConfigUtils.getData(PathConstants.PATH_TO_ENDPOINTS, EndPointConstants.USER_ID)
                + ConfigUtils.getData(PathConstants.PATH_TO_API_METHODS_KEYS, ApiMethodsKeysConstants.OWNER_ID));
        String postRandomText = RandomUtils.generateRandomText();
        Response response = VKApiUtils.postWallRecord(postRandomText);
        Assert.assertEquals(userPage.getPostMessage(), postRandomText, "New post message is NOT correct");
        browser.refresh();
        Assert.assertEquals(userPage.getAuthorLink(),
                ConfigUtils.getData(PathConstants.PATH_TO_CONFIG_DATA, ConfigDataConstants.MAIN_PAGE_URL) + (String) ConfigUtils.getData(PathConstants.PATH_TO_ENDPOINTS, EndPointConstants.USER_ID)
                        + ConfigUtils.getData(PathConstants.PATH_TO_API_METHODS_KEYS, ApiMethodsKeysConstants.OWNER_ID),
                "New post user is NOT correct");
        String editRandomText = RandomUtils.generateRandomText();
        Response photoServerResponse = VKApiUtils.uploadPhoto(PathConstants.PATH_TO_ATTACHED_PIC);
        Photo photo = DataUtils.responseToModel(photoServerResponse, Photo.class);
        Response photoIdResponse = VKApiUtils.savePhotoToWall(photo);
        VKApiUtils.editWallPostPhoto(DataUtils.getJsonNode(response, JsonNodesEnums.POST_ID.value), editRandomText, DataUtils.getJsonNode(photoIdResponse, JsonNodesEnums.POST_0_ID.value));
        browser.refresh();
        Assert.assertEquals(userPage.getPostMessage(), editRandomText, "Edited post message is NOT correct");
        Assert.assertEquals(userPage.getPhotoLink(), DataUtils.getJsonNode(photoIdResponse, JsonNodesEnums.POST_0_ID.value),
                "Edited pic is NOT correct");
        String commentRandomText = RandomUtils.generateRandomText();
        VKApiUtils.addCommentToWallPost(DataUtils.getJsonNode(response, JsonNodesEnums.POST_ID.value), commentRandomText);
        Assert.assertEquals(userPage.getReplyAuthorLink(), (String) ConfigUtils.getData(PathConstants.PATH_TO_CONFIG_DATA,
                        ConfigDataConstants.MAIN_PAGE_URL) + ConfigUtils.getData(PathConstants.PATH_TO_ENDPOINTS,
                        EndPointConstants.USER_ID) + ConfigUtils.getData(PathConstants.PATH_TO_API_METHODS_KEYS, ApiMethodsKeysConstants.OWNER_ID),
                "Reply user is NOT correct");
        Assert.assertEquals(userPage.getReplyText(), commentRandomText, "Reply message is NOT correct");
        userPage.postLikeButtonClick();
        Response likeResponse = VKApiUtils.postIsLiked(DataUtils.getJsonNode(response, JsonNodesEnums.POST_ID.value));
        Assert.assertEquals(Integer.parseInt(DataUtils.getJsonNode(likeResponse, JsonNodesEnums.OBJ_LIKED.value)),
                ConfigUtils.getData(PathConstants.PATH_TO_TEST_DATA, TestDataConstants.EXPECTED_LIKES), "Like has NOT appeared");
        VKApiUtils.deleteWallRecord(DataUtils.getJsonNode(response, JsonNodesEnums.POST_ID.value));
        Assert.assertTrue(userPage.isPostDeleted((String) ConfigUtils.getData(PathConstants.PATH_TO_API_METHODS_KEYS, ApiMethodsKeysConstants.OWNER_ID),
                DataUtils.getJsonNode(response, JsonNodesEnums.POST_ID.value)), "Post is NOT deleted");
    }
}
