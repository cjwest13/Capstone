package API;

import controller.WebData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebView;

/**
 * Class to get Images, html from the Web.
 * @author Clifton West
 * @version April 3, 2016.
 */
public class Web implements WebData {

    /** Viewer to display html content */
    private WebView browser;

    /**
     * Constructor for the Web Class.
     */
    public Web() {
        browser = new WebView();
    }

    /**
     * Get the requested Image from the Internet.
     * @param url Url of image from the Web. <br><b>(NEED "http://" in front)</b>
     * @return ImageView that contains the requested Image.
     * @throws Exception If the Image couldn't be loaded for any reason.
     */
    @Override
    public ImageView getWebImage(String url) throws Exception {
        Image image = new Image(url);
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        return imageView;
    }

    /**
     * Gets the requested Website from the Internet.
     * @param url Url of website from the Web. <br><b>(NEED "http://" in front)</b>
     * @return WebView that is the screen that will display
     * @throws Exception If the WebView crashed or the html couldn't be loaded for any reason.
     */
    @Override
    public WebView getWebHtml(String url) throws Exception {
        browser.getEngine().load(url);
        return browser;
    }
}
