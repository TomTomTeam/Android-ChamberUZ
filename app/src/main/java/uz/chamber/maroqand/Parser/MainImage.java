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

/**
 * Created by lk on 2016. 7. 19..
 */
public class MainImage {
    String html = "http://chamber.uz/en/index";
    Document document;
    Elements titles;
    CallBack callBack;

    public MainImage(CallBack callBack){
        this.callBack = callBack;
        new ConnectThread().start();
    }


    private class ConnectThread extends Thread {
        @Override
        public void run() {
            document= Jsoup.parse(requestHttp(html));
            titles = document.select("div.carousel-inner");
            Elements media = titles.select("[src]");
            ArrayList<String> list = new ArrayList<>();
            for(int i=0; i<media.size(); i++){
                Element src = media.get(i);
                String a = src.toString().replace("<img src=\"", "http://chamber.uz");
                a = a.substring(0, a.length()-12);
                Log.i("aa",a);
                list.add(a);

            }
            callBack.done(list);

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
