package uz.chamber.maroqand;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {
    String htmlPageUrl = "http://chamber.uz/en/page/3435";

    TextView textviewHtmlDocument;

    String htmlContentInStringFormat;

    @Override

    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        textviewHtmlDocument = (TextView) findViewById(R.id.textView);


        textviewHtmlDocument.setMovementMethod(new ScrollingMovementMethod());

        Button htmlTitleButton = (Button) findViewById(R.id.button);

        assert htmlTitleButton != null;
        htmlTitleButton.setOnClickListener(new View.OnClickListener() {


            @Override

            public void onClick(View v) {
                JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
                jsoupAsyncTask.execute();
            }
        });
    }

    private class JsoupAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override

        protected void onPreExecute() {

            super.onPreExecute();

        }

        @Override

        protected Void doInBackground(Void... params) {

            try {

                Document doc = Jsoup.connect(htmlPageUrl).get();

                Elements links = doc.select("div.page-content");

                for (Element link : links) {

                    htmlContentInStringFormat += (link.attr("abs:href")

                            + "(" + link.text().trim() + ")\n");

                }

            } catch (IOException e) {

                e.printStackTrace();

            }

            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            textviewHtmlDocument.setText(htmlContentInStringFormat);
        }
    }

}



