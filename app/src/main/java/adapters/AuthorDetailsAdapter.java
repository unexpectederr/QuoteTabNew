package adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import digitalbath.quotetabnew.R;
import helpers.AppController;
import models.authors.Quotes;

/**
 * Created by Spaja on 05-Jan-17.
 */

public class AuthorDetailsAdapter extends RecyclerView.Adapter<AuthorDetailsAdapter.ViewHolder> {

    private Quotes mDataSet;

    public AuthorDetailsAdapter(Quotes mDataSet) {
        this.mDataSet = mDataSet;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView quoteText;
        ImageView quoteImage;

        public ViewHolder(View itemView) {
            super(itemView);
            quoteText = (TextView) itemView.findViewById(R.id.quoteText);
            quoteImage = (ImageView) itemView.findViewById(R.id.quoteImage);
        }
    }

    @Override
    public AuthorDetailsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.author_details_recycler_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AuthorDetailsAdapter.ViewHolder holder, int position) {
        holder.quoteText.setText(mDataSet.getQuotes().get(position).getQuoteDetails().getQuoteText());

        //problem sa linkom za sliku quota
        Glide.with(holder.quoteImage.getContext())
                .load(AppController.QUOTES_IMAGES_URL + mDataSet.getQuotes().get(position)
                        .getQuoteDetails().getThumbnailUrl()).error(R.drawable.avatar).into(holder.quoteImage);
    }

    @Override
    public int getItemCount() {
        return mDataSet.getQuotes().size();
    }
}
