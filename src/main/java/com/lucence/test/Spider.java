package com.lucence.test;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
/**
 * 
 * @author CallMeWhy
 * 
 */
public class Spider {
 private static HttpClient httpClient = new HttpClient();
 /**
  * @param path
  *            目标网页的链接
  * @return 返回布尔值，表示是否正常下载目标页面
  * @throws Exception
  *             读取网页流或写入本地文件流的IO异常
  */
 public static boolean downloadPage(String path) throws Exception {
  // 定义输入输出流
  InputStream input = null;
  OutputStream output = null;
  // 得到 post 方法
  GetMethod getMethod = new GetMethod(path);
  // 执行，返回状态码
  int statusCode = httpClient.executeMethod(getMethod);
  // 针对状态码进行处理
  // 简单起见，只处理返回值为 200 的状态码
  if (statusCode == HttpStatus.SC_OK) {
   input = getMethod.getResponseBodyAsStream();
   // 通过对URL的解析到文件名
   String filename=null;
 /*  if(path.endsWith("html")||path.endsWith("jsp")||path.endsWith("htm")||path.endsWith("asp")||path.endsWith("php")){
	    filename = path.substring(path.lastIndexOf('/') + 1);
   }else{
	    filename = path.substring(path.lastIndexOf('/') + 1)+ ".html";
   }*/
   if(path.endsWith("html")){
	    filename = path.substring(path.lastIndexOf('/') + 1);
  }else{
	  filename=String.valueOf((Math.random()*1000000))+"time.html";
  }
   // 获得文件输出流
   output = new FileOutputStream("E:\\DATASpider\\"+filename);
   // 输出到文件
   int tempByte = -1;
   while ((tempByte = input.read()) > 0) {
    output.write(tempByte);
   }
   // 关闭输入流
   if (input != null) {
    input.close();
   }
   // 关闭输出流
   if (output != null) {
    output.close();
   }
   HtmlParse.parseHtml("E:\\DATASpider\\"+filename);
   return true;
  }
  return false;
 }
 public static void main(String[] args) {
  try {
   // 抓取百度首页，保存
   Spider.downloadPage("http://www.baidu.com");
  } catch (Exception e) {
   e.printStackTrace();
  }
 }
}