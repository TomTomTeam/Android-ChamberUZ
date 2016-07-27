package uz.chamber.maroqand.Activity;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import uz.chamber.maroqand.CallBack.CallBackNetwork;
import uz.chamber.maroqand.DownloadImageTask;
import uz.chamber.maroqand.Model.NewsListComponent;
import uz.chamber.maroqand.Parser.Parser;
import uz.chamber.maroqand.R;
import uz.chamber.maroqand.Model.Selector;

public class SubPageView extends AppCompatActivity {
    Parser parser;
    String spage_header;
    ArrayList<String> breadcrumb;
    ArrayList<Selector> page_content;
    TextView title_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subpage);

        parser = new Parser("http://chamber.uz/en/page/2068", callBackNetwork);

        title_tv = (TextView) findViewById(R.id.title_subpage);

        // todo remove this only test
        // new NewsTabParser("http://chamber.uz/en/news#cci_news", callBackNetwork);

    }

    CallBackNetwork callBackNetwork = new CallBackNetwork() {
        @Override
        public void setTitle(final String title) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    title_tv.setText(title);
                }
            });
        }

        @Override
        public void setBreadcrumb(final ArrayList<String> breadcrumb) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    TextView textView = (TextView) findViewById(R.id.breadcrumb_subpage);
                    String string = breadcrumb.get(0);
                    for (int i = 1; i < breadcrumb.size(); i++) {
                        string += " / ";
                        string += breadcrumb.get(i);
                    }
                    textView.setText(string);
                }
            });
        }

        @Override
        public void setContent(final ArrayList<Selector> content) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    LinearLayout linearLayout = (LinearLayout) findViewById(R.id.contentDynamic_subpage);
                    for (int i = 0; i < content.size(); i++) {
                        switch (content.get(i).getSelect()) {
                            case 0: //text
                                TextView textView = new TextView(SubPageView.this);
                                textView.setLayoutParams(new LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
                                textView.setPadding(10, 10, 10, 10);
                                textView.setTextSize(13);
                                textView.setText(content.get(i).getText());
                                linearLayout.addView(textView);
                                break;
                            case 1: //hyper
                                TextView hyperTextView = new TextView(SubPageView.this);
                                hyperTextView.setLayoutParams(new LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
                                hyperTextView.setPadding(10, 10, 10, 10);
                                hyperTextView.setTextSize(13);
                                hyperTextView.setMovementMethod(LinkMovementMethod.getInstance());
                                hyperTextView.setClickable(true);
                                hyperTextView.setText(Html.fromHtml("<a href=" + content.get(i).getHtml() + ">" + content.get(i).getText() + "</a>"));
                                linearLayout.addView(hyperTextView);
                                break;
                            case 2: //picture
                                ImageView imageView = new ImageView(SubPageView.this);
                                imageView.setLayoutParams(new LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
                                imageView.setPadding(10, 10, 10, 10);
                                Log.e("src", content.get(i).getSrcUrl());
                                linearLayout.addView(imageView);
                                new DownloadImageTask(imageView).execute("http://chamber.uz" + content.get(i).getSrcUrl());
                                break;
                        }
                    }
                }
            });
        }

        @Override
        public void setNewsContent(ArrayList<NewsListComponent> newsContent) {

        }
    };

}