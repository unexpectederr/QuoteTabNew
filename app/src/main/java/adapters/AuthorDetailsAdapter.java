package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
        TextView quoteTag, quoteTag2, quoteTag3;

        ViewHolderText(View itemView) {
            super(itemView);
            quoteText = (TextView) itemView.findViewById(R.id.quoteText);
            shareText = (ImageView) itemView.findViewById(R.id.share_icon_text);
            favoriteText = (ImageView) itemView.findViewById(R.id.ic_favorite_text);
            quoteTag = (TextView) itemView.findViewById(R.id.quoteTag);
            quoteTag2 = (TextView) itemView.findViewById(R.id.quoteTag2);
            quoteTag3 = (TextView) itemView.findViewById(R.id.quoteTag3);
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
            ((ViewHolderText) holder).quoteTag.setText(tags[0]);
            ((ViewHolderText) holder).quoteTag.setOnClickListener(
                    new OnTagClickListener(context, ((ViewHolderText) holder).quoteTag.getText().toString()));
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

//    //Class for handling quote share clicks
//    private class OnShareClickListener implements View.OnClickListener {
//
//        @Override
//        public void onClick(View v) {
//            Toast.makeText(context, "Share this quote", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    //Class for handling quote favorite clicks
//    private class OnFavoriteClickListener implements View.OnClickListener {
//
//        @Override
//        public void onClick(View v) {
//            Toast.makeText(context, "Favorite this quote", Toast.LENGTH_SHORT).show();
//        }
//    }
}
