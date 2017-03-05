package adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import digitalbath.quotetab.R;
import helpers.main.AppController;
import helpers.main.AppHelper;
import helpers.main.Constants;
import listeners.OnAuthorClickListener;
import listeners.OnFavoriteQuoteClickListener;
import listeners.OnQuoteClickListener;
import listeners.OnShareClickListener;
import listeners.OnTagClickListener;
import listeners.SaveImageToFileClickListener;
import models.quotes.Quote;
import models.quotes.QuoteReference;

/**
 * Created by Spaja on 10-Jan-17.
 */

public class QuotesAdapter extends RecyclerView.Adapter<QuotesAdapter.ViewHolder> {

    private Activity context;
    private ArrayList<Quote> mDataSet;
    private ArrayList<Quote> favoriteQuotes;
    private int lastPosition = -1;
    private boolean isFavorites;
    private boolean isFromAuthors;
    private RecyclerView recyclerView;
    private RelativeLayout emptyList;

    public QuotesAdapter(Activity context, ArrayList<Quote> mDataSet, ArrayList<Quote> favoriteQuotes,
                         boolean isFavorites, boolean isFromAuthors, RecyclerView recyclerView,
                         RelativeLayout emptyList) {

        this.context = context;
        this.mDataSet = mDataSet;
        this.favoriteQuotes = favoriteQuotes;
        this.isFavorites = isFavorites;
        this.isFromAuthors = isFromAuthors;
        this.recyclerView = recyclerView;
        this.emptyList = emptyList;

        if (recyclerView != null && emptyList != null && favoriteQuotes.size() != 0) {

            recyclerView.setVisibility(View.VISIBLE);
            emptyList.setVisibility(View.GONE);
        } else if (recyclerView != null && emptyList != null && favoriteQuotes.size() == 0) {
            recyclerView.setVisibility(View.GONE);
            emptyList.setVisibility(View.VISIBLE);
            emptyList.startAnimation(AppHelper.getAnimationUp(context));
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quote_list_item, parent, false);
        return new QuotesAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.downloadIcon.setOnClickListener(new SaveImageToFileClickListener(context,
                holder.relativeLayout, holder.tags, holder.actionButtons));

        if (mDataSet.get(position).getImageId() == 0)
            mDataSet.get(position).setImageId(AppController.getBitmapIndex());

        if (mDataSet.get(position).isFavorite()) {

            holder.favoriteIcon.setImageResource(R.drawable.ic_favorite);

        } else if (favoriteQuotes.size() != 0) {

            for (int i = 0; i < favoriteQuotes.size(); i++) {

                if (mDataSet.get(position).getQuoteId()
                        .equals(favoriteQuotes.get(i).getQuoteId())) {

                    mDataSet.get(position).setFavorite(true);
                    holder.favoriteIcon.setImageResource(R.drawable.ic_favorite);

                    break;
                }
            }
        }

        if (!mDataSet.get(position).isFavorite())
            holder.favoriteIcon.setImageResource(R.drawable.ic_favorite_empty);

        AppController.loadImageIntoView(context, mDataSet.get(position).getImageId(),
                holder.cardImage, false, false);

        holder.quoteText.setText(mDataSet.get(position).getQuoteText());
        holder.quoteText.setTypeface(AppHelper.getRalewayLight(holder.quoteText.getContext()));

        if (!isFromAuthors) {
            holder.authorName.setText("- " + mDataSet.get(position).getAuthor().getAuthorName() + " -");
            holder.authorName.setOnClickListener(new OnAuthorClickListener(context, mDataSet.get(position)
                    .getAuthor().getAuthorId()));
        }

        holder.shareIcon.setOnClickListener(new OnShareClickListener(context,
                holder.quoteText.getText().toString(), mDataSet.get(position).getAuthor()
                .getAuthorName(), Constants.COVER_IMAGES_URL + mDataSet.get(position).getImageId()
                + ".jpg", holder.quoteText));

        holder.favoriteIcon.setOnClickListener(new OnFavoriteQuoteClickListener(context, favoriteQuotes,
                holder.favoriteIcon, mDataSet.get(position), this, isFavorites, recyclerView, emptyList));

        if (mDataSet.get(position).getCategories() != null) {

            String[] tags = mDataSet.get(position).getCategories().split(" ");

            holder.quoteTags.removeAllViews();

            if (tags[0].trim().length() != 0) {

                for (int i = 0; i < tags.length; i++) {

                    if (i < Constants.MAX_NUMBER_OF_QUOTES) {

                        TextView quoteTag = new TextView(context);
                        quoteTag.setBackgroundResource(R.drawable.background_outline_g);
                        quoteTag.setText(tags[i]);
                        quoteTag.setPadding(30, 15, 30, 15);

                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT));

                        params.setMarginStart(12);

                        quoteTag.setLayoutParams(params);
                        quoteTag.setGravity(Gravity.CENTER);
                        quoteTag.setTextColor(context.getResources().getColor(R.color.light_gray));
                        quoteTag.setTypeface(AppHelper.getRalewayLight(context));
                        quoteTag.setOnClickListener(new OnTagClickListener(context, tags[i], isFavorites));

                        holder.quoteTags.addView(quoteTag);
                    }
                }
            }
        }

        holder.itemView.setOnClickListener(new OnQuoteClickListener(context, mDataSet.get(position)));

        setAnimation(holder.itemView, holder.getAdapterPosition());
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

    public void addQuotes(ArrayList<Quote> quotes) {

        mDataSet.addAll(quotes);
        notifyItemRangeInserted(mDataSet.size() - quotes.size(), quotes.size());

    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView quoteText;
        ImageView shareIcon;
        LinearLayout quoteTags;
        ImageView cardImage;
        TextView authorName;
        ImageView favoriteIcon;
        ImageView downloadIcon;
        RelativeLayout relativeLayout;
        HorizontalScrollView tags;
        LinearLayout actionButtons;

        ViewHolder(View itemView) {
            super(itemView);

            quoteText = (TextView) itemView.findViewById(R.id.quoteText);
            shareIcon = (ImageView) itemView.findViewById(R.id.share_icon);
            quoteTags = (LinearLayout) itemView.findViewById(R.id.quote_tags);
            cardImage = (ImageView) itemView.findViewById(R.id.card_image);
            authorName = (TextView) itemView.findViewById(R.id.card_author_name);
            favoriteIcon = (ImageView) itemView.findViewById(R.id.favorite_icon);
            downloadIcon = (ImageView) itemView.findViewById(R.id.download_icon);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.save_image);
            tags = (HorizontalScrollView) itemView.findViewById(R.id.tags_scroll_view);
            actionButtons = (LinearLayout) itemView.findViewById(R.id.qwe);
        }
    }
}
