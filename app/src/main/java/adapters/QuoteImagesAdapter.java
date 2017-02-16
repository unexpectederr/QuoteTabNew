package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import activities.quotetabnew.R;
import helpers.main.AppController;
import helpers.main.Constants;
import helpers.other.QuoteImageView;
import listeners.OnImageClickListener;
import models.images.ImageSuggestion;

/**
 * Created by Spaja on 14-Feb-17.
 */

public class QuoteImagesAdapter extends RecyclerView.Adapter<QuoteImagesAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<ImageSuggestion> mDataSet;
    private QuoteImageView largeImage;
    private int screenWidth;

    public QuoteImagesAdapter(Context context, ArrayList<ImageSuggestion> mDataSet,
                              QuoteImageView largeImage, int screenWidth) {

        this.context = context;
        this.mDataSet = mDataSet;
        this.largeImage = largeImage;
        this.screenWidth = screenWidth;
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
                .inflate(R.layout.preview_image_list_item, parent, false);

        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(screenWidth/4, screenWidth/4);
        v.setLayoutParams(lp);

        return new QuoteImagesAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        String imageUrl;

        if (mDataSet.get(position).getImageId() != 0) {

            AppController.loadImageIntoView(context, mDataSet.get(position).getImageId(),
                    holder.quoteImage, false, false);

            imageUrl = Constants.COVER_IMAGES_URL + mDataSet.get(position).getImageId() + ".jpg";

        } else {

            Glide.with(context)
                    .load(mDataSet.get(position).getPriviewImageUrl())
                    .into(holder.quoteImage);

            imageUrl = mDataSet.get(position).getPriviewImageUrl();
        }

        holder.quoteImage.setOnClickListener(new OnImageClickListener
                (context, largeImage, imageUrl));


    }


    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

}
