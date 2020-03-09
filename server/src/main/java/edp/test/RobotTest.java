package edp.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import edp.core.utils.InterfaceUtils;
import org.apache.commons.codec.digest.DigestUtils;
import sun.misc.BASE64Encoder;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

public class RobotTest {
    public static void main(String[] args) {
        String url = "https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=603ac224-9a0c-4067-930b-aa6549cb1192";
//        String msgJsonString = getTextMsgJsonStrSample();
        String msgJsonString = getImageMsgJsonStrSample();
//        String msgJsonString = getNetworkImageMsgJsonStrSample();
        String result = InterfaceUtils.sendPost(url,msgJsonString,"");//application/json
        System.out.println("Result: " + result);
    }


    private static String getTextMsgJsonStrSample(){
        JSONObject text = new JSONObject();
        text.put("content", "hello world!");
        JSONObject msg = new JSONObject();
        msg.put("msgtype", "text");
        msg.put("text", text);
        return msg.toJSONString();
    }


    private static String getImageMsgJsonStrSample(){
        String imgFilePath="/Users/frank/out.jpg";

        JSONObject image = new JSONObject();
        image.put("base64", getSampleImageStr(imgFilePath));
        image.put("md5", getSampleImageMD5(imgFilePath));

        JSONObject msg = new JSONObject();
        msg.put("msgtype", "image");
        msg.put("image", image);
        return msg.toJSONString();
    }


    private static String getNetworkImageMsgJsonStrSample(){
        String imgFilePath="http://d.hiphotos.baidu.com/image/pic/item/a044ad345982b2b713b5ad7d3aadcbef76099b65.jpg";

        JSONObject image = new JSONObject();
        try {
            image.put("base64", encodeNetworkImageToBase64(new URL(imgFilePath)));
            image.put("md5", encodeNetworkImageToMD5(new URL(imgFilePath)));
        } catch (Exception e) {
            e.printStackTrace();
        }


        JSONObject msg = new JSONObject();
        msg.put("msgtype", "image");
        msg.put("image", image);
        return msg.toJSONString();
    }



    /**
     * 根据图片地址转换为base64编码字符串
     * @param imgFile
     * @return
     */
    private static String getSampleImageStr(String imgFile) {
        InputStream inputStream = null;
        byte[] data = null;
        try {
            inputStream = new FileInputStream(imgFile);
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 加密
        Base64.Encoder encoder = Base64.getEncoder();
        String base64 = encoder.encodeToString(data);
        System.out.println("base64----->" + base64);
        return base64;
    }

    private static String getSampleImageMD5(String imgFile) {
        String md5 = null;
        try {
            md5 = DigestUtils.md5Hex(new FileInputStream(imgFile));
            System.out.println("md5----->" + md5);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return md5;
    }


    /**
     * 将网络图片编码为base64
     *
     * @param url
     * @return
     * @throws Exception
     */
    public static String encodeNetworkImageToBase64(URL url) throws Exception {
        //将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        System.out.println("图片的路径为:" + url.toString());
        //打开链接
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            //设置请求方式为"GET"
            conn.setRequestMethod("GET");
            //超时响应时间为5秒
            conn.setConnectTimeout(5 * 1000);
            //通过输入流获取图片数据
            InputStream inStream = conn.getInputStream();
            //得到图片的二进制数据，以二进制封装得到数据，具有通用性
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            //创建一个Buffer字符串
            byte[] buffer = new byte[1024];
            //每次读取的字符串长度，如果为-1，代表全部读取完毕
            int len = 0;
            //使用一个输入流从buffer里把数据读取出来
            while ((len = inStream.read(buffer)) != -1) {
            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
                outStream.write(buffer, 0, len);
            }
            //关闭输入流
            inStream.close();
            byte[] data = outStream.toByteArray();
            //对字节数组Base64编码
            BASE64Encoder encoder = new BASE64Encoder();
            String base64 = encoder.encode(data);
            System.out.println("网络文件[{}]编码成base64字符串:[{}]"+url.toString()+base64);
            return base64;//返回Base64编码过的字节数组字符串
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("图片上传失败,请联系客服!");
        }
    }



    /**
     * 将网络图片编码为base64
     *
     * @param url
     * @return
     * @throws Exception
     */
    public static String encodeNetworkImageToMD5(URL url) throws Exception {
        //将图片文件转化为字节数组字符串，并对其进行MD5编码处理
        System.out.println("图片的路径为:" + url.toString());
        //打开链接
        HttpURLConnection conn = null;
        try {
            conn = (HttpURLConnection) url.openConnection();
            //设置请求方式为"GET"
            conn.setRequestMethod("GET");
            //超时响应时间为5秒
            conn.setConnectTimeout(5 * 1000);
            //通过输入流获取图片数据
            InputStream inStream = conn.getInputStream();



            String md5 = null;
            try {
                md5 = DigestUtils.md5Hex(inStream);
                System.out.println("md5----->" + md5);
                //关闭输入流
                inStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return md5;
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("图片上传失败,请联系客服!");
        }
    }




    private static String getNewsMsgJsonStrSample(){

        JSONObject msg = new JSONObject();
        msg.put("msgtype", "news");

        JSONObject articleOne = new JSONObject();
        articleOne.put("title", "中秋节礼品领取");
        articleOne.put("description", "今年中秋节公司有豪礼相送");
        articleOne.put("url", "http://www.baidu.com");
        articleOne.put("picurl", "http://res.mail.qq.com/node/ww/wwopenmng/images/independent/doc/test_pic_msg1.png");

        JSONArray articles = new JSONArray();
        articles.add(articleOne);

        JSONObject news = new JSONObject();
        news.put("articles", articles);

        msg.put("news", news);
        return msg.toJSONString();
    }

}
