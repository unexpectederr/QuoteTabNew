package adapters;

import android.content.Context;
import android.media.Image;
import android.provider.ContactsContract;
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

import com.bumptech.glide.Glide;

import java.util.Random;

import digitalbath.quotetabnew.R;
import helpers.AppHelper;
import helpers.Constants;
import listeners.OnFavoriteClickListener;
import listeners.OnShareClickListener;
import listeners.OnTagClickListener;
import models.authors.Quotes;

/**
 * Created by Spaja on 05-Jan-17.
 */

public class AuthorDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Quotes mDataSet;
    private Context context;
    private int lastPosition = -1;

    public AuthorDetailsAdapter(Quotes mDataSet, Context context) {
        this.mDataSet = mDataSet;
        this.context = context;
        setHasStableIds(true);
    }

    private class ViewHolderCard extends RecyclerView.ViewHolder {

        TextView quoteText;
        ImageView cardImage;
        ImageView shareIcon;
        ImageView favoriteIcon;
        LinearLayout quoteTags;
        ImageView quotesLeft;
        ImageView quotesRight;

        ViewHolderCard(View itemView) {
            super(itemView);
            quoteText = (TextView) itemView.findViewById(R.id.quoteText);
            cardImage = (ImageView) itemView.findViewById(R.id.card_image);
            shareIcon = (ImageView) itemView.findViewById(R.id.share_icon);
            favoriteIcon = (ImageView) itemView.findViewById(R.id.favorite_icon);
            quoteTags = (LinearLayout) itemView.findViewById(R.id.quote_tags);
            quotesLeft = (ImageView) itemView.findViewById(R.id.quotes_left);
            quotesRight = (ImageView) itemView.findViewById(R.id.quotes_right);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.author_details_recycler_list_item, parent, false);

        return new ViewHolderCard(v);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        Glide.with(((ViewHolderCard) holder).cardImage.getContext())
                .load(Constants.COVER_IMAGES_URL + mDataSet.getQuotes().get(position).getQuoteId() + ".jpg")
                .error(R.drawable.avatar)
                .into(((ViewHolderCard) holder).cardImage);

       /* Glide.with(((ViewHolderCard) holder).quotesLeft.getContext())
                .load(R.drawable.quotation_marks_left)
                .into(((ViewHolderCard) holder).quotesLeft);

        Glide.with(((ViewHolderCard) holder).quotesRight.getContext())
                .load(R.drawable.quotation_marks_right)
                .into(((ViewHolderCard) holder).quotesRight);*/

        ((ViewHolderCard) holder).quoteText.setText(mDataSet.getQuotes().get(position).getQuoteDetails().getQuoteText());
        ((ViewHolderCard) holder).quoteText.setTypeface(AppHelper.getRalewayLigt(((ViewHolderCard) holder).cardImage.getContext()));
        ((ViewHolderCard) holder).shareIcon.setOnClickListener(new OnShareClickListener(context));
        ((ViewHolderCard) holder).favoriteIcon.setOnClickListener(new OnFavoriteClickListener(context));

        String[] tags = mDataSet.getQuotes().get(position).getQuoteDetails().getCategories().split(" ");

        ((ViewHolderCard) holder).quoteTags.removeAllViews();

        for (int i = 0; i < tags.length; i++) {

            if (i < Constants.MAX_NUMBER_OF_QUOTES) {

                TextView quoteTag = new TextView(context);
                quoteTag.setBackgroundResource(R.drawable.button_outline);
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

                ((ViewHolderCard) holder).quoteTags.addView(quoteTag);
            }
        }

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
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mDataSet.getQuotes().size();
    }
}
