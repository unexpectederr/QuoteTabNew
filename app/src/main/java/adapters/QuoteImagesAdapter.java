package adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.media.effect.Effect;
import android.media.effect.EffectFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import activities.quotetabnew.R;
import helpers.main.AppController;
import listeners.OnImageClickListener;
import models.images.ImageSuggestion;

/**
 * Created by Spaja on 14-Feb-17.
 */

public class QuoteImagesAdapter extends RecyclerView.Adapter<QuoteImagesAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<ImageSuggestion> mDataSet;
    private ImageView largeImage;

    public QuoteImagesAdapter(Context context, ArrayList<ImageSuggestion> mDataSet, ImageView largeImage) {

        this.context = context;
        this.mDataSet = mDataSet;
        this.largeImage = largeImage;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView quoteImage;

        MyViewHolder(View itemView) {
            super(itemView);

            quoteImage = (ImageView) itemView.findViewById(R.id.quote_background_image);
        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quote_images_list_item, parent, false);
        return new QuoteImagesAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        if (mDataSet.get(position).getImageId() != 0) {
            AppController.loadImageIntoView(context, mDataSet.get(position).getImageId(),
                    holder.quoteImage, false, false);
        } else {
            Glide.with(context)
                    .load(mDataSet.get(position).getPriviewImageUrl())
                    .into(holder.quoteImage);
        }

        holder.quoteImage.setOnClickListener(new OnImageClickListener(context,
                largeImage, mDataSet.get(position).getImageId()));


    }


    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

}
