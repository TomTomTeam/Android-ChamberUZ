package uz.chamber.maroqand.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import uz.chamber.maroqand.Util.AppConfig;
import uz.chamber.maroqand.Model.ATag;
import uz.chamber.maroqand.Model.FooterData;
import uz.chamber.maroqand.R;


public class FooterView extends LinearLayout implements View.OnClickListener{

    private Button btAddress;
    private Button btLinks;
    private Button btSend;
    private LinearLayout llAddress;
    private LinearLayout llLinks;
    private LinearLayout llSend;
    private View v;

    public FooterView(Context context) {
        super(context);

        initView();
        setAddressTab();
        setLinkTab();
        setConnectCCITab();
        addView(v);
    }

    private void initView(){
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);
        v = li.inflate(R.layout.view_footer, this, false);

        btAddress = (Button) v.findViewById(R.id.bt_footer_address);
        btLinks = (Button) v.findViewById(R.id.bt_footer_links);
        btSend = (Button) v.findViewById(R.id.bt_footer_send);

        btAddress.setOnClickListener(this);
        btLinks.setOnClickListener(this);
        btSend.setOnClickListener(this);

        llAddress = (LinearLayout) v.findViewById(R.id.ll_footer_address);
        llLinks = (LinearLayout) v.findViewById(R.id.ll_footer_link);
        llSend = (LinearLayout) v.findViewById(R.id.ll_footer_send);

        btAddress.setText(FooterData.getInstance().getAddressTitle());
        btLinks.setText((FooterData.getInstance().getLinkTitle()));
        btSend.setText(FooterData.getInstance().getConnectCCITitle());

        btAddress.setTextColor(getResources().getColor(R.color.whiteblue));
        btLinks.setTextColor(getResources().getColor(R.color.whiteblue));
        btSend.setTextColor(getResources().getColor(R.color.whiteblue));
    }

    private void setConnectCCITab() {
        final ArrayList<ATag> sendList = FooterData.getInstance().getConnectCCIContent();

        for(int i=0; i<sendList.size(); i++){
            final int j = i;
            Button button = new Button(getContext());
            button.setText(sendList.get(i).getTitle());
            button.setTextColor(getResources().getColor(R.color.whiteblue));
            button.setBackgroundColor(getContext().getResources().getColor(R.color.blue));
            button.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(AppConfig.getRealPath(sendList.get(j).getUrl())));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getContext().startActivity(intent);
                }
            });
            llSend.addView(button);
        }
    }

    private void setLinkTab() {
        ArrayList<ATag> listLinks = FooterData.getInstance().getLinkContent();

        for(int i=0; i<listLinks.size(); i++){
            TextView textView = new TextView(getContext());
            textView.setText(listLinks.get(i).getTitle());
            textView.setTextColor(getResources().getColor(R.color.whiteblue));
            Log.i("aa", listLinks.get(i).getTitle());
            llLinks.addView(textView);
        }
    }

    private void setAddressTab() {
        TextView tvAddress = (TextView) v.findViewById(R.id.tv_footer_address);
        tvAddress.setText(FooterData.getInstance().getAddressContent());
        tvAddress.setTextColor(getResources().getColor(R.color.whiteblue));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_footer_address:
                llAddress.setVisibility(VISIBLE);
                llLinks.setVisibility(GONE);
                llSend.setVisibility(GONE);
                break;
            case R.id.bt_footer_links:
                llAddress.setVisibility(GONE);
                llLinks.setVisibility(VISIBLE);
                llSend.setVisibility(GONE);
                break;
            case R.id.bt_footer_send:
                llAddress.setVisibility(GONE);
                llLinks.setVisibility(GONE);
                llSend.setVisibility(VISIBLE);
                break;
        }
    }
}
