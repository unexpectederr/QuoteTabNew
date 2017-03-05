package adapters;

import android.app.Activity;
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
import helpers.main.Constants;
import listeners.OnAuthorClickListener;
import listeners.OnQuoteClickListener;
import models.authors.Author;
import models.quotes.Quote;
import models.search.SearchResponse;

/**
 * Created by Spaja on 22-Feb-17.
 */

public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM_AUTHOR = 0;
    private static final int TYPE_HEADER = 1;
    private static final int TYPE_ITEM_QUOTE = 2;

    private SearchResponse mSearchResults;
    private Activity mContext;
    private ArrayList<Object> mDataSet;

    public SearchAdapter(SearchResponse searchResults, Activity context) {

        this.mSearchResults = searchResults;
        this.mContext = context;

        mDataSet = new ArrayList<>();

        if (searchResults.getAuthors().size() > 0)
            mDataSet.add(new Author());

        for (int i = 0; i < searchResults.getAuthors().size(); i++)
            mDataSet.add(searchResults.getAuthors().get(i));

        if (searchResults.getQuotes().size() > 0)
            mDataSet.add(mDataSet.size(), new Quote());

        for (int i = 0; i < searchResults.getQuotes().size(); i++)
            mDataSet.add(searchResults.getQuotes().get(i));

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_ITEM_AUTHOR) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View v = inflater.inflate(R.layout.fav_authors_list_item, parent, false);
            return new ViewHolderAuthor(v);
        } else if (viewType == TYPE_HEADER) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View v = inflater.inflate(R.layout.search_header_item, parent, false);
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

            Author author = (Author) mDataSet.get(position);

            ((ViewHolderAuthor) holder).authorName.setText(author.getAuthorName());

            ((ViewHolderAuthor) holder).authorInfo.setText(author.getProfession() + " - "
                    + author.getQuotesCount() + " quotes");

            Glide.with(((ViewHolderAuthor) holder).authorImage.getContext())
                    .load(Constants.IMAGES_URL + author.getAuthorId() + ".jpg")
                    .dontAnimate()
                    .placeholder(R.drawable.avatar)
                    .error(R.drawable.avatar)
                    .into(((ViewHolderAuthor) holder).authorImage);

            holder.itemView.setOnClickListener(new OnAuthorClickListener(mContext, author.getAuthorId()));

        } else if (holder instanceof ViewHolderQuote) {

            Quote quote = (Quote) mDataSet.get(position);
            ((ViewHolderQuote) holder).quoteText.setText(quote.getQuoteText());

            Glide.with(mContext).load(R.drawable.logo_2).into(((ViewHolderQuote) holder).logo);

            holder.itemView.setOnClickListener(new OnQuoteClickListener(mContext, (Quote) mDataSet.get(position)));

        } else if (holder instanceof ViewHolderHeader) {

            if (position == 0 && mDataSet.get(0) instanceof Author) {
                ((ViewHolderHeader) holder).header.setText("Authors matching query '"
                        + mSearchResults.getQuery() + "'");
            } else {
                ((ViewHolderHeader) holder).header.setText("Quotes matching query '"
                        + mSearchResults.getQuery() + "'");
            }
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    @Override
    public int getItemViewType(int position) {

        if (position == 0) {
            return TYPE_HEADER;
        } else if (position < mSearchResults.getAuthors().size() + 1 && mSearchResults.getAuthors().size() != 0) {
            return TYPE_ITEM_AUTHOR;
        } else if (position == mSearchResults.getAuthors().size() + 1 && mSearchResults.getAuthors().size() != 0) {
            return TYPE_HEADER;
        } else
            return TYPE_ITEM_QUOTE;
    }

    private class ViewHolderAuthor extends RecyclerView.ViewHolder {

        TextView authorName;
        TextView authorInfo;
        CircleImageView authorImage;

        ViewHolderAuthor(View itemView) {
            super(itemView);

            authorName = (TextView) itemView.findViewById(R.id.author_name);
            authorInfo = (TextView) itemView.findViewById(R.id.author_info);
            authorImage = (CircleImageView) itemView.findViewById(R.id.author_image);
        }
    }

    private class ViewHolderQuote extends RecyclerView.ViewHolder {

        TextView quoteText;
        ImageView logo;

        ViewHolderQuote(View itemView) {
            super(itemView);
            quoteText = (TextView) itemView.findViewById(R.id.search_quote_text);
            logo = (ImageView) itemView.findViewById(R.id.logo);
        }
    }

    private class ViewHolderHeader extends RecyclerView.ViewHolder {

        TextView header;

        ViewHolderHeader(View itemView) {
            super(itemView);

            header = (TextView) itemView.findViewById(R.id.title);
        }
    }
}
