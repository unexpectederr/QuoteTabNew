package activities.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.io.Serializable;
import java.util.ArrayList;
import activities.quotes.QuotesByAuthor;
import adapters.DashboardPagerAdapter;
import de.hdodenhof.circleimageview.CircleImageView;
import digitalbath.quotetab.R;
import helpers.main.AppController;
import helpers.main.AppHelper;
import helpers.main.Constants;
import listeners.OnFavoriteAuthorClickListener;
import listeners.OnFavoriteQuoteClickListener;
import listeners.OnShareClickListener;
import models.authors.Author;
import models.quotes.Quote;

public class DashboardFragment extends Fragment implements Serializable {

    private static final String ARG_QUOTE = "ARG_QUOTE";
    private static final String ARG_PAGE = "ARG_PAGE";
    private static final String ARG_FAVORITE_QUOTES = "ARG_FAVORITE_QUOTES";
    private static final String ARG_FAVORITE_AUTHORS = "ARG_FAVORITE_AUTHORS";
    private static final String ARG_PAGER_ADAPTER = "ARG_PAGER_ADAPTER";

    private Quote mQuote;

    private ArrayList<Quote> mFavoriteQuotes;
    private ArrayList<Author> mFavoriteAuthors;
    private ImageView mFavoriteAuthor, mFavoriteQuote;
    private DashboardPagerAdapter adapter;

    public static DashboardFragment getNewInstance(int page, Quote quote,
                                                   ArrayList<Quote> favoriteQuotes,
                                                   ArrayList<Author> favoriteAuthors, DashboardPagerAdapter adapter) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_FAVORITE_AUTHORS, favoriteAuthors);
        args.putSerializable(ARG_FAVORITE_QUOTES, favoriteQuotes);
        args.putSerializable(ARG_QUOTE, quote);
        args.putInt(ARG_PAGE, page);
//        args.putSerializable(ARG_PAGER_ADAPTER, adapter);

        DashboardFragment fragment = new DashboardFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mQuote = (Quote) getArguments().getSerializable(ARG_QUOTE);
        mFavoriteQuotes = (ArrayList<Quote>) getArguments().getSerializable(ARG_FAVORITE_QUOTES);
        mFavoriteAuthors = (ArrayList<Author>) getArguments().getSerializable(ARG_FAVORITE_AUTHORS);
//        adapter = (DashboardPagerAdapter) getArguments().getSerializable(ARG_PAGER_ADAPTER);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dashboard_fragment, container, false);

        ImageView cardImage = (ImageView) view.findViewById(R.id.backdrop);

        AppController.loadImageIntoView(getContext(), mQuote.getImageId(), cardImage, true, true);

        final CircleImageView authorImage = (CircleImageView) view.findViewById(R.id.author_image);

        Glide.with(getContext())
                .load(Constants.IMAGES_URL + mQuote.getAuthor().getAuthorId() + ".jpg")
                .dontAnimate()
                .placeholder(R.drawable.avatar)
                .error(R.drawable.avatar)
                .into(authorImage);

        authorImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(authorImage.getContext(), QuotesByAuthor.class);
                i.putExtra(Constants.AUTHOR_ID, mQuote.getAuthor().getAuthorId());
                getActivity().startActivityForResult(i, 1);
            }
        });

        TextView quoteTextView = (TextView) view.findViewById(R.id.quote_text);
        quoteTextView.setTypeface(AppHelper.getRalewayLight(getContext()));
        quoteTextView.setText(mQuote.getQuoteText());

        final TextView authorTextView = (TextView) view.findViewById(R.id.author);
        authorTextView.setText("- " + mQuote.getAuthor().getAuthorName() + " -");
        authorTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(authorTextView.getContext(), QuotesByAuthor.class);
                i.putExtra(Constants.AUTHOR_ID, mQuote.getAuthor().getAuthorId());
                getActivity().startActivityForResult(i, 1);
            }
        });

        ImageView share = (ImageView) view.findViewById(R.id.dashboard_share);

        share.setOnClickListener(new OnShareClickListener(getActivity(),
                mQuote.getQuoteText(), mQuote.getAuthor().getAuthorName(),
                Constants.COVER_IMAGES_URL + mQuote.getImageId() + ".jpg",
                quoteTextView));

        mFavoriteAuthor = (ImageView) view.findViewById(R.id.dashboard_author);

        for (int i = 0; i < mFavoriteAuthors.size(); i++) {
            if (mQuote.getAuthor().getAuthorId()
                    .equals(mFavoriteAuthors.get(i).getAuthorId())) {
                mQuote.getAuthor().setFavorite(true);
            }
        }

        mQuote.getAuthor().setFavorite(mQuote.getAuthor().isFavorite());

        mFavoriteAuthor.setImageResource(mQuote.getAuthor().isFavorite() ?
                R.drawable.ic_author : R.drawable.ic_author_empty);

        mFavoriteAuthor.setOnClickListener(new OnFavoriteAuthorClickListener(getActivity(),
                mQuote.getAuthor(), mFavoriteAuthors, mFavoriteAuthor, null, false, null, null));

        mFavoriteQuote = (ImageView) view.findViewById(R.id.dashboard_favorite);

        for (int i = 0; i < mFavoriteQuotes.size(); i++) {
            if (mQuote.getQuoteId().equals(mFavoriteQuotes.get(i).getQuoteId())) {
                mQuote.setFavorite(true);
            }
        }

        mFavoriteQuote.setImageResource(mQuote.isFavorite() ?
                R.drawable.ic_favorite : R.drawable.ic_favorite_empty);


        mFavoriteQuote.setOnClickListener(new OnFavoriteQuoteClickListener(mFavoriteQuote.getContext(),
                mFavoriteQuotes, mFavoriteQuote, mQuote, null, false, null, null));

        return view;
    }

    public void refreshData(ArrayList<Author> favoriteAuthors, ArrayList<Quote> favoriteQuotes) {

        mQuote.setFavorite(false);
        mFavoriteAuthors = favoriteAuthors;

        for (int i = 0; i < favoriteQuotes.size(); i++) {
            if (mQuote.getQuoteId()
                    .equals(favoriteQuotes.get(i).getQuoteId())) {
                mQuote.setFavorite(true);
            }
        }

        mFavoriteQuote.setImageResource(mQuote.isFavorite() ?
                R.drawable.ic_favorite : R.drawable.ic_favorite_empty);

        mQuote.getAuthor().setFavorite(false);
        mFavoriteQuotes = favoriteQuotes;

        for (int i = 0; i < favoriteAuthors.size(); i++) {
            if (mQuote.getAuthor().getAuthorId()
                    .equals(favoriteAuthors.get(i).getAuthorId())) {
                mQuote.getAuthor().setFavorite(true);
            }
        }

        mFavoriteAuthor.setImageResource(mQuote.getAuthor().isFavorite() ?
                R.drawable.ic_author : R.drawable.ic_author_empty);
    }
}
