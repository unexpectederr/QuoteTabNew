package listeners;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import activities.quote_maker.QuoteMaker;
import activities.quotetabnew.R;
import adapters.ImageEffectsAdapter;
import helpers.main.Constants;
import helpers.other.QuoteImageView;
import jp.wasabeef.glide.transformations.GrayscaleTransformation;
import jp.wasabeef.glide.transformations.gpu.InvertFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.KuwaharaFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.SepiaFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.ToonFilterTransformation;
import models.filters.Filter;

/**
 * Created by Spaja on 14-Feb-17.
 */

public class OnEffectClickListener implements View.OnClickListener {

    private Context context;
    private QuoteImageView imageView;
    private String className;
    private ArrayList<Filter> mDataSet;
    private ImageEffectsAdapter adapter;
    private int position;


    public OnEffectClickListener(ImageEffectsAdapter adapter, String className,
                                 Context context, QuoteImageView imageView,
                                 ArrayList<Filter> mDataSet, int position) {
        this.adapter = adapter;
        this.className = className;
        this.context = context;
        this.imageView = imageView;
        this.mDataSet = mDataSet;
        this.position = position;
    }


    @Override
    public void onClick(View view) {

        for (int i = 0; i < mDataSet.size(); i++) {
            if (mDataSet.get(i).isSelected()) {
                mDataSet.get(i).setSelected(false);
                adapter.notifyItemChanged(i);
            }
        }
        mDataSet.get(position).setSelected(true);
        adapter.notifyItemChanged(position);

        try {
            Class<?> clazz = Class.forName(className);
            Constructor<?> ctor = clazz.getConstructor(Context.class);
            Glide.with(context).load(imageView.getImageUrl()).bitmapTransform((Transformation<Bitmap>)
                    ctor.newInstance(context)).into(imageView);
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException
                | ClassNotFoundException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
