package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import digitalbath.quotetab.R;
import models.authors.AuthorDetails;
import models.quotes.Quote;
import models.search.SearchResponse;

/**
 * Created by Spaja on 22-Feb-17.
 */

public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM_AUTHOR = 0;
    private static final int TYPE_HEADER = 1;
    private static final int TYPE_ITEM_QUOTE = 2;
    private SearchResponse mDataSet;
    private Context context;
    private int responseSize;
    private ArrayList<Object> allElements;

    public SearchAdapter(SearchResponse mDataSet, Context context) {

        this.mDataSet = mDataSet;
        this.context = context;
        responseSize = mDataSet.getAuthors().size() + mDataSet.getQuotes().size();
        allElements = new ArrayList<>();
        allElements.add(new AuthorDetails()); //prva pozicija u list za header
        for (int i = 0; i < mDataSet.getAuthors().size(); i++) {
            allElements.add(mDataSet.getAuthors().get(i));
        }

        allElements.add(mDataSet.getAuthors().size() + 1, new Quote()); //prva pozicija quota u list za header
        for (int i = 0; i < mDataSet.getQuotes().size(); i++) {
            allElements.add(mDataSet.getQuotes().get(i));
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM_AUTHOR) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View v = inflater.inflate(R.layout.fav_authors_list_item, parent, false);
            return new ViewHolderAuthor(v);
        } else if (viewType == TYPE_HEADER) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View v = inflater.inflate(R.layout.authors_list_header, parent, false);
            return new ViewHolderHeader(v);
        } else {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View v = inflater.inflate(R.layout.search_quote_list_item, parent, false);
            return new ViewHolderQuote(v);
        }
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolderAuthor) {
            AuthorDetails author = (AuthorDetails) allElements.get(position);
            ((ViewHolderAuthor) holder).authorName.setText(author.getAuthorFields().getName());
            Glide.with(context).load(author.getAuthorFields().getImageUrl())
                    .asBitmap().dontAnimate().into(((ViewHolderAuthor) holder).authorImage);
        } else if (holder instanceof ViewHolderQuote) {
            Quote quote = (Quote) allElements.get(position);
            ((ViewHolderQuote) holder).quoteText.setText(quote.getQuoteDetails().getQuoteText());
        } else if (holder instanceof ViewHolderHeader) {
            if (position == 0) {
                ((ViewHolderHeader) holder).header.setText("Authors");
            } else {
                ((ViewHolderHeader) holder).header.setText("Quotes");
            }
        }
    }

    @Override
    public int getItemCount() {
        return responseSize + 2; //+2 mjesta za header
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        } else if (position < mDataSet.getAuthors().size() + 1 && mDataSet.getAuthors().size() != 0) {
            return TYPE_ITEM_AUTHOR;
        } else if (position == mDataSet.getAuthors().size() + 1 && mDataSet.getAuthors().size() != 0) {
            return TYPE_HEADER;
        } else
            return TYPE_ITEM_QUOTE;
    }

    private class ViewHolderAuthor extends RecyclerView.ViewHolder {

        TextView authorName;
        TextView authorInfo;
        CircleImageView authorImage;
        ImageView favoriteIcon;

        ViewHolderAuthor(View itemView) {
            super(itemView);

            authorName = (TextView) itemView.findViewById(R.id.author_name);
            authorInfo = (TextView) itemView.findViewById(R.id.author_info);
            authorImage = (CircleImageView) itemView.findViewById(R.id.author_image);
            favoriteIcon = (ImageView) itemView.findViewById(R.id.author_favorite);
        }
    }

    private class ViewHolderQuote extends RecyclerView.ViewHolder {

        TextView quoteText;

        ViewHolderQuote(View itemView) {
            super(itemView);
            quoteText = (TextView) itemView.findViewById(R.id.search_quote_text);
        }
    }

    private class ViewHolderHeader extends RecyclerView.ViewHolder {

        TextView header;

        ViewHolderHeader(View itemView) {
            super(itemView);

            header = (TextView) itemView.findViewById(R.id.header);
        }
    }
}
