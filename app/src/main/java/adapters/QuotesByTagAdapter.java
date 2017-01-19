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
import helpers.main.AppHelper;
import helpers.main.Constants;
import listeners.OnFavoriteClickListener;
import listeners.OnShareClickListener;
import listeners.OnTagClickListener;
import models.quotes.Quote;

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
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quote_recycler_list_item, parent, false);
        return new QuotesByTagAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (mDataSet.get(position).getQuoteDetails() != null) {
            holder.quoteText.setText(mDataSet.get(position).getQuoteDetails().getQuoteText());
            holder.shareText.setOnClickListener(new OnShareClickListener(context));
            holder.favoriteText.setOnClickListener(new OnFavoriteClickListener(context));
            String[] tags = mDataSet.get(position).getQuoteDetails().getCategories().split(" ");

            holder.quoteTags.removeAllViews();

            for (int i = 0; i < tags.length; i++) {

                if (i < Constants.MAX_NUMBER_OF_QUOTES) {

                    TextView quoteTag = new TextView(context);
                    quoteTag.setBackgroundResource(R.drawable.background_outline);
                    quoteTag.setText(tags[i]);
                    quoteTag.setPadding(30, 15, 30, 15);

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT));

                    params.setMarginEnd(12);

                    quoteTag.setLayoutParams(params);
                    quoteTag.setGravity(Gravity.CENTER);
                    quoteTag.setTextColor(context.getResources().getColor(R.color.light_gray));
                    quoteTag.setTypeface(AppHelper.getRalewayLigt(context));
                    quoteTag.setOnClickListener(new OnTagClickListener(context, tags[i]));

                    holder.quoteTags.addView(quoteTag);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public void addQuotes(ArrayList<Quote> topQuotes) {
        mDataSet.addAll(topQuotes);
        notifyItemRangeInserted(mDataSet.size() - topQuotes.size(), topQuotes.size());
    }
}
