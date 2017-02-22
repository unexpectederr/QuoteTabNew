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

import java.util.ArrayList;
import java.util.Random;

import digitalbath.quotetab.R;
import de.hdodenhof.circleimageview.CircleImageView;
import helpers.main.AppController;
import helpers.main.AppHelper;
import helpers.main.Constants;
import listeners.OnAuthorClickListener;
import listeners.OnFavoriteQuoteClickListener;
import listeners.OnShareClickListener;
import models.authors.AuthorDetails;
import models.dashboard.TopPhotos;
import models.quotes.Quote;
import models.quotes.QuoteFields;

public class DashboardFragment extends Fragment {

    private static final String ARG_QUOTE = "ARG_QUOTE";
    private static final String ARG_PAGE = "ARG_PAGE";
    private static final String ARG_FAVORITE_QUOTES = "ARG_FAVORITE_QUOTES";
    private static final String ARG_FAVORITE_AUTHORS = "ARG_FAVORITE_AUTHORS";
    private static final String ARG_AUTHOR = "ARG_AUTHOR";


    private TopPhotos quote;
    private int mPage;
    private ArrayList<Quote> favoriteQuotes;
    private ArrayList<AuthorDetails> favoriteAuthors;

    public static DashboardFragment getNewInstance(int page, TopPhotos quote, ArrayList<Quote> favoriteQuotes,
                                                   ArrayList<AuthorDetails> favoriteAuthors) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_FAVORITE_AUTHORS, favoriteAuthors);
        args.putSerializable(ARG_FAVORITE_QUOTES, favoriteQuotes);
        args.putSerializable(ARG_QUOTE, quote);
        args.putInt(ARG_PAGE, page);

        DashboardFragment fragment = new DashboardFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        quote = (TopPhotos) getArguments().getSerializable(ARG_QUOTE);
        mPage = (int) getArguments().getSerializable(ARG_PAGE);
        favoriteQuotes = (ArrayList<Quote>) getArguments().getSerializable(ARG_FAVORITE_QUOTES);
        favoriteAuthors = (ArrayList<AuthorDetails>) getArguments().getSerializable(ARG_FAVORITE_AUTHORS);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dashboard_fragment, container, false);

        ImageView cardImage = (android.widget.ImageView) view.findViewById(R.id.backdrop);

        AppController.loadImageIntoView(getContext(), new Random()
                .nextInt(Constants.NUMBER_OF_COVERS), cardImage, true, true);

        CircleImageView authorImage = (CircleImageView) view.findViewById(R.id.author_image);
        Glide.with(getContext())
                .load(Constants.IMAGES_URL + quote.getSource().getAuthorId() + ".jpg")
                .dontAnimate()
                .placeholder(R.drawable.avatar)
                .error(R.drawable.avatar)
                .into(authorImage);
        authorImage.setOnClickListener(new OnAuthorClickListener(authorImage.getContext(), quote.getSource().getAuthorId()));

        TextView quoteTextView = (TextView) view.findViewById(R.id.quote_text);
        quoteTextView.setTypeface(AppHelper.getRalewayLight(getContext()));
        quoteTextView.setText(quote.getSource().getQuote());

        TextView author = (TextView) view.findViewById(R.id.author);
        author.setText("- " + quote.getSource().getAuthorName() + "-");
        author.setOnClickListener(new OnAuthorClickListener(author.getContext(), quote.getSource().getAuthorId()));

        ImageView share = (ImageView) view.findViewById(R.id.dashboard_share);
        RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.main_layout);
        share.setOnClickListener(new OnShareClickListener(share.getContext(), quote.getSource().getQuote(),
                quote.getSource().getAuthorName(), relativeLayout));

        ImageView favoriteQuote = (ImageView) view.findViewById(R.id.dashboard_favorite);
        favoriteQuote.setImageResource(R.drawable.ic_favorite_empty);

        ImageView favoriteAuthor = (ImageView) view.findViewById(R.id.dashboard_author);
        favoriteAuthor.setImageResource(R.drawable.ic_author_empty);

//        for (int i = 0; i < favoriteAuthors.size(); i++) {
//            if (quote.getSource().getAuthorId().equals(favoriteAuthors.get(i).getId())){
//                favoriteAuthor.setImageResource(R.drawable.ic_author);
//            }
//        }

        for (int i = 0; i < favoriteQuotes.size(); i++) {
            if (quote.getSource().getQuoteId().equals(favoriteQuotes.get(i).getQuoteDetails().getQuoteId())) {
                favoriteQuote.setImageResource(R.drawable.ic_favorite);
                quote.setFavorite(true);
            } else {
                quote.setFavorite(false);
            }
        }

        QuoteFields fields = new QuoteFields();
        fields.setAuthorName(quote.getSource().getAuthorName());
        fields.setAuthorId(quote.getSource().getAuthorId());
        fields.setQuoteText(quote.getSource().getQuote());
        fields.setQuoteId(quote.getSource().getQuoteId());

        Quote quote1 = new Quote(quote.isFavorite(), new Random().nextInt(Constants.NUMBER_OF_COVERS), fields);

        favoriteQuote.setOnClickListener(new OnFavoriteQuoteClickListener(favoriteQuote.getContext(),
                favoriteQuotes, favoriteQuote, quote1, null, false));

        return view;
    }
}
