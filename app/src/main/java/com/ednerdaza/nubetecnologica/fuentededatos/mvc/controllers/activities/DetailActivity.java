package com.ednerdaza.nubetecnologica.fuentededatos.mvc.controllers.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.ednerdaza.nubetecnologica.fuentededatos.R;
import com.ednerdaza.nubetecnologica.fuentededatos.classes.helpers.GlobalConfig;
import com.ednerdaza.nubetecnologica.fuentededatos.classes.helpers.Helpers;
import com.ednerdaza.nubetecnologica.fuentededatos.mvc.models.entities.DataList;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private DataList dataList;

    ImageView mImageViewItemDetail, mHeaderViewItemDetail;
    TextView mTextViewTitle, mTextViewItemTitle, mTextViewItemSummary;

    Context context;
    private ImageView imageViewItemDetail;
    private TextView textViewItemTitle;
    private TextView textViewItemSummary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(GlobalConfig.APP_TAG, "/ ON CREATE / "+dataList);
        context = this;
        Helpers.setContexto(context);
        Helpers.setActivity(this);
        setContentView(R.layout.activity_detail);
        Intent intent = this.getIntent();
        dataList= (DataList)intent.getSerializableExtra("dataList");
        int kind = dataList.getId();
        //setTitle(dataList.getName());


        imageViewItemDetail = (ImageView)findViewById(R.id.iv_image_url_detail);
        textViewItemTitle = (TextView) findViewById(R.id.textview_details);
        textViewItemSummary = (TextView) findViewById(R.id.tv_summary_detail);

        textViewItemTitle.setText(dataList.getName());
        textViewItemSummary.setText(dataList.getTextDescription());
        Picasso.with(context).load(dataList.getImageURL())
                .centerInside()
                .fit()
                .error(R.drawable.errorimagebanner)
                .placeholder(R.drawable.loadingimagebanner)
                .config(Bitmap.Config.RGB_565)
                .into(imageViewItemDetail);

    }
}
