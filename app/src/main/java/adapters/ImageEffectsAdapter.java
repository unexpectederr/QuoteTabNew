package adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import digitalbath.quotetab.R;
import de.hdodenhof.circleimageview.CircleImageView;
import helpers.other.QuoteImageView;
import listeners.OnEffectClickListener;
import models.filters.Filter;

/**
 * Created by Spaja on 14-Feb-17.
 */

public class ImageEffectsAdapter extends RecyclerView.Adapter<ImageEffectsAdapter.MyViewHolder> {

    private ArrayList<Filter> mDataSet;
    private Context context;
    private QuoteImageView imageLarge;

    public ImageEffectsAdapter(ArrayList<Filter> mDataSet, Context context, QuoteImageView imageLarge) {

        this.mDataSet = mDataSet;
        this.context = context;
        this.imageLarge = imageLarge;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        CircleImageView effectImage;
        TextView effectName;

        MyViewHolder(View itemView) {
            super(itemView);

            effectImage = (CircleImageView) itemView.findViewById(R.id.effect_image);
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
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.effectName.setText(mDataSet.get(position).getFilterName());

        try {
            Class<?> clazz = Class.forName(mDataSet.get(position).getFilterClass());
            Constructor<?> ctor = clazz.getConstructor(Context.class);
            int imageId = R.drawable.filter_image;
            Glide.with(context).load(imageId).bitmapTransform((Transformation<Bitmap>)
                    ctor.newInstance(context)).into(holder.effectImage);
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException
                | ClassNotFoundException | IllegalAccessException e) {
            e.printStackTrace();
        }

        holder.itemView.setOnClickListener(new OnEffectClickListener(this, mDataSet.get(position).getFilterClass(),
                context, imageLarge, mDataSet, position));

        holder.effectImage.setBorderColor(mDataSet.get(position).isSelected() ?
                context.getResources().getColor(R.color.colorPrimary) :
                context.getResources().getColor(R.color.light_gray_ultra));

    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

}
