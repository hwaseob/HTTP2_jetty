import org.eclipse.jetty.http.*;
import org.eclipse.jetty.http2.api.Session;
import org.eclipse.jetty.http2.api.Stream;
import org.eclipse.jetty.http2.api.server.ServerSessionListener;
import org.eclipse.jetty.http2.client.HTTP2Client;
import org.eclipse.jetty.http2.frames.DataFrame;
import org.eclipse.jetty.http2.frames.HeadersFrame;
import org.eclipse.jetty.util.Callback;
import org.eclipse.jetty.util.FuturePromise;
import org.eclipse.jetty.util.Jetty;

import java.net.InetSocketAddress;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

// Copied from the JavaDoc for org.eclipse.jetty.http2.client.HTTP2Client
public class client2 {

    public static void main(String[] args) throws Exception {
        long startTime = System.nanoTime();

        // Create and start HTTP2Client.
        HTTP2Client client = new HTTP2Client();
//        SslContextFactory sslContextFactory = new SslContextFactory(true);
//        client.addBean(sslContextFactory);
        client.start();

        // Connect to host.
        String host = "localhost";
//        int port = 8443;
        int port = 9090;

        FuturePromise<Session> sessionPromise = new FuturePromise<>();
        client.connect(/*sslContextFactory,*/ new InetSocketAddress(host, port), new ServerSessionListener.Adapter(), sessionPromise);

        // Obtain the client Session object.
        Session session = sessionPromise.get(5, TimeUnit.SECONDS);

        // Prepare the HTTP request headers.
        HttpFields requestFields = new HttpFields();
        requestFields.put("User-Agent", client.getClass().getName() + "/" + Jetty.VERSION);
        // Prepare the HTTP request object.
//        MetaData.Request request = new MetaData.Request("GET", new HttpURI("https://" + host + ":" + port + "/"), HttpVersion.HTTP_2, requestFields);
        MetaData.Request request = new MetaData.Request("GET", new HttpURI("http://" + host + ":" + port + "/"), HttpVersion.HTTP_1_1, requestFields);
        // Create the HTTP/2 HEADERS frame representing the HTTP request.
        HeadersFrame headersFrame = new HeadersFrame(request, null, true);

        // Prepare the listener to receive the HTTP response frames.
        Stream.Listener responseListener = new Stream.Listener.Adapter() {
            @Override
            public void onHeaders(Stream stream, HeadersFrame frame) {
                System.out.println("[" + stream.getId() + "] HEADERS " + frame.getMetaData().toString());
                for (Iterator<HttpField> i= frame.getMetaData().getFields().iterator();
                     i.hasNext();)
                {
                    HttpField f=i.next();
                    System.out.println(f.getName()+" : "+f.getValue());
                }
            }

            @Override
            public void onData(Stream stream, DataFrame frame, Callback callback)
            {
                byte[] bytes = new byte[frame.getData().remaining()];
                frame.getData().get(bytes);
                int duration = (int) TimeUnit.NANOSECONDS.toSeconds(System.nanoTime() - startTime);
                System.out.println("After " + duration + " seconds: " + new String(bytes));
                callback.succeeded();
            }
        };

//        Promise<Stream> f1=new FuturePromise<>();
        session.newStream(headersFrame, new FuturePromise<>(), responseListener);
//        session.newStream(headersFrame, new FuturePromise<>(), responseListener);
//        session.newStream(headersFrame, new FuturePromise<>(), responseListener);

        Thread.sleep(TimeUnit.SECONDS.toMillis(20));

        client.stop();
    }
}