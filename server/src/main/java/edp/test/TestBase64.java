package edp.test;

import edp.core.utils.Base64Util;
import edp.core.utils.MD5Util;
import org.apache.commons.codec.digest.DigestUtils;
import sun.misc.BASE64Encoder;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;


public class TestBase64 {



    public static void main(String[] args) throws Exception {
        String imgFilePath="http://d.hiphotos.baidu.com/image/pic/item/a044ad345982b2b713b5ad7d3aadcbef76099b65.jpg";
        String base64_str = encodeImageToBase64(new URL(imgFilePath));//将网络图片编码为base64
//String base64_str = Base64Util.getImageBinary("D:/test.jpg","jpg");//将本地图片编码为base64
        System.out.println(base64_str);
        String md5 = encodeImageMD5(new URL(imgFilePath));
        System.out.println("md5: " + md5);
        Base64Util.base64StringToFile(base64_str,"/Users/frank/out.jpg");
    }


    public static String getSampleImageBase64Str()  {
//        String imgFilePath="http://d.hiphotos.baidu.com/image/pic/item/a044ad345982b2b713b5ad7d3aadcbef76099b65.jpg";
//        String base64_str = null;//将网络图片编码为base64
//        try {
//            base64_str = encodeImageToBase64(new URL(imgFilePath));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return base64_str;

        String imgFilePath="/Users/frank/out.jpg";
//        File file = new File(imgFilePath);
        String base64_str = Base64Util.getImageBinary(imgFilePath,"jpg");
        return  base64_str;
    }

    public static String getSampleImageBase64StrMD5()  {
//        String imgFilePath="http://d.hiphotos.baidu.com/image/pic/item/a044ad345982b2b713b5ad7d3aadcbef76099b65.jpg";
//        String imgFilePath="file:///Users/frank/out.jpg";
//        String md5 = null;//将网络图片编码为base64
//        try {
//            md5 = encodeImageMD5(new URL(imgFilePath));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
////        return MD5Util.getMD5(base64_str,false,32);
//        return md5;

        String imgFilePath="/Users/frank/out.jpg";
        String md5 = null;
        try {
            md5 = DigestUtils.md5Hex(new FileInputStream(imgFilePath));
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
    public static String encodeImageToBase64(URL url) throws Exception {
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
     * 将网络图片MD5签名
     *
     * @param url
     * @return
     * @throws Exception
     */
    public static String encodeImageMD5(URL url) throws Exception {
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

            String md5 = DigestUtils.md5Hex(inStream);
//关闭输入流
            inStream.close();


            System.out.println("网络文件[{}]MD5字符串:[{}]"+url.toString()+md5);
            return md5;//返回Base64编码过的字节数组字符串
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("图片上传失败,请联系客服!");
        }
    }


}