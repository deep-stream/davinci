package edp.core.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import edp.davinci.service.screenshot.ImageContent;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;

public class WeChatUtils {




    /**
     * send image to Wechat Enterprise Robots
     * @return
     */
    public static void sendImageMsgToWX(List<ImageContent> images){


        if(null == images || images.isEmpty() || images.size() == 0) return;

        ImageContent image0 = images.get(0);


        String base64 = null;
        String md5 = null;

        File imageFile  = image0.getImageFile();
        InputStream inputStream = null;

        byte[] data = null;
        try {
            inputStream = new FileInputStream(imageFile);
            data = new byte[inputStream.available()];
            inputStream.read(data);
            inputStream.close();
            // 加密
            Base64.Encoder encoder = Base64.getEncoder();
            base64 = encoder.encodeToString(data);
            System.out.println("base64----->" + base64);

            //MD5签名
            inputStream = new FileInputStream(imageFile);
            md5 = DigestUtils.md5Hex(inputStream);
            inputStream.close();
            System.out.println("md5----->" + md5);

            System.out.println("image url----->" + image0.getUrl());
            System.out.println("image desc----->" + image0.getDesc());
            System.out.println("image cID----->" + image0.getCId());
            System.out.println("image Order----->" + image0.getOrder());
            System.out.println("image AbsolutePath----->" + image0.getImageFile().getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }


        JSONObject image = new JSONObject();
        image.put("base64", base64);
        image.put("md5", md5);

        JSONObject msg = new JSONObject();
        msg.put("msgtype", "image");
        msg.put("image", image);
        String msgJsonString = msg.toJSONString();

        String url = "https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=603ac224-9a0c-4067-930b-aa6549cb1192";
        String result = InterfaceUtils.sendPost(url,msgJsonString,"");//application/json
        System.out.println("Result: " + result);
        return;
    }



    /**
     * send News Msg to Wechat Enterprise Robots
     * @return
     */
    public static void sendNewsMsgToWX(List<ImageContent> images){
        if(null == images || images.isEmpty() || images.size() == 0) return;

        ImageContent image0 = images.get(0);


        JSONObject msg = new JSONObject();
        msg.put("msgtype", "news");

        JSONObject articleOne = new JSONObject();
        articleOne.put("title",image0.getDesc());
        articleOne.put("description", "点击图片，查看详情");
        articleOne.put("url", image0.getUrl().replace("localhost","172.20.10.4"));
        articleOne.put("picurl", image0.getUrl());

        JSONArray articles = new JSONArray();
        articles.add(articleOne);

        JSONObject news = new JSONObject();
        news.put("articles", articles);

        msg.put("news", news);



        String url = "https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=603ac224-9a0c-4067-930b-aa6549cb1192";
        String result = InterfaceUtils.sendPost(url,msg.toJSONString(),"");//application/json
        System.out.println("Result: " + result);
        return ;
    }

}
