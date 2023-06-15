package tests;

import constants.EndPointConstants;
import constants.PathConstants;
import constants.TestDataConstants;
import io.restassured.response.Response;
import models.Post;
import models.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import tests.basetest.BaseTest;
import utils.data.APIUtils;
import utils.data.ConfigUtils;
import utils.data.DataUtils;

import java.io.File;

public class RestAPITest extends BaseTest {

    @Test
    public void GetPostsTest() {
        Response response = APIUtils.getMethodResponse((String) ConfigUtils.getConfigTestData(PathConstants.PATH_TO_ENDPOINTS, EndPointConstants.POSTS));
        Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "Status code is NOT " + HttpStatus.SC_OK);
        Assert.assertTrue(APIUtils.isResponseBodyJson(response), "The list in response body is NOT json.");
        Assert.assertTrue(DataUtils.isPostListSorted(DataUtils.sortResponse(response.asString())), "Posts are NOT sorted ascending by id.");
    }

    @Test
    public void GetPostByIdTest() {
        Response response = APIUtils.getMethodResponse(ConfigUtils.getConfigTestData(PathConstants.PATH_TO_ENDPOINTS, EndPointConstants.POSTS) + (String) ConfigUtils.getConfigTestData(PathConstants.PATH_TO_ENDPOINTS, EndPointConstants.POST_ID));
        Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "Status code is NOT " + HttpStatus.SC_OK);
        Assert.assertEquals(
                DataUtils.parseData(response.asString(), Post.class).toString(),
                DataUtils.parseData(new File(PathConstants.PATH_TO_POST_MODEL_DATA), Post.class).toString(),
                "Post data is NOT correct.");
    }

    @Test
    public void GetNotFoundStatusCodeTest() {
        Response response = APIUtils.getMethodResponse(ConfigUtils.getConfigTestData(PathConstants.PATH_TO_ENDPOINTS, EndPointConstants.POSTS) + (String) ConfigUtils.getConfigTestData(PathConstants.PATH_TO_ENDPOINTS, EndPointConstants.NOT_FOUND_POST_ID));
        Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_NOT_FOUND, "Status code is NOT " + HttpStatus.SC_NOT_FOUND);
        Assert.assertEquals(response.body().asString(),
                (String) ConfigUtils.getConfigTestData(PathConstants.PATH_TO_TEST_DATA, TestDataConstants.EMPTY_RESPONSE_BODY),
                "Response body is NOT empty.");
    }

    @Test
    public void PostRequestTest() {
        Post generatedPost = new Post();
        generatedPost.setId((Integer) ConfigUtils.getConfigTestData(PathConstants.PATH_TO_TEST_DATA, TestDataConstants.POST_REQUEST_ID));
        generatedPost.setUserId((Integer) ConfigUtils.getConfigTestData(PathConstants.PATH_TO_TEST_DATA, TestDataConstants.POST_REQUEST_ID));
        generatedPost.setTitle(RandomStringUtils.random((Integer) ConfigUtils.getConfigTestData(PathConstants.PATH_TO_TEST_DATA, TestDataConstants.TITLE_LENGTH), true, false));
        generatedPost.setBody(RandomStringUtils.random((Integer) ConfigUtils.getConfigTestData(PathConstants.PATH_TO_TEST_DATA, TestDataConstants.BODY_LENGTH), true, false));
        Response response = APIUtils.postMethodRequest((String) ConfigUtils.getConfigTestData(PathConstants.PATH_TO_ENDPOINTS, EndPointConstants.POSTS), generatedPost);
        Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_CREATED, "Status code is NOT " + HttpStatus.SC_CREATED);
        Assert.assertEquals(DataUtils.parseData(response.body().asString(), Post.class).toString(), generatedPost.toString(), "Post information is NOT correct");
    }

    @Test
    public void ValidateResposeAllUsersTest() {
        Response response = APIUtils.getMethodResponse((String) ConfigUtils.getConfigTestData(PathConstants.PATH_TO_ENDPOINTS, EndPointConstants.USERS));
        Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "Status code is NOT " + HttpStatus.SC_OK);
        Assert.assertTrue(APIUtils.isResponseBodyJson(response), "The list in response body is NOT json.");
        Assert.assertEquals(
                DataUtils.filterUsersById(response.asString(), User.class,
                        (Integer) ConfigUtils.getConfigTestData(PathConstants.PATH_TO_TEST_DATA, TestDataConstants.USER_ID)).toString(),
                DataUtils.parseData(new File(PathConstants.PATH_TO_USER_MODEL_DATA), User.class).toString(),
                "User data is NOT correct.");
    }

    @Test
    public void ValidateResposeOneUserTest() {
        Response response = APIUtils.getMethodResponse(ConfigUtils.getConfigTestData(PathConstants.PATH_TO_ENDPOINTS, EndPointConstants.USERS) + (String) ConfigUtils.getConfigTestData(PathConstants.PATH_TO_ENDPOINTS, EndPointConstants.USER_ID));
        Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "Status code is NOT " + HttpStatus.SC_OK);
        Assert.assertEquals(
                DataUtils.parseData(response.asString(), User.class).toString(),
                DataUtils.parseData(new File(PathConstants.PATH_TO_USER_MODEL_DATA), User.class).toString(),
                "User data is NOT correct.");
    }
}