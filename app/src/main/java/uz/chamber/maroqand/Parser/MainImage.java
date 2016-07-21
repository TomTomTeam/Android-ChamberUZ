package uz.chamber.maroqand.Parser;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import uz.chamber.maroqand.CallBack;
import uz.chamber.maroqand.Model.MainViewPagerData;

/**
 * Created by lk on 2016. 7. 19..
 */
public class MainImage {
    String html = "http://chamber.uz/en/index"; //todo Language setting
    Document document;
    Elements inner;
    Elements items;
    Element image;
    Element aTag;
    String title;
    CallBack callBack;
    String linkUrl;
    String imgUrl;


    public MainImage(CallBack callBack){
        this.callBack = callBack;
        new ConnectThread().start();
    }

    private class ConnectThread extends Thread {
        @Override
        public void run() {
            ArrayList<MainViewPagerData> dataList = new ArrayList<>();
            document= Jsoup.parse(requestHttp(html));
            inner = document.select("div.carousel-inner");
            items = inner.select("div.item");
            for(int i=0; i<items.size(); i++){
                Log.e("aa", items.get(i).toString());
                image = items.get(i).select("[src]").first();
                imgUrl = image.toString().replace("<img src=\"", "http://chamber.uz");
                imgUrl = imgUrl.substring(0, imgUrl.length()-12);
                aTag = items.get(i).select("a[href]").first();
                title = items.get(i).text();
                linkUrl = aTag.toString().replace("\"> "+title +" </a>","").replace("<a class=\"carousel-caption\" href=\"", "http://chamber.uz");

                MainViewPagerData data = new MainViewPagerData(imgUrl, title, linkUrl);
                dataList.add(data);

                Log.i("aa",imgUrl + " ./  " + aTag + " / " + title);

            }
            callBack.done(dataList);

        }

        public String requestHttp(String urlStr){
            try {
                URL url = new URL(urlStr);

                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                InputStream is = con.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String response="";
                String tmp = "";
                while( (tmp = reader.readLine())!= null){
                    response += tmp;
                    Log.e("parse", tmp);
                }
                is.close();

                return response;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
