package adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.zakariya.stickyheaders.SectioningAdapter;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import activities.quotes.QuotesByAuthor;
import activities.quotetabnew.R;
import helpers.main.Constants;
import listeners.OnFavoriteAuthorClickListener;
import models.authors.AuthorDetails;
import models.authors.AuthorFields;
import models.authors.PopularAuthors;

/**
 * Created by Spaja on 26-Dec-16.
 */

public class PopularAuthorsAdapter extends SectioningAdapter {

    private PopularAuthors mDataSet;
    private int mNumberOfSections;
    private Context mContext;
    private int mLastPosition = -1;
    private ArrayList<AuthorDetails> mFavoriteAuthors;

    public PopularAuthorsAdapter(PopularAuthors dataSet, Context context,
                                 ArrayList<AuthorDetails> favoriteAuthors) {

        this.mContext = context;
        this.mDataSet = dataSet;
        this.mFavoriteAuthors = favoriteAuthors;

        mNumberOfSections = mDataSet.getAuthorGroup().size();

    }

    private class ItemViewHolder extends SectioningAdapter.ItemViewHolder {

        TextView authorName;
        TextView authorInfo;
        CircleImageView authorImage;
        ImageView favoriteIcon;

        ItemViewHolder(View itemView) {
            super(itemView);
            authorName = (TextView) itemView.findViewById(R.id.author_name);
            authorInfo = (TextView) itemView.findViewById(R.id.author_info);
            authorImage = (CircleImageView) itemView.findViewById(R.id.author_image);
            favoriteIcon = (ImageView) itemView.findViewById(R.id.author_favorite);
        }
    }

    private class HeaderViewHolder extends SectioningAdapter.HeaderViewHolder {

        TextView header;

        HeaderViewHolder(View itemView) {
            super(itemView);
            header = (TextView) itemView.findViewById(R.id.header);
        }
    }

    @Override
    public int getNumberOfSections() {
        return mNumberOfSections;
    }

    @Override
    public int getNumberOfItemsInSection(int sectionIndex) {
        return mDataSet.getAuthorGroup().get(sectionIndex).getAuthors().size();
    }

    @Override
    public boolean doesSectionHaveHeader(int sectionIndex) {
        return true;
    }

    @Override
    public boolean doesSectionHaveFooter(int sectionIndex) {
        return false;
    }

    @Override
    public ItemViewHolder onCreateItemViewHolder(ViewGroup parent, int itemType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.authors_recyclerview_list_item, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public HeaderViewHolder onCreateHeaderViewHolder(ViewGroup parent, int headerType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.authors_recyclerview_list_header, parent, false);
        return new HeaderViewHolder(v);
    }

    @Override
    public void onBindItemViewHolder(SectioningAdapter.ItemViewHolder viewHolder, int sectionIndex,
                                     int itemIndex, int itemType) {

        ItemViewHolder ivh = (ItemViewHolder) viewHolder;

        AuthorFields authorFields = mDataSet.getAuthorGroup().get(sectionIndex).getAuthors()
                .get(itemIndex).getAuthorFields();

        if (mDataSet.getAuthorGroup().get(sectionIndex).getAuthors()
                .get(itemIndex).isFavorite()) {

            ivh.favoriteIcon.setImageResource(R.drawable.ic_author);

        } else if (mFavoriteAuthors.size() != 0) {

            for (int i = 0; i < mFavoriteAuthors.size(); i++) {

                if (mDataSet.getAuthorGroup().get(sectionIndex).getAuthors().get(itemIndex).getId()
                        .equals(mFavoriteAuthors.get(i).getId())) {

                    mDataSet.getAuthorGroup().get(sectionIndex).getAuthors()
                            .get(itemIndex).setFavorite(true);
                    ivh.favoriteIcon.setImageResource(R.drawable.ic_author);

                    break;
                }
            }
        }

        if (!mDataSet.getAuthorGroup().get(sectionIndex).getAuthors()
                .get(itemIndex).isFavorite())
            ivh.favoriteIcon.setImageResource(R.drawable.ic_author_empty);

        ivh.authorName.setText(authorFields.getName());

        ivh.authorInfo.setText(authorFields.getProfessionName() + " - "
                + authorFields.getQuotesCount() + " quotes");

        Glide.with(((ItemViewHolder) viewHolder).authorImage.getContext())
                .load(Constants.IMAGES_URL + authorFields.getImageUrl()).dontAnimate()
                .placeholder(R.drawable.avatar)
                .error(R.drawable.avatar)
                .into(((ItemViewHolder) viewHolder).authorImage);

        ivh.favoriteIcon.setOnClickListener(new OnFavoriteAuthorClickListener(mContext,
                mDataSet.getAuthorGroup().get(sectionIndex).getAuthors().get(itemIndex),
                mFavoriteAuthors, ivh.favoriteIcon, null, false));

        ivh.itemView.setOnClickListener(new OnAuthorClickListener(mDataSet.getAuthorGroup()
                .get(sectionIndex).getAuthors().get(itemIndex).getId()));

        setAnimation(ivh.itemView, viewHolder.getAdapterPosition());
    }

    private void setAnimation(View viewToAnimate, int position) {

        if (position > mLastPosition) {
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.abc_slide_in_bottom);
            viewToAnimate.startAnimation(animation);
            mLastPosition = position;
        }
    }

    @Override
    public void onBindHeaderViewHolder(SectioningAdapter.HeaderViewHolder viewHolder, int sectionIndex,
                                       int headerType) {

        HeaderViewHolder hvh = (HeaderViewHolder) viewHolder;
        hvh.itemView.setBackgroundColor(0x55ffffff);
        hvh.header.setText(mDataSet.getAuthorGroup().get(sectionIndex).getReferences().getLetter());
    }

    private class OnAuthorClickListener implements View.OnClickListener {

        String authorID;

        OnAuthorClickListener(String authorID) {
            this.authorID = authorID;
        }

        @Override
        public void onClick(View v) {
            Intent i = new Intent(mContext, QuotesByAuthor.class);
            i.putExtra(Constants.AUTHOR_ID, authorID);
            mContext.startActivity(i);
        }
    }
}
