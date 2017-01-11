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

import com.bumptech.glide.Glide;

import digitalbath.quotetabnew.R;
import helpers.AppController;
import listeners.OnFavoriteClickListener;
import listeners.OnShareClickListener;
import listeners.OnTagClickListener;
import models.authors.Quotes;

/**
 * Created by Spaja on 05-Jan-17.
 */

public class AuthorDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Quotes mDataSet;
    private static final int TYPE_TEXT = 0;
    private static final int TYPE_IMAGE = 1;
    private Context context;


    public AuthorDetailsAdapter(Quotes mDataSet, Context context) {
        this.mDataSet = mDataSet;
        this.context = context;
    }

    private class ViewHolderText extends RecyclerView.ViewHolder {

        TextView quoteText;
        ImageView shareText;
        ImageView favoriteText;
        LinearLayout quoteTags;
        TextView quoteTag;

        ViewHolderText(View itemView) {
            super(itemView);
            quoteText = (TextView) itemView.findViewById(R.id.quoteText);
            shareText = (ImageView) itemView.findViewById(R.id.share_icon_text);
            favoriteText = (ImageView) itemView.findViewById(R.id.ic_favorite_text);
            quoteTags = (LinearLayout) itemView.findViewById(R.id.quote_tags);
        }
    }

    private class ViewHolderImage extends RecyclerView.ViewHolder {

        ImageView quoteImageTypeTwo;
        ImageView shareImage;
        ImageView favoriteImage;

        ViewHolderImage(View itemView) {
            super(itemView);
            quoteImageTypeTwo = (ImageView) itemView.findViewById(R.id.quoteImageTypeTwo);
            shareImage = (ImageView) itemView.findViewById(R.id.share_icon_image);
            favoriteImage = (ImageView) itemView.findViewById(R.id.ic_favorite_image);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_TEXT) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.author_details_recycler_list_item, parent, false);
            return new ViewHolderText(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.author_details_recylcer_list_item_type_two, parent, false);
            return new ViewHolderImage(v);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ViewHolderImage) {

            Glide.with(((ViewHolderImage) holder).quoteImageTypeTwo.getContext())
                    .load(AppController.QUOTES_IMAGES_URL + mDataSet.getQuotes().get(position).getQuoteDetails().getThumbnailUrl())
                    .error(R.drawable.avatar)
                    .into(((ViewHolderImage) holder).quoteImageTypeTwo);

            ((ViewHolderImage) holder).shareImage.setOnClickListener(new OnShareClickListener(context));
            ((ViewHolderImage) holder).favoriteImage.setOnClickListener(new OnFavoriteClickListener(context));

        } else if (holder instanceof ViewHolderText) {

            ((ViewHolderText) holder).quoteText.setText(mDataSet.getQuotes().get(position).getQuoteDetails().getQuoteText());
            ((ViewHolderText) holder).shareText.setOnClickListener(new OnShareClickListener(context));
            ((ViewHolderText) holder).favoriteText.setOnClickListener(new OnFavoriteClickListener(context));
            String[] tags = mDataSet.getQuotes().get(position).getQuoteDetails().getCategories().split(" ");
            for (int i = 0; i < tags.length; i++) {
                if (i < 3) {
                    ((ViewHolderText) holder).quoteTag = new TextView(context);
                    ((ViewHolderText) holder).quoteTag.setBackgroundResource(R.drawable.button_outline);
                    ((ViewHolderText) holder).quoteTag.setText(tags[i]);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    params.setMarginEnd(12);
                    ((ViewHolderText) holder).quoteTag.setLayoutParams(params);
                    ((ViewHolderText) holder).quoteTag.setGravity(Gravity.CENTER);
                    ((ViewHolderText) holder).quoteTag.setTextColor(context.getResources().getColor(R.color.cardview_light_background));
                    ((ViewHolderText) holder).quoteTag.setPadding(12, 0, 12, 5);
                    ((ViewHolderText) holder).quoteTags.addView(((ViewHolderText) holder).quoteTag);
                    ((ViewHolderText) holder).quoteTag.setOnClickListener(new OnTagClickListener(context, ((ViewHolderText) holder).quoteTag.getText().toString()));
                }
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mDataSet.getQuotes().get(position).getQuoteDetails().getThumbnailUrl() != null)
            return TYPE_IMAGE;
        return TYPE_TEXT;
    }

    @Override
    public int getItemCount() {
        return mDataSet.getQuotes().size();
    }
}
