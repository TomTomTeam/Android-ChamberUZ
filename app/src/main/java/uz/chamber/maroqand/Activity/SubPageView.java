package uz.chamber.maroqand.Activity;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

import uz.chamber.maroqand.AppConfig;
import uz.chamber.maroqand.AppController;
import uz.chamber.maroqand.CallBack.CallBackNetwork;
import uz.chamber.maroqand.Model.NewsListComponent;
import uz.chamber.maroqand.Model.TabList;
import uz.chamber.maroqand.Parser.Parser;
import uz.chamber.maroqand.R;
import uz.chamber.maroqand.Model.Selector;

public class SubPageView extends AppCompatActivity {
    Parser parser;
    String spage_header;
    ArrayList<String> breadcrumb;
    ArrayList<Selector> page_content;
    String type;
    String date;
    TextView title_tv;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subpage);

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        type = intent.getStringExtra("type");
        date = intent.getStringExtra("date");

        parser = new Parser(url, callBackNetwork);

        title_tv = (TextView) findViewById(R.id.title_subpage);
        textView = (TextView) findViewById(R.id.breadcrumb_subpage);

    }

    CallBackNetwork callBackNetwork = new CallBackNetwork() {
        @Override
        public void setTitle(final String title) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    title_tv.setText(title);
                    findViewById(R.id.iv_subpage_news_icon).setVisibility(View.VISIBLE);
                }
            });
        }

        @Override
        public void setBreadcrumb(final ArrayList<String> breadcrumb) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(type.equals("news")){
                        textView.setText(date.substring(0,10));
                    }else {
                        String string = breadcrumb.get(0);
                        for (int i = 1; i < breadcrumb.size(); i++) {
                            string += " / ";
                            string += breadcrumb.get(i);
                        }
                        textView.setText(string);
                    }
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
                                Log.e("test", content.get(i).getText());
                                TextView textView = new TextView(SubPageView.this);
                                textView.setLayoutParams(new LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
                                textView.setPadding(50, 25, 50, 25);
                                textView.setTextSize(15);
                                textView.setTextColor(Color.BLACK);
                                textView.setText(content.get(i).getText());
                                linearLayout.addView(textView);
                                break;
                            case 1: //hyper
                                TextView hyperTextView = new TextView(SubPageView.this);
                                hyperTextView.setLayoutParams(new LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
                                hyperTextView.setPadding(50, 25, 50, 25);
                                hyperTextView.setTextSize(15);

                                hyperTextView.setMovementMethod(LinkMovementMethod.getInstance());
                                hyperTextView.setClickable(true);
                                hyperTextView.setText(Html.fromHtml("<a href=" + content.get(i).getHtml() + ">" + content.get(i).getText() + "</a>"));
                                linearLayout.addView(hyperTextView);
                                break;
                            case 2: //picture
                                NetworkImageView v = new NetworkImageView(getApplicationContext());
                                v.setScaleType(ImageView.ScaleType.CENTER_CROP);
                                v.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 900));
                                //v.setPadding(10, 10, 10, 10);
                                Log.e("src", content.get(i).getSrcUrl());
                                v.setImageUrl(AppConfig.getRealPath(content.get(i).getSrcUrl()), AppController.getInstance().getImageLoader());
                                linearLayout.addView(v);
                                break;
                        }
                    }
                    linearLayout.addView(new FooterView(getApplicationContext()));
                }
            });
        }
    };
}
