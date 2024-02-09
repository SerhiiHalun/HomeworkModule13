package task1;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import task1.model.User;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class HttpUtil {
 private static final HttpClient CLIENT =  HttpClient.newHttpClient();
 private  static  final Gson GSON = new Gson();
 public static User sendPost(URI uri, User user){
  final String userGson = GSON.toJson(user);
  HttpRequest request = HttpRequest.newBuilder()
          .uri(uri)
          .POST(HttpRequest.BodyPublishers.ofString(userGson))
          .header("Content-type","application/json")
          .build();
  try {
   HttpResponse<String>response = CLIENT.send(request,HttpResponse.BodyHandlers.ofString());
   return GSON.fromJson(response.body(),User.class);
  } catch (IOException e) {
   throw new RuntimeException(e);
  } catch (InterruptedException e) {
   throw new RuntimeException(e);
  }

 }
 public static User sendGetUserById(URI uri){
  HttpRequest request = HttpRequest.newBuilder()
          .uri(uri)
          .header("Content-Type", "application/json")
          .GET()
          .build();
  try {
   HttpResponse<String> response = CLIENT.send(request,HttpResponse.BodyHandlers.ofString());
    List<User> users = GSON.fromJson(response.body(),new TypeToken<List<User>>(){}.getType());
    System.out.println(users);
    return users.get(0);
  } catch (IOException e) {
   throw new RuntimeException(e);
  } catch (InterruptedException e) {
   throw new RuntimeException(e);
  }
  
 }

}
