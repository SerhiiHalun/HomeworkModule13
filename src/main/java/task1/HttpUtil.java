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
   public static User sendPostUser(URI uri, User user){
         final String userGson = GSON.toJson(user);
         HttpRequest request = HttpRequest.newBuilder()
                 .uri(uri)
                 .POST(HttpRequest.BodyPublishers.ofString(userGson))
                 .header("Content-type","application/json")
                 .build();
         try {
              HttpResponse<String>response = CLIENT.send(request,HttpResponse.BodyHandlers.ofString());
             System.out.println("Response status code: " + response.statusCode());
              return GSON.fromJson(response.body(),User.class);
         } catch (IOException e) {
              throw new RuntimeException(e);
         } catch (InterruptedException e) {
              throw new RuntimeException(e);
         }
   }
   public static User updateUser(URI uri, User user) {
       String userGson = GSON.toJson(user);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Content-type","application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(userGson))
                .build();
       try {
           HttpResponse<String> response= CLIENT.send(request,HttpResponse.BodyHandlers.ofString());
           System.out.println("Response status code: " + response.statusCode());
           return  GSON.fromJson(response.body(),User.class);
       } catch (IOException e) {
           throw new RuntimeException(e);
       } catch (InterruptedException e) {
           throw new RuntimeException(e);
       }

   }
   public static  void deleteUser(URI uri, User user) throws IOException, InterruptedException {
       HttpRequest request = HttpRequest.newBuilder()
               .uri(uri)
               .header("Content-type","application/json")
               .DELETE()
               .build();
       HttpResponse response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
       System.out.println("Delete object User ID = "+user.getId());
       System.out.println("Response status code: " + response.statusCode());

   }
   public static User sendGetUserById(URI uri){
    HttpRequest request = HttpRequest.newBuilder()
            .uri(uri)
            .header("Content-Type", "application/json")
            .GET()
            .build();
    try {
     HttpResponse<String> response = CLIENT.send(request,HttpResponse.BodyHandlers.ofString());
     User users = GSON.fromJson(response.body(),User.class);
     System.out.println("Response status code: " + response.statusCode());
      return users;
    } catch (IOException e) {
     throw new RuntimeException(e);
    } catch (InterruptedException e) {
     throw new RuntimeException(e);
    }
   }

    public static List<User> sendGetUsersList(URI uri){
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Content-Type", "application/json")
                .GET()
                .build();
        try {
            HttpResponse<String> response = CLIENT.send(request,HttpResponse.BodyHandlers.ofString());
            List<User> users = GSON.fromJson(response.body(),new TypeToken<List<User>>(){}.getType());
            System.out.println("Response status code: " + response.statusCode());
            return users;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
