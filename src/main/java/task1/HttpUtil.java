package task1;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import task1.model.Todo;
import task1.model.User;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.Collectors;

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

    public static User sendGetUserByUsername(URI uri){
       List<User> users = HttpUtil.sendGetUsersList(uri);
       return users.get(0);
    }
    //Task2
    public  static int getLastPostId(URI uri){
       try {

            HttpRequest request = HttpRequest.newBuilder()
               .uri(uri)
               .GET()
               .build();

            HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());

            String[] arrayPosts = response.body().split("},");
            return arrayPosts.length;
       } catch (IOException e) {
           throw new RuntimeException(e);
       } catch (InterruptedException e) {
           throw new RuntimeException(e);
       }
    }
    public static String getCommentOnLastPost(URI uri){
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();
            try {
                HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
                return response.body();
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException("Error sending GET request", e);
            }
    }
    public  static  List<Todo> getTodosNotCompleted(URI uri){
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("Content-Type", "application/json")
                .GET()
                .build();
        try {
            HttpResponse<String> response =CLIENT.send(request,HttpResponse.BodyHandlers.ofString());
            List<Todo>result = GSON.fromJson(response.body(),new TypeToken<List<Todo>>(){}.getType());
            result=result.stream().filter((Todo todo)-> todo.isCompleted() == false).collect(Collectors.toList());
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
