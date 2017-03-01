package activities.dashboard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.firebase.auth.AuthResult;

import java.util.ArrayList;
import java.util.Random;

import digitalbath.quotetab.R;
import de.hdodenhof.circleimageview.CircleImageView;
import helpers.main.AppController;
import helpers.main.AppHelper;
import helpers.main.Constants;
import listeners.OnAuthorClickListener;
import listeners.OnFavoriteAuthorClickListener;
import listeners.OnFavoriteQuoteClickListener;
import listeners.OnShareClickListener;
import models.authors.AuthorDetails;
import models.authors.AuthorFields;
import models.authors.AuthorFieldsFromQuote;
import models.authors.Profession;
import models.dashboard.TopPhotos;
import models.quotes.Quote;
import models.quotes.QuoteFields;

public class DashboardFragment extends Fragment {

    private static final String ARG_QUOTE = "ARG_QUOTE";
    private static final String ARG_PAGE = "ARG_PAGE";
    private static final String ARG_FAVORITE_QUOTES = "ARG_FAVORITE_QUOTES";
    private static final String ARG_FAVORITE_AUTHORS = "ARG_FAVORITE_AUTHORS";
    private static final String ARG_AUTHOR = "ARG_AUTHOR";

    private Quote quote;
    private int mPage;
    private ArrayList<Quote> favoriteQuotes;
    private ArrayList<AuthorDetails> favoriteAuthors;
    private AuthorFieldsFromQuote author;

    public static DashboardFragment  getNewInstance(int page, Quote quote, ArrayList<Quote> favoriteQuotes,
                                                   ArrayList<AuthorDetails> favoriteAuthors, AuthorFieldsFromQuote author) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_FAVORITE_AUTHORS, favoriteAuthors);
        args.putSerializable(ARG_FAVORITE_QUOTES, favoriteQuotes);
        args.putSerializable(ARG_QUOTE, quote);
        args.putSerializable(ARG_AUTHOR, author);
        args.putInt(ARG_PAGE, page);

        DashboardFragment fragment = new DashboardFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        quote = (Quote) getArguments().getSerializable(ARG_QUOTE);
        mPage = (int) getArguments().getSerializable(ARG_PAGE);
        favoriteQuotes = (ArrayList<Quote>) getArguments().getSerializable(ARG_FAVORITE_QUOTES);
        favoriteAuthors = (ArrayList<AuthorDetails>) getArguments().getSerializable(ARG_FAVORITE_AUTHORS);
        author = (AuthorFieldsFromQuote) getArguments().getSerializable(ARG_AUTHOR);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        AuthorDetails authorDetails = new AuthorDetails();
        AuthorFields fields = new AuthorFields();

        ArrayList<String> name = new ArrayList<>();
        name.add(author.getAuthorName());

        ArrayList<String> authorId = new ArrayList<>();
        authorId.add(author.getAuthorId());

        ArrayList<String> imageUrl = new ArrayList<>();
        imageUrl.add(author.getAuthorImageUrl());

        ArrayList<Integer> quotesCount = new ArrayList<>();
        quotesCount.add(author.getQuotesCount());

        ArrayList<String> professionName = new ArrayList<>();
        professionName.add(author.getProfession().getProfessionName());

        fields.setName(name);
        fields.setAuthorId(authorId);
        fields.setImageUrl(imageUrl);
        fields.setQuotesCount(quotesCount);
        fields.setProfessionName(professionName);
        authorDetails.setId(author.getAuthorId());

        authorDetails.setAuthorFields(fields);

        View view = inflater.inflate(R.layout.dashboard_fragment, container, false);

        ImageView cardImage = (android.widget.ImageView) view.findViewById(R.id.backdrop);

        AppController.loadImageIntoView(getContext(), quote.getImageId(), cardImage, true, true);

        CircleImageView authorImage = (CircleImageView) view.findViewById(R.id.author_image);
        Glide.with(getContext())
                .load(Constants.IMAGES_URL + quote.getQuoteDetails().getAuthorId() + ".jpg")
                .dontAnimate()
                .placeholder(R.drawable.avatar)
                .error(R.drawable.avatar)
                .into(authorImage);

        authorImage.setOnClickListener(new OnAuthorClickListener(authorImage.getContext(),
                quote.getQuoteDetails().getAuthorId()));

        TextView quoteTextView = (TextView) view.findViewById(R.id.quote_text);
        quoteTextView.setTypeface(AppHelper.getRalewayLight(getContext()));
        quoteTextView.setText(quote.getQuoteDetails().getQuoteText());

        TextView authorTextView = (TextView) view.findViewById(R.id.author);
        authorTextView.setText("- " + quote.getQuoteDetails().getAuthorName() + "-");
        authorTextView.setOnClickListener(new OnAuthorClickListener(authorTextView.getContext(),
                quote.getQuoteDetails().getAuthorId()));

        ImageView share = (ImageView) view.findViewById(R.id.dashboard_share);

        share.setOnClickListener(new OnShareClickListener(getActivity(),
                quote.getQuoteDetails().getQuoteText(), quote.getQuoteDetails().getAuthorName(),
                Constants.COVER_IMAGES_URL + quote.getImageId() + ".jpg",
                quoteTextView));

        ImageView favoriteAuthor = (ImageView) view.findViewById(R.id.dashboard_author);

        for (int i = 0; i < favoriteAuthors.size(); i++) {
            if (authorDetails.getAuthorFields().getAuthorId().equals(favoriteAuthors.get(i).getAuthorFields().getAuthorId())) {
                favoriteAuthor.setImageResource(R.drawable.ic_author);
                authorDetails.setFavorite(true);
            } else {
                favoriteAuthor.setImageResource(R.drawable.ic_author_empty);
                authorDetails.setFavorite(false);
            }
        }

        favoriteAuthor.setOnClickListener(new OnFavoriteAuthorClickListener(favoriteAuthor.getContext(),
                authorDetails, favoriteAuthors, favoriteAuthor, null, false, null, null));

        ImageView favoriteQuote = (ImageView) view.findViewById(R.id.dashboard_favorite);

        for (int i = 0; i < favoriteQuotes.size(); i++) {
            if (quote.getQuoteDetails().getQuoteId().equals(favoriteQuotes.get(i)
                    .getQuoteDetails().getQuoteId())) {
                favoriteQuote.setImageResource(R.drawable.ic_favorite);
                quote.setFavorite(true);
            } else {
                favoriteQuote.setImageResource(R.drawable.ic_favorite_empty);
                quote.setFavorite(false);
            }
        }

        favoriteQuote.setOnClickListener(new OnFavoriteQuoteClickListener(favoriteQuote.getContext(),
                favoriteQuotes, favoriteQuote, quote, null, false, null, null));

        return view;
    }
}
