package com.ednerdaza.nubetecnologica.fuentededatos.mvc.controllers.adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ednerdaza.nubetecnologica.fuentededatos.R;
import com.ednerdaza.nubetecnologica.fuentededatos.classes.helpers.GlobalConfig;
import com.ednerdaza.nubetecnologica.fuentededatos.mvc.controllers.Interfaces.DelegateItemAdapter;
import com.ednerdaza.nubetecnologica.fuentededatos.mvc.models.entities.DataList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by administrador on 9/06/16.
 */
public class AdapterItems extends RecyclerView.Adapter<AdapterItems.ItemsViewHolder> {

    // delegate para que reciba el onclick desde fuera del adapter
    private DelegateItemAdapter mDelegateItemAdapter;
    public DelegateItemAdapter getDelegate() {
        return mDelegateItemAdapter;
    }
    public void setDelegate(DelegateItemAdapter mDelegateItemAdapter) {
        this.mDelegateItemAdapter = mDelegateItemAdapter;
    }

    ArrayList<DataList> items;
    Activity context;

    public AdapterItems(Activity context, ArrayList<DataList> items){
        this.context = context;
        this.items = items;
    }

    @Override
    public ItemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //return null;
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_main, parent, false);
        ItemsViewHolder itemsViewHolder = new ItemsViewHolder(v);
        return itemsViewHolder;
    }

    @Override
    public void onBindViewHolder(final ItemsViewHolder holder, final int position) {

        Log.v(GlobalConfig.APP_TAG, " ITEM --> " + items.get(position));
        Log.v(GlobalConfig.APP_TAG, " URL --> " + items.get(position).getImageURL());

        holder.tvTitle.setText(items.get(position).getName());

        //Helpers.readWebpage(items.get(position).getTextShortDescription());

        //String textoCorto = items.get(position).getTextShortDescription();

        holder.tvSummary.setText(items.get(position).getTextShortDescription());
        Picasso.with(context).load(items.get(position).getIconURL())
                .centerInside()
                .fit()
                .error(R.drawable.error)
                .placeholder(R.drawable.loading)
                .config(Bitmap.Config.RGB_565)
                .into(holder.ivImageUrl);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.v(GlobalConfig.APP_TAG, "HICE CLICK EN --> " + position);
                String title = items.get(position).getName();

                if(mDelegateItemAdapter!=null){
                    mDelegateItemAdapter.onItemClicked(items.get(position));
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        //return 0;
        return items.size();
    }

    public static class ItemsViewHolder extends RecyclerView.ViewHolder {
        CardView cvRoot;
        TextView tvTitle;
        TextView tvSummary;
        ImageView ivImageUrl;

        ItemsViewHolder(View itemView) {
            super(itemView);
            cvRoot = (CardView)itemView.findViewById(R.id.cv_layout);
            tvTitle = (TextView)itemView.findViewById(R.id.tv_title);
            tvSummary = (TextView)itemView.findViewById(R.id.tv_summary);
            ivImageUrl = (ImageView)itemView.findViewById(R.id.iv_image_url);
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

}
