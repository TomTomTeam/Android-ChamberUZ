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
    Elements elements;
    String title, content, imageUrl;
    CallBackNetwork network;

    public ParserPictureText(String html, CallBackNetwork network) {
        this.html=html;
        ConnectThread connectThread = new ConnectThread();
        connectThread.start();
        this.network = network;
    }

    public void done(String title){

    }

    class ConnectThread extends Thread {
        @Override
        public void run() {
            super.run();
            try{
                document= Jsoup.connect(html).get();

                elements = document.select("h1.spage-header");
                title=elements.text();
/*
                elements = document.select("div.page-content");

                for(Element e:elements){
                    content += (e.text()+"\n\n");
                }*/
            //    Elements elements = document.getElementsByTag("img");

                Element image = document.select("img").first();
                imageUrl=image.absUrl("src");
                /*
                for (Element ele : elements) {
						content+=ele.attr("src");
						content+="\n";
                }*/
                network.setTitle(imageUrl);;
                done(title);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

}