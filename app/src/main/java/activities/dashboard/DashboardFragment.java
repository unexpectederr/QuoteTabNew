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

import de.hdodenhof.circleimageview.CircleImageView;
import activities.quotetabnew.R;
import helpers.main.AppController;
import helpers.main.AppHelper;
import helpers.main.Constants;
import helpers.other.ReadAndWriteToFile;
import listeners.OnAuthorClickListener;
import listeners.OnFavoriteQuoteClickListener;
import listeners.OnShareClickListener;
import models.dashboard.DashboardItem;
import models.quotes.Quote;

public class DashboardFragment extends Fragment {

    private static final String ARG_ITEM = "ARG_ITEM";
    private static final String ARG_PAGE = "ARG_PAGE";

    private DashboardItem mItem;
    private int mPage;

    public static DashboardFragment getNewInstance(int page, DashboardItem item) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_ITEM, item);
        args.putInt(ARG_PAGE, page);

        DashboardFragment fragment = new DashboardFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mItem = (DashboardItem) getArguments().getSerializable(ARG_ITEM);
        mPage = (int) getArguments().getSerializable(ARG_PAGE);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dashboard_fragment, container, false);

        ImageView cardImage = (android.widget.ImageView) view.findViewById(R.id.backdrop);

        AppController.loadImageIntoView(getContext(), mItem.getDashItemId(), cardImage, true);

        CircleImageView authorImage = (CircleImageView) view.findViewById(R.id.author_image);
        Glide.with(getContext())
                .load(Constants.IMAGES_URL + mItem.getAuthorId() + ".jpg")
                .dontAnimate()
                .placeholder(R.drawable.avatar)
                .error(R.drawable.avatar)
                .into(authorImage);
        authorImage.setOnClickListener(new OnAuthorClickListener(authorImage.getContext(), mItem.getAuthorId()));

        TextView quote = (TextView) view.findViewById(R.id.quote);
        quote.setTypeface(AppHelper.getRalewayLigt(getContext()));
        quote.setText(mItem.getQuote());

        TextView author = (TextView) view.findViewById(R.id.author);
        author.setText("- " + mItem.getAuthor() + "-");
        author.setOnClickListener(new OnAuthorClickListener(author.getContext(), mItem.getAuthorId()));

        ImageView share = (ImageView) view.findViewById(R.id.dashboard_share);
        share.setOnClickListener(new OnShareClickListener(share.getContext(), mItem.getQuote(), mItem.getAuthor()));

        //Potrebno proslijediti quote u fragment ili na neki slican nacin proslijediti parametre u OnFavoriteQuoteClickListener
        ImageView favorite = (ImageView) view.findViewById(R.id.dashboard_favorite);


//        favorite.setOnClickListener(new OnFavoriteQuoteClickListener(favorite.getContext(),
//                ReadAndWriteToFile.getFavoriteQuotes(favorite.getContext()), favorite, mItem.getQuoteId(), null, false));

        return view;
    }
}
