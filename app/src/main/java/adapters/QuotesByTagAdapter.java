package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import digitalbath.quotetabnew.R;
import listeners.OnFavoriteClickListener;
import listeners.OnShareClickListener;
import listeners.OnTagClickListener;
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
        LinearLayout quoteTags;
        TextView quoteTag;

        ViewHolder(View itemView) {
            super(itemView);

            quoteText = (TextView) itemView.findViewById(R.id.quoteText);
            shareText = (ImageView) itemView.findViewById(R.id.share_icon);
            favoriteText = (ImageView) itemView.findViewById(R.id.favorite_icon);
            quoteTags = (LinearLayout) itemView.findViewById(R.id.quote_tags);
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

        for (int i = 0; i < tags.length; i++){
            if (i < 3) {
                holder.quoteTag = new TextView(context);
                holder.quoteTag.setBackgroundResource(R.drawable.button_outline);
                holder.quoteTag.setText(tags[i]);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                params.setMarginEnd(12);
                holder.quoteTag.setLayoutParams(params);
                holder.quoteTag.setGravity(Gravity.CENTER);
                holder.quoteTag.setTextColor(context.getResources().getColor(R.color.cardview_light_background));
                holder.quoteTag.setPadding(12, 0, 12, 5);
                holder.quoteTags.addView(holder.quoteTag);
                holder.quoteTag.setOnClickListener(new OnTagClickListener(context, holder.quoteTag.getText().toString()));
            }
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
