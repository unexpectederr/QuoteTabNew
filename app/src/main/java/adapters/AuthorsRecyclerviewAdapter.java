package adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import digitalbath.quotetabnew.R;
import helpers.AppController;
import models.Authors.Results;

/**
 * Created by Spaja on 26-Dec-16.
 */

public class AuthorsRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Results> mDataset;
    private ArrayList<String> namesList = new ArrayList<>();
    private ArrayList<String> imagesList = new ArrayList<>();
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_HEADER = 1;

    public AuthorsRecyclerViewAdapter(ArrayList<Results> mDataSet) {
        this.mDataset = mDataSet;
    }

    private class ViewHolderItem extends RecyclerView.ViewHolder {

        TextView authorName;
        ImageView authorImage;

        public ViewHolderItem(View itemView) {
            super(itemView);
            authorName = (TextView) itemView.findViewById(R.id.author_name);
            authorImage = (ImageView) itemView.findViewById(R.id.author_image);
            for (int i = 0; i < mDataset.size(); i++) {
                namesList.add(mDataset.get(i).getFields().getName().get(0));
                //Uvijek mi bude null pointer ovdje!!!
                //if (mDataset.get(i).getFields().getImageUrl().get(0) != null) {
                    //imagesList.add(mDataset.get(i).getFields().getImageUrl().get(0));
                //}
            }
        }
    }

    private class ViewHolderHeader extends RecyclerView.ViewHolder {

        public ViewHolderHeader(View itemView) {
            super(itemView);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //Ovo je planirano za header view
        if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.authors_recyclerview_list_item, parent, false);
            return new ViewHolderItem(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.authors_recyclerview_list_item, parent, false);
            return new ViewHolderItem(v);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ViewHolderItem) {
            ((ViewHolderItem) holder).authorName.setText(namesList.get(position));
            //Glide.with(((ViewHolderItem) holder).authorImage.getContext()).load(AppController.IMAGES_URL + imagesList.get(position)).into(((ViewHolderItem) holder).authorImage);
        }
    }

    @Override
    public int getItemViewType(int position) {
        //napraviti uslov za header view
        return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
