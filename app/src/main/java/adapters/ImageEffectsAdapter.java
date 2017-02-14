package adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import activities.quotetabnew.R;
import helpers.main.Constants;
import listeners.OnEffectClickListener;
import models.images.Effect;

/**
 * Created by Spaja on 14-Feb-17.
 */

public class ImageEffectsAdapter extends RecyclerView.Adapter<ImageEffectsAdapter.MyViewHolder> {

    private ArrayList<Effect> mDataSet;
    private Context context;
    private ImageView imageLarge;

    public ImageEffectsAdapter(ArrayList<Effect> mDataSet, Context context, ImageView imageLarge) {

        this.mDataSet = mDataSet;
        this.context = context;
        this.imageLarge = imageLarge;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView effectImage;
        TextView effectName;

        public MyViewHolder(View itemView) {
            super(itemView);

            effectImage = (ImageView) itemView.findViewById(R.id.effect_image);
            effectName = (TextView) itemView.findViewById(R.id.effect_name);
        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.effect_list_item, parent, false);
        return new ImageEffectsAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.effectName.setText(mDataSet.get(position).getEffectName());
        Glide.with(context).load(Constants.COVER_IMAGES_URL + mDataSet.get(position).getImageId() + ".jpg").into(holder.effectImage);

        holder.itemView.setOnClickListener(new OnEffectClickListener(context, imageLarge, position));
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

}
