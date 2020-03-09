package edp.core.utils;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class InterfaceUtils {

    public static String sendPost(String url, String data,String contentType){
        String result = "";

        contentType = "application/json";
        try {
            org.apache.http.client.HttpClient client = new DefaultHttpClient();

            //设置代理
            //HttpHost proxy = new HttpHost("192.168.0.0", 8080, "http");
            //client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);

            HttpPost post = new HttpPost(url);
            post.setHeader("Content-Type", contentType);
            post.addHeader("Authorization", "Basic YWRtaW46");

            StringEntity s = new StringEntity(data, "utf-8");
            s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,contentType));
            post.setEntity(s);

            // 发送请求
            HttpResponse httpResponse = client.execute(post);

            // 获取响应输入流
            InputStream inStream = httpResponse.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, "utf-8"));
            StringBuilder strber = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null){
                strber.append(line + "\n");
            }
            inStream.close();

            result = strber.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}