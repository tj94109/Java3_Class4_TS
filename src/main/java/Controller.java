import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import twitter4j.*;
import twitter4j.auth.AccessToken;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

public class Controller {
    Twitter twitter;

    @FXML // fx:id="imageview";
            ImageView imageview;
    HashMap<String, String> credentials = new HashMap<String, String>();


    public Controller(){
        TwitterFactory factory = new TwitterFactory();
        twitter = factory.getInstance();
        this.readConfig();
        login();

    }

    public void load(){
        try {
            AccountSettings settings = twitter.getAccountSettings();
            String screen_name = settings.getScreenName();
            User u = twitter.users().showUser(screen_name);
            String url = u.getOriginalProfileImageURL();
            Image image = new Image(url);
            imageview.setImage(image);

        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }

    private void login(){
        TwitterFactory factory = new TwitterFactory();
        twitter = factory.getInstance();

        twitter.setOAuthConsumer(credentials.get("consumer_key"),
                credentials.get("consumer_secret"));

        String token = credentials.get("access_token");
        String tokenSecret = credentials.get("access_token_secret");

        twitter.setOAuthAccessToken(new AccessToken(token, tokenSecret));

    }

    private void readConfig(){
        InputStream inputStream = null;
        try {
            Properties prop = new Properties();
            String propFileName = "config.properties";

            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }

            Enumeration<Object> keys = prop.keys();

            while(keys.hasMoreElements()){
                String k = (String) keys.nextElement();
                String v = prop.getProperty(k);
                credentials.put(k,v);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try{
                inputStream.close();
            } catch (Exception e){
                e.printStackTrace();
            }

        }
    }

}