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

import activities.authors.Authors;
import digitalbath.quotetab.R;
import de.hdodenhof.circleimageview.CircleImageView;
import helpers.main.Constants;
import listeners.OnAuthorClickListener;
import listeners.OnFavoriteAuthorClickListener;
import models.authors.Author;
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
    private ArrayList<Author> mFavoriteAuthors;
    private static final int TYPE_ITEM_LAST = 4;

    public PopularAuthorsAdapter(PopularAuthors dataSet, Context context,
                                 ArrayList<Author> favoriteAuthors) {

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
        TextView text;

        ItemViewHolder(View itemView) {
            super(itemView);
            authorName = (TextView) itemView.findViewById(R.id.author_name);
            authorInfo = (TextView) itemView.findViewById(R.id.author_info);
            authorImage = (CircleImageView) itemView.findViewById(R.id.author_image);
            favoriteIcon = (ImageView) itemView.findViewById(R.id.author_favorite);
            text = (TextView) itemView.findViewById(R.id.text_load);
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
    public int getSectionItemUserType(int sectionIndex, int itemIndex) {

        if (mDataSet.getAuthorGroup().get(sectionIndex)
                .getAuthors().get(itemIndex).isLast())
            return TYPE_ITEM_LAST;
        else
            return TYPE_ITEM;
    }

    @Override
    public ItemViewHolder onCreateItemViewHolder(ViewGroup parent, int itemType) {

        if (itemType == TYPE_ITEM) {

            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.authors_list_item, parent, false);
            return new ItemViewHolder(v);

        } else {

            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.authors_last_item, parent, false);
            return new ItemViewHolder(v);
        }
    }

    @Override
    public HeaderViewHolder onCreateHeaderViewHolder(ViewGroup parent, int headerType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.authors_list_header, parent, false);
        return new HeaderViewHolder(v);
    }

    @Override
    public void onBindItemViewHolder(SectioningAdapter.ItemViewHolder viewHolder, int sectionIndex,
                                     int itemIndex, int itemType) {

        ItemViewHolder ivh = (ItemViewHolder) viewHolder;

        if (itemType == TYPE_ITEM) {

            Author author = mDataSet.getAuthorGroup()
                    .get(sectionIndex).getAuthors().get(itemIndex);

            if (mDataSet.getAuthorGroup().get(sectionIndex).getAuthors()
                    .get(itemIndex).isFavorite()) {

                ivh.favoriteIcon.setImageResource(R.drawable.ic_author);

            } else if (mFavoriteAuthors.size() != 0) {

                for (int i = 0; i < mFavoriteAuthors.size(); i++) {

                    if (mDataSet.getAuthorGroup().get(sectionIndex).getAuthors().get(itemIndex).getAuthorId()
                            .equals(mFavoriteAuthors.get(i).getAuthorId())) {

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

            ivh.authorName.setText(author.getAuthorName());

            ivh.authorInfo.setText(author.getProfession() + " - "
                    + author.getQuotesCount() + " quotes");

            Glide.with(((ItemViewHolder) viewHolder).authorImage.getContext())
                    .load(Constants.IMAGES_URL + author.getAuthorId() + ".jpg")
                    .dontAnimate()
                    .placeholder(R.drawable.avatar)
                    .error(R.drawable.avatar)
                    .into(((ItemViewHolder) viewHolder).authorImage);

            ivh.favoriteIcon.setOnClickListener(new OnFavoriteAuthorClickListener(mContext,
                    mDataSet.getAuthorGroup().get(sectionIndex).getAuthors().get(itemIndex),
                    mFavoriteAuthors, ivh.favoriteIcon, null, false, null, null));

            ivh.itemView.setOnClickListener(new OnAuthorClickListener(mContext, mDataSet.getAuthorGroup()
                    .get(sectionIndex).getAuthors().get(itemIndex).getAuthorId()));

            setAnimation(ivh.itemView, viewHolder.getAdapterPosition());

        } else {

            ivh.text.setText("All " + mDataSet.getAuthorGroup().get(sectionIndex).getReferences()
                    .getLetter().toUpperCase() + " Authors...");

            ivh.itemView.setOnClickListener(new OnAuthorLetterClickListener
                    (mDataSet.getAuthorGroup().get(sectionIndex).getReferences()
                            .getLetter()));

            setAnimation(ivh.itemView, viewHolder.getAdapterPosition());
        }
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

    private class OnAuthorLetterClickListener implements View.OnClickListener {

        String letter;

        OnAuthorLetterClickListener(String letter) {
            this.letter = letter;
        }

        @Override
        public void onClick(View view) {

            Intent i = new Intent(mContext, Authors.class);
            i.putExtra("IS_BY_LETTER", true);
            i.putExtra("LETTER", letter);
            ((activities.authors.PopularAuthors) mContext).startActivityForResult(i, 1);

        }
    }
}
