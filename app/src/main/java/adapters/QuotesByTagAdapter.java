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
import listeners.OnFavoriteClickListener;
import listeners.OnShareClickListener;
import models.authors.Quote;

/**
 * Created by Spaja on 10-Jan-17.
 */

public class QuotesByTagAdapter extends RecyclerView.Adapter<QuotesByTagAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Quote> mDataSet;

    public QuotesByTagAdapter(Context context, ArrayList<Quote> mDataSet) {
        this.context = context;
        this.mDataSet = mDataSet;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView quoteText;
        ImageView shareText;
        ImageView favoriteText;
        TextView quoteTag, quoteTag2, quoteTag3;

        ViewHolder(View itemView) {
            super(itemView);

            quoteText = (TextView) itemView.findViewById(R.id.quoteText);
            shareText = (ImageView) itemView.findViewById(R.id.share_icon_text);
            favoriteText = (ImageView) itemView.findViewById(R.id.ic_favorite_text);
            quoteTag = (TextView) itemView.findViewById(R.id.quoteTag);
            quoteTag2 = (TextView) itemView.findViewById(R.id.quoteTag2);
            quoteTag3 = (TextView) itemView.findViewById(R.id.quoteTag3);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.author_details_recycler_list_item, parent, false);
        return new QuotesByTagAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.quoteText.setText(mDataSet.get(position).getQuoteDetails().getQuoteText());
        holder.shareText.setOnClickListener(new OnShareClickListener(context));
        holder.favoriteText.setOnClickListener(new OnFavoriteClickListener(context));
        String[] tags = mDataSet.get(position).getQuoteDetails().getCategories().split(" ");

        holder.quoteTag.setText(tags[0]);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
