package task1;

import task1.model.User;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class Main {
    private  static final String USER_URL = "https://jsonplaceholder.typicode.com/users";


    public static void main(String[] args) {

        try {
        //Task1.Create
        User user = new User(13,"Ivan","Axe","asdsdwe@gmail.com");
        User createUser = HttpUtil.sendPostUser(URI.create(USER_URL),user);
        System.out.println(createUser);

        //Task1.GetUserByID
        String user_URL_id = String.format("%s/%d","https://jsonplaceholder.typicode.com/users",1);
        URI user_URI_id = URI.create(user_URL_id);
        System.out.println(user_URL_id);
        User userReturnById = HttpUtil.sendGetUserById(user_URI_id);
        System.out.println(userReturnById);

        //Task1.Update
        userReturnById.setName("Joy Boy");
        User userAfterUpdate = HttpUtil.updateUser(user_URI_id,userReturnById);
        System.out.println(userAfterUpdate);

        //Task1.Delete
        HttpUtil.deleteUser(user_URI_id,userReturnById);

        //Task1.GetUserList
        List<User> userList= HttpUtil.sendGetUsersList(URI.create(USER_URL));
        System.out.println(userList);

        //Task1.GetUserByUsername
        String user_URI_username = String.format("%s?username=%s","https://jsonplaceholder.typicode.com/users","Samantha");
        User userReturnByUsername = HttpUtil.sendGetUserByUsername(URI.create(user_URI_username));
        System.out.println(userReturnByUsername);
        } catch (IOException e){
            throw new RuntimeException();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //Task2
        try {
            int userId = 1;
            final String POSTS_URL = "https://jsonplaceholder.typicode.com/users/"+userId+"/posts";
            int lastPostId = HttpUtil.getLastPostId(new URI(POSTS_URL));
            final String COMMENT_URL = "https://jsonplaceholder.typicode.com/posts/"+lastPostId+"/comments";
            String commentsJson = HttpUtil.getCommentOnLastPost(new URI(COMMENT_URL));
            String fileName = "user-"+userId+"-post-"+lastPostId+"-comments.json";
            try (FileWriter writer = new FileWriter(fileName)) {
                writer.write(commentsJson);
                System.out.println("Comments written to file: " + fileName);
            } catch (IOException e) {
                throw new RuntimeException("Error writing comments to file", e);
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        //Task3
        try {
            int userId = 1;
            final String URL_todos = "https://jsonplaceholder.typicode.com/users/"+userId+"/todos";
            System.out.println(HttpUtil.getTodosNotCompleted(new URI(URL_todos)));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
