package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;
import digitalbath.quotetab.R;
import models.search.SearchResponse;

/**
 * Created by Spaja on 22-Feb-17.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {

    public static final int TYPE_ITEM_AUTHOR = 0;
    public static final int TYPE_HEADER = 1;
    public static final int TYPE_ITEM_QUOTE = 2;
    private SearchResponse mDataSet;
    private Context context;
    private int responseSize;

    public SearchAdapter(SearchResponse mDataSet, Context context) {

        this.mDataSet = mDataSet;
        this.context = context;
        responseSize = mDataSet.getAuthors().size() + mDataSet.getQuotes().size();

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        if (viewType == TYPE_ITEM_AUTHOR) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            v = inflater.inflate(R.layout.fav_authors_list_item, parent, false);
        } else if (viewType == TYPE_HEADER) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            v = inflater.inflate(R.layout.authors_list_header, parent, false);
        } else if (viewType == TYPE_ITEM_QUOTE) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            v = inflater.inflate(R.layout.quote_list_item, parent, false);
        }
    return new SearchAdapter.MyViewHolder(v);
    }



    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return responseSize;
    }

    @Override
    public int getItemViewType(int position) {
        int itemType = 0;
        for (int i = 0; i < responseSize; i++) {
            if (position == 0) {
                itemType = TYPE_HEADER;
            } else if (position < 1 + mDataSet.getAuthors().size() && mDataSet.getAuthors().size() != 0) {
                itemType = TYPE_ITEM_QUOTE;
            } else if (position == 2 + mDataSet.getAuthors().size() && mDataSet.getAuthors().size() != 0) {
                itemType = TYPE_HEADER;
            } else if (position > 2 + mDataSet.getAuthors().size() && mDataSet.getAuthors().size() != 0)
                itemType = TYPE_ITEM_AUTHOR;
        }
        return itemType;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView authorName;
        TextView authorInfo;
        CircleImageView authorImage;
        ImageView favoriteIcon;


        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
