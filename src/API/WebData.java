package API;

import javafx.scene.image.ImageView;
import javafx.scene.web.WebView;

import java.net.URL;

/**
 * API for the accessing web data.
 * @author  Clifton West
 * @version February 1, 2016
 */
public interface WebData {

    /**
     * Getting data off the internet.
     * @param address   the address on the internet to grab date
     * @param params    Any parameters needed.
     */
    default void getData(URL address, String params) {

    }

    ImageView getWebImage(String url) throws Exception;

    WebView getWebHtml(String url) throws Exception;
    /**
     *
     * @param address   the address on the internet to grab the dat
     * @param params    Any parameters needed.
     */
    default void putData(URL address, String params) {

    }
}
