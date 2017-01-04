package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

import digitalbath.quotetabnew.R;
import models.Authors.AuthorListItem;

/**
 * Created by Spaja on 26-Dec-16.
 */

public class AuthorsAdapter extends RecyclerView.Adapter<AuthorsAdapter.ViewHolderItem> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_HEADER = 1;

    private ArrayList<AuthorListItem> mDataSet;
    private Context mContext;

    public AuthorsAdapter(Context context, ArrayList<AuthorListItem> dataSet) {
        this.mDataSet = dataSet;
        this.mContext = context;
    }

    public class ViewHolderItem extends RecyclerView.ViewHolder {

        TextView authorName;
        ImageView authorImage;

        ViewHolderItem(View itemView) {
            super(itemView);
            authorName = (TextView) itemView.findViewById(R.id.name);
            authorImage = (ImageView) itemView.findViewById(R.id.author_image);
        }
    }

    @Override
    public ViewHolderItem onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if (viewType == TYPE_HEADER) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.authors_recyclerview_list_header, parent, false);
        } else {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.authors_recyclerview_list_item, parent, false);
        }
        return new ViewHolderItem(v);
    }

    @Override
    public void onBindViewHolder(ViewHolderItem holder, int position) {

        holder.authorName.setText(mDataSet.get(position).getText());

    }

    @Override
    public int getItemViewType(int position) {
        return mDataSet.get(position).isHeader() ? TYPE_HEADER : TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

}
