import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;

public class client1_1 {
    public static void main(String[] args) throws Exception {
//        HttpClient httpClient = new HttpClient();
//        try {
//            httpClient.start();
//            ContentExchange contentExchange = new ContentExchange();
//            contentExchange.setMethod(method);
//            contentExchange.setRequestContentType(MediaType.APPLICATION_JSON);
//            contentExchange.setRequestContent(new ByteArrayBuffer(content.getBytes("UTF-8")));
//            httpClient.setConnectorType(HttpClient.CONNECTOR_SELECT_CHANNEL);
//            contentExchange.setURL(url);
//            httpClient.send(contentExchange);
//            contentExchange.waitForDone();
//            return contentExchange.getResponseStatus();
//        } finally {
//            httpClient.stop();
//        }

        HttpClient client = new HttpClient();
        client.start();

//        ContentResponse res = client.GET("http://localhost:8080");
        ContentResponse res = client.GET("http://localhost:9090");

        System.out.println(res.getContentAsString());

        client.stop();
    }
}
