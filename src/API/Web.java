package API;

import controller.WebData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebView;

/**
 * Created by cjwest on 4/3/16.
 */
public class Web implements WebData {

    private WebView browser;


    public Web() {
        browser = new WebView();
    }

    @Override
    public ImageView getWebImage(String url) throws Exception {
        Image image = new Image(url);
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        return imageView;
    }

    @Override
    public WebView getWebHtml(String url) throws Exception {
        browser.getEngine().load(url);
        return browser;
    }
}
