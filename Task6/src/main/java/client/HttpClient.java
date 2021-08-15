package client;

import org.apache.http.client.fluent.Request;

import java.io.IOException;

public class HttpClient {
    public static void main(String[] args) {
//        HttpGet httpGet = new HttpGet("http://localhost:8801");
//        try (CloseableHttpClient httpClient = HttpClients.createDefault();
//             CloseableHttpResponse response = httpClient.execute(httpGet);) {
//            HttpEntity httpEntity = response.getEntity();
//            System.out.println(EntityUtils.toString(httpEntity));
//            EntityUtils.consume(httpEntity);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        try {
            System.out.println(Request.Get("http://localhost:8801")
                    .execute()
                    .returnContent()
                    .asString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
