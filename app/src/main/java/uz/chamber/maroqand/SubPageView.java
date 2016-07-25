package uz.chamber.maroqand;

import android.app.ActionBar;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by WTF on 2016-07-25.
 */
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

        parser = new Parser("http://chamber.uz/en/page/2068", callBackNetwork );

        title_tv = (TextView)findViewById(R.id.title_subpage);



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
                    TextView textView = (TextView)findViewById(R.id.breadcrumb_subpage);
                    String string=breadcrumb.get(0);
                    for(int i=1;i<breadcrumb.size();i++){
                        string+=" / ";
                        string+=breadcrumb.get(i);
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
                    LinearLayout linearLayout = (LinearLayout)findViewById(R.id.contentDynamic_subpage);
                    for(int i=0; i<content.size();i++){
                        switch(content.get(i).getSelect()){
                            case 0: //text
                                TextView textView = new TextView(SubPageView.this);
                                textView.setLayoutParams(new LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
                                textView.setPadding(10,10,10,10);
                                textView.setTextSize(13);
                                textView.setText(content.get(i).getText());
                                linearLayout.addView(textView);
                                break;
                            case 1: //hyper
                                TextView hyperTextView = new TextView(SubPageView.this);
                                hyperTextView.setLayoutParams(new LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
                                hyperTextView.setPadding(10,10,10,10);
                                hyperTextView.setTextSize(13);
                                hyperTextView.setMovementMethod(LinkMovementMethod.getInstance());
                                hyperTextView.setClickable(true);
                                hyperTextView.setText(Html.fromHtml("<a href="+content.get(i).getHtml()+">"+content.get(i).getText()+"</a>"));
                                linearLayout.addView(hyperTextView);
                                break;
                            case 2: //picture
                                //todo 미완
                                ImageView imageView = new ImageView(SubPageView.this);
                                imageView.setLayoutParams(new LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
                                imageView.setPadding(10,10,10,10);
                                new DownloadImageTask(imageView).execute(content.get(i).getSrcUrl());
                                linearLayout.addView(imageView);
                                break;
                        }
                    }
                }
            });
        }
    };

}
