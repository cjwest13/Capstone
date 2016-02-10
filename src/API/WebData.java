package API;

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

    /**
     *
     * @param address   the address on the internet to grab the dat
     * @param params    Any parameters needed.
     */
    default void putData(URL address, String params) {

    }
}
