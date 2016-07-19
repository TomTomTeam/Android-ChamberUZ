package uz.chamber.maroqand;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;

/**
 * Created by WTF on 2016-07-18.
 */
public class ParserPictureText {
    String html;
    Document document;
    Elements titles;
    String title;
    CallBackNetwork network;

    public ParserPictureText(String html, CallBackNetwork network)
    {
        this.html=html;
        ConnectThread connectThread = new ConnectThread();
        connectThread.start();
        this.network = network;
    }
    public String getTitle()
    {
        return title;
    }

    public class ConnectThread extends Thread
    {
        @Override
        public void run() {
            super.run();
            try{
                document= Jsoup.connect(html).get();
                titles = document.select("div.page-content");

                for(Element e:titles){
                    title += (e.text()+"\n\n");
                }
                network.title(title);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

}