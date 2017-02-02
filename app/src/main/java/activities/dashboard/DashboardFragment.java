package activities.dashboard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Random;

import activities.quotetabnew.R;
import de.hdodenhof.circleimageview.CircleImageView;
import helpers.main.AppController;
import helpers.main.AppHelper;
import helpers.main.Constants;
import listeners.OnAuthorClickListener;
import listeners.OnFavoriteQuoteClickListener;
import listeners.OnShareClickListener;
import models.dashboard.TopPhotos;
import models.quotes.Quote;
import models.quotes.QuoteFields;

public class DashboardFragment extends Fragment {

    private static final String ARG_ITEM = "ARG_ITEM";
    private static final String ARG_PAGE = "ARG_PAGE";
    private static final String ARG_FAVORITES = "ARG_FAVORITES";

    private TopPhotos quote;
    private int mPage;
    private ArrayList<Quote> mFavorites;

    public static DashboardFragment getNewInstance(int page, TopPhotos quote, ArrayList<Quote> favoriteQuotes) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_FAVORITES, favoriteQuotes);
        args.putSerializable(ARG_ITEM, quote);
        args.putInt(ARG_PAGE, page);

        DashboardFragment fragment = new DashboardFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        quote = (TopPhotos) getArguments().getSerializable(ARG_ITEM);
        mPage = (int) getArguments().getSerializable(ARG_PAGE);
        mFavorites = (ArrayList<Quote>) getArguments().getSerializable(ARG_FAVORITES);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dashboard_fragment, container, false);

        ImageView cardImage = (android.widget.ImageView) view.findViewById(R.id.backdrop);

        AppController.loadImageIntoView(getContext(), new Random().nextInt(Constants.NUMBER_OF_COVERS), cardImage, true);

        CircleImageView authorImage = (CircleImageView) view.findViewById(R.id.author_image);
        Glide.with(getContext())
                .load(Constants.IMAGES_URL + quote.getSource().getAuthorId() + ".jpg")
                .dontAnimate()
                .placeholder(R.drawable.avatar)
                .error(R.drawable.avatar)
                .into(authorImage);
        authorImage.setOnClickListener(new OnAuthorClickListener(authorImage.getContext(), quote.getSource().getAuthorId()));

        TextView quoteTextView = (TextView) view.findViewById(R.id.quote);
        quoteTextView.setTypeface(AppHelper.getRalewayLight(getContext()));
        quoteTextView.setText(quote.getSource().getQuote());

        TextView author = (TextView) view.findViewById(R.id.author);
        author.setText("- " + quote.getSource().getAuthorName() + "-");
        author.setOnClickListener(new OnAuthorClickListener(author.getContext(), quote.getSource().getAuthorId()));

        ImageView share = (ImageView) view.findViewById(R.id.dashboard_share);
        share.setOnClickListener(new OnShareClickListener(share.getContext(), quote.getSource().getQuote(),
                quote.getSource().getAuthorName()));

        ImageView favorite = (ImageView) view.findViewById(R.id.dashboard_favorite);
        favorite.setImageResource(R.drawable.ic_favorite_empty);

        for (int i = 0; i < mFavorites.size(); i++) {
            if (quote.getSource().getQuoteId().equals(mFavorites.get(i).getQuoteDetails().getQuoteId())) {
                favorite.setImageResource(R.drawable.ic_favorite);
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

        favorite.setOnClickListener(new OnFavoriteQuoteClickListener(favorite.getContext(),
                mFavorites, favorite, quote1, null, false));

        return view;
    }
}
