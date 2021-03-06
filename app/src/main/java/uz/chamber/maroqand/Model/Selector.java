package uz.chamber.maroqand.Model;

import android.util.Log;

public class Selector {
    int select; // text=0, hyper=1, image=2
    String text;
    String html;
    String srcUrl;

    public Selector(String text, String html, String srcUrl, String inputImageUrl) {
        this.text = text;
        this.html = html;
        this.srcUrl = srcUrl;
        if (srcUrl != "") {
            select = 2;
        } else if (inputImageUrl != "") {
            this.srcUrl = inputImageUrl;
            Log.i("aa", inputImageUrl);
            select = 2;
        } else if (html != "") {
            select = 1;
        } else {
            select = 0;
        }
    }

    public int getSelect() {
        return select;
    }

    public void setSelect(int select) {
        this.select = select;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getSrcUrl() {
        return srcUrl;
    }

    public void setSrcUrl(String srcUrl) {
        this.srcUrl = srcUrl;
    }
}
