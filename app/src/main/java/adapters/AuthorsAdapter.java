package adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tonicartos.superslim.GridSLM;
import com.tonicartos.superslim.LayoutManager;
import com.tonicartos.superslim.LinearSLM;

import java.util.ArrayList;

import digitalbath.quotetabnew.R;
import helpers.AppController;
import models.Authors.Results;

/**
 * Created by Spaja on 26-Dec-16.
 */

public class AuthorsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Results> mDataset;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_HEADER = 1;

    public AuthorsAdapter(ArrayList<Results> mDataSet) {
        this.mDataset = mDataSet;
    }

    private class ViewHolderItem extends RecyclerView.ViewHolder {

        TextView authorName;
        ImageView authorImage;

        ViewHolderItem(View itemView) {
            super(itemView);
            authorName = (TextView) itemView.findViewById(R.id.author_name);
            authorImage = (ImageView) itemView.findViewById(R.id.author_image);
        }

        void setLayoutParams(LayoutManager.LayoutParams params) {
            itemView.setLayoutParams(params);
        }

        LayoutManager.LayoutParams getLayoutParams() {
            return LayoutManager.LayoutParams.from(itemView.getLayoutParams());
        }
    }

    private class ViewHolderHeader extends RecyclerView.ViewHolder {

        TextView header;

        public ViewHolderHeader(View itemView) {
            super(itemView);
            header = (TextView) itemView.findViewById(R.id.header);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if (viewType == TYPE_HEADER) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.authors_recyclerview_list_header, parent, false);
        } else {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.authors_recyclerview_list_item, parent, false);
        }
        return new ViewHolderItem(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (mDataset.get(position).getFields().getImageUrl() != null) {
            ((ViewHolderItem) holder).authorName.setText(mDataset.get(position).getFields().getName().get(0));
            Glide.with(((ViewHolderItem) holder).authorImage.getContext())
                    .load(AppController.IMAGES_URL + mDataset.get(position).getFields().getImageUrl().get(0))
                    .placeholder(R.drawable.avatar)
                    .into(((ViewHolderItem) holder).authorImage);
            final LayoutManager.LayoutParams params = ((ViewHolderItem) holder).getLayoutParams();
            params.setSlm(GridSLM.ID);
            params.headerDisplay = LayoutManager.LayoutParams.HEADER_STICKY;
            params.setFirstPosition(mDataset.get(position).getSectionFirstPosition());
            ((ViewHolderItem) holder).setLayoutParams(params);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mDataset.get(position).isHeader() ? TYPE_HEADER : TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
