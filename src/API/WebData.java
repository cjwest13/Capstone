package API;

import javafx.scene.image.ImageView;
import javafx.scene.web.WebView;

/**
 * API for the accessing web data.
 * @author  Clifton West
 * @version February 1, 2016
 */
public interface WebData {

    /**
     * Get the requested Image from the Internet.
     * @param url Url of image from the Web. <br><b>(NEED "http://" in front)</b>
     * @return ImageView that contains the requested Image.
     * @throws Exception If the Image couldn't be loaded for any reason.
     */
    ImageView getWebImage(String url) throws Exception;

    /**
     * Gets the requested Website from the Internet.
     * @param url Url of website from the Web. <br><b>(NEED "http://" in front)</b>
     * @return WebView that is the screen that will display
     * @throws Exception If the WebView crashed or the html couldn't be loaded for any reason.
     */
    WebView getWebHtml(String url) throws Exception;
}
