package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import digitalbath.quotetabnew.R;
import helpers.main.AppController;
import helpers.main.AppHelper;
import helpers.main.Constants;
import helpers.other.ReadAndWriteToFile;
import listeners.OnAuthorClickListener;
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
    private int lastPosition = -1;

    public QuotesByTagAdapter(Context context, ArrayList<Quote> mDataSet) {
        this.context = context;
        this.mDataSet = mDataSet;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView quoteText;
        ImageView shareText;
        LinearLayout quoteTags;
        ImageView cardImage;
        TextView authorName;
        ImageView favoriteIcon;

        ViewHolder(View itemView) {
            super(itemView);

            quoteText = (TextView) itemView.findViewById(R.id.quoteText);
            shareText = (ImageView) itemView.findViewById(R.id.share_icon);
            quoteTags = (LinearLayout) itemView.findViewById(R.id.quote_tags);
            cardImage = (ImageView) itemView.findViewById(R.id.card_image);
            authorName = (TextView) itemView.findViewById(R.id.card_author_name);
            favoriteIcon = (ImageView) itemView.findViewById(R.id.favorite_icon);

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quote_recycler_list_item, parent, false);
        return new QuotesByTagAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        if (mDataSet.get(position).getQuoteDetails() != null) {

            if (mDataSet.get(position).getImageId() == 0)
                mDataSet.get(position).setImageId(AppController.getBitmapIndex());

            ArrayList<Quote> favoriteQuotes = ReadAndWriteToFile.getFavoriteQuotes(context);

            if (favoriteQuotes.size() != 0) {
                for (int i = 0; i < favoriteQuotes.size(); i++) {
                    if (mDataSet.get(position).getQuoteDetails().getQuoteId().equals(favoriteQuotes.get(i).
                            getQuoteDetails().getQuoteId())) {
                        holder.favoriteIcon.setImageResource(R.drawable.ic_favorite);
                        mDataSet.get(position).setFavorite(true);
                        break;
                    } else {
                        holder.favoriteIcon.setImageResource(R.drawable.ic_favorite_empty);
                        mDataSet.get(position).setFavorite(false);
                    }
                }
            } else {
                holder.favoriteIcon.setImageResource(R.drawable.ic_favorite_empty);
            }

            AppController.loadImageIntoView(context, mDataSet.get(position).getImageId(),
                    holder.cardImage, false);

            holder.quoteText.setText(mDataSet.get(position).getQuoteDetails().getQuoteText());
            holder.quoteText.setTypeface(AppHelper.getRalewayLigt(holder.quoteText.getContext()));

            holder.authorName.setText("- " + mDataSet.get(position).getQuoteDetails().getAuthorName() + " -");
            holder.authorName.setOnClickListener(new OnAuthorClickListener(context, mDataSet.get(position)
                    .getQuoteDetails().getAuthorId()));

            holder.shareText.setOnClickListener(new OnShareClickListener(context,
                    holder.quoteText.getText().toString(),
                    mDataSet.get(position).getQuoteDetails().getAuthorName()));

            holder.favoriteIcon.setOnClickListener(new OnFavoriteClickListener(context,
                    mDataSet.get(position), holder.favoriteIcon, position, new QuotesByTagAdapter(context, mDataSet)));

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

    private void setAnimation(View viewToAnimate, int position) {

        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.abc_slide_in_bottom);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
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
