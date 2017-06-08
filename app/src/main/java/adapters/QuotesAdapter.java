package adapters;

import android.app.Activity;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

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
    private ImageView mActiveDownloadIcon;
    private AppBarLayout appBarLayout;

    public QuotesAdapter(Activity context, ArrayList<Quote> mDataSet, ArrayList<Quote> favoriteQuotes,
                         boolean isFavorites, boolean isFromAuthors, RecyclerView recyclerView,
                         RelativeLayout emptyList, AppBarLayout appBarLayout) {

        this.context = context;
        this.mDataSet = mDataSet;
        this.favoriteQuotes = favoriteQuotes;
        this.isFavorites = isFavorites;
        this.isFromAuthors = isFromAuthors;
        this.recyclerView = recyclerView;
        this.emptyList = emptyList;
        this.appBarLayout = appBarLayout;

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

            for (int i = 0; i < 4; i++) {

                TextView tag = (TextView) holder.quoteTags.getChildAt(i);

                if (tags.length > i && tags[i] != null) {

                    tag.setVisibility(View.VISIBLE);
                    tag.setText(tags[i]);
                    tag.setOnClickListener(new OnTagClickListener
                            (context, tags[i], isFavorites));

                } else {

                    tag.setVisibility(View.GONE);

                }
            }
        }

        holder.itemView.setOnClickListener(new OnQuoteClickListener(context, mDataSet.get(position)));

        holder.downloadIcon.setOnClickListener(new SaveImageToFileClickListener(context,
                holder.quoteText.getText().toString(),
                mDataSet.get(position).getAuthor().getAuthorName(),
                Constants.COVER_IMAGES_URL + mDataSet.get(position).getImageId() + ".jpg",
                holder.quoteText.getLineCount(), this));

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

    public void setActiveDownloadButton(ImageView activeDownloadIcon) {
        this.mActiveDownloadIcon = activeDownloadIcon;
    }

    public ImageView getActiveDownloadIcon() {
        return mActiveDownloadIcon;
    }

    public void expandToolbar() {
        appBarLayout.setExpanded(true,true);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView quoteText;
        ImageView shareIcon;
        LinearLayout    quoteTags;
        ImageView cardImage;
        TextView authorName;
        ImageView favoriteIcon;
        ImageView downloadIcon;
        RelativeLayout relativeLayout;
        LinearLayout actionButtons;

        ViewHolder(View itemView) {
            super(itemView);

            quoteText = (TextView) itemView.findViewById(R.id.quoteText);
            shareIcon = (ImageView) itemView.findViewById(R.id.share_icon);
            quoteTags = (LinearLayout) itemView.findViewById(R.id.tags);
            cardImage = (ImageView) itemView.findViewById(R.id.card_image);
            authorName = (TextView) itemView.findViewById(R.id.card_author_name);
            favoriteIcon = (ImageView) itemView.findViewById(R.id.favorite_icon);
            downloadIcon = (ImageView) itemView.findViewById(R.id.download_icon);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.save_image);
            actionButtons = (LinearLayout) itemView.findViewById(R.id.qwe);

        }
    }
}
