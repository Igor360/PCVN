package interfaces;

import java.util.List;

public interface WAMPServerInterface {

    /**
     * An RPC call has been received
     *
     * @param topic
     * @param params
     */
    public void onCall(String topic, List<String> params);

    /**
     * A request to subscribe to a topic has been made
     *
     * @param topics
     */
    public void onSubscribe(String topic);

    /**
     * A request to unsubscribe from a topic has been made
     *
     * @param topic
     */
    public void onUnSubscribe(String topic);

    /**
     * A client is attempting to publish content to a subscribed connections on a URI
     *
     * @param topic
     * @param event
     */
    public void onPublish(String topic, String event);

}
