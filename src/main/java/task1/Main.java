package task1;

import task1.model.User;

import java.net.URI;
import java.net.URISyntaxException;

public class Main {
    private  static final String USER_URL = "https://jsonplaceholder.typicode.com/users";
    public static void main(String[] args) {
        //Task1.Create
        User user = new User(13,"Ivan","Axe","asdsdwe@gmail.com");
        User createUser = HttpUtil.sendPost(URI.create(USER_URL),user);
        System.out.println(createUser);
        //Task1.GetUserByID
        String user_URL_id = String.format("%s?id=%d","https://jsonplaceholder.typicode.com/users",createUser.getId());
        System.out.println(user_URL_id);
        try {
            System.out.println(HttpUtil.sendGetUserById(new URI(user_URL_id)));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
