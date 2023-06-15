package utils.data;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Post;
import org.json.JSONArray;
import org.json.JSONException;
import org.testng.Reporter;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataUtils {

    public static ArrayList<Post> getSortedId(ArrayList<Post> inputlist) {
        try {
            inputlist.sort((a1, a2) -> {
                try {
                    return Integer.compare(a1.getId(), a2.getId());
                } catch (JSONException e) {
                    Reporter.log("id comparison failed", true);
                }
                return 0;
            });
            return inputlist;
        } catch (Exception e) {
            Reporter.log("id sorting failed", true);
        }
        return null;
    }

    public static ArrayList<Post> sortResponse(String responseBody) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            ArrayList<Post> myObjects = mapper.readValue(responseBody, new TypeReference<ArrayList<Post>>() {
            });
            return DataUtils.getSortedId(myObjects);
        } catch (Exception e) {
            Reporter.log("failed to sort the response", true);
        }
        return null;
    }

    public static Boolean isPostListSorted(ArrayList<Post> posts) {
        List<String> results = new ArrayList<>();
        for (Post p : posts) {
            results.add(p.getId().toString());
        }
        List<String> resultscopy;
        resultscopy = results;
        Collections.sort(resultscopy);
        return resultscopy.equals(results);
    }

    public static <T> T filterUsersById(String responseBody, Class<T> tClass, Integer id) {
        try {
            JSONArray users = new JSONArray(responseBody);
            for (int n = 0; n < users.length(); n++) {
                if (users.getJSONObject(n).getInt("id") == id) {
                    return parseData(users.getJSONObject(n).toString(), tClass);
                }
            }
        } catch (Exception ex) {
            Reporter.log("failed to filter users by id", true);
        }
        return null;
    }

    public static <T> T parseData(String input, Class<T> tClass) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(input, tClass);
        } catch (Exception ex) {
            Reporter.log("failed to parse data", true);
        }
        return null;
    }

    public static <T> T parseData(File file, Class<T> tClass) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(file, tClass);
        } catch (Exception ex) {
            Reporter.log("failed to parse data", true);
        }
        return null;
    }
}
