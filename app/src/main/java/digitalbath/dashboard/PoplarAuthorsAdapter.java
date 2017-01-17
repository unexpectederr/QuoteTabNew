package digitalbath.dashboard;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.util.List;
import digitalbath.quotetabnew.R;
import helpers.main.Constants;
import models.dashboard.PopularAuthor;


public class PoplarAuthorsAdapter extends RecyclerView.Adapter<PoplarAuthorsAdapter.ViewHolder> {

    private List<PopularAuthor> mDataSet;
    private Context mContext;
    private int increment;

    public PoplarAuthorsAdapter(List<PopularAuthor> myDataSet, Context context) {
        mDataSet = myDataSet;
        mDataSet.add(new PopularAuthor());
        mDataSet.add(new PopularAuthor());

        mContext = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mName1;
        public ImageView mImage1;
        public TextView mName2;
        public ImageView mImage2;
        public TextView mName3;
        public ImageView mImage3;

        public ViewHolder(View v) {
            super(v);
            mName1 = (TextView) v.findViewById(R.id.name1);
            mImage1 = (ImageView) v.findViewById(R.id.image1);
            mName2 = (TextView) v.findViewById(R.id.name2);
            mImage2 = (ImageView) v.findViewById(R.id.image2);
            mName3 = (TextView) v.findViewById(R.id.name3);
            mImage3 = (ImageView) v.findViewById(R.id.image3);
        }
    }


    @Override
    public PoplarAuthorsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {

        int layoutId = R.layout.popular_authors_type_one;

        switch (viewType) {
            case 1:
                layoutId = R.layout.popular_authors_type_one;
                break;
            case 2:
                layoutId = R.layout.popular_authors_type_two;
                break;
            case 3:
                layoutId = R.layout.popular_authors_type_three;
                break;
        }

        View v = LayoutInflater.from(parent.getContext())
                .inflate(layoutId, parent, false);

        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.mName1.setText(mDataSet.get(position + increment).getName());
        Glide.with(mContext).load(Constants.IMAGES_URL +
                mDataSet.get(position + increment).getImageUrl()).into(holder.mImage1);

        holder.mName2.setText(mDataSet.get(position + 1 + increment).getName());
        Glide.with(mContext).load(Constants.IMAGES_URL +
                mDataSet.get(position+ 1 + increment).getImageUrl()).into(holder.mImage2);

        holder.mName3.setText(mDataSet.get(position + 2 + increment).getName());
        Glide.with(mContext).load(Constants.IMAGES_URL +
                mDataSet.get(position + 2 + increment).getImageUrl()).into(holder.mImage3);

        increment += 2;

    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return 1;
        else if (position == 1)
            return 2;
        else if (position == 2)
            return 3;
        else
            return 1;
    }

    @Override
    public int getItemCount() {
        return mDataSet.size()/3;
    }
}


