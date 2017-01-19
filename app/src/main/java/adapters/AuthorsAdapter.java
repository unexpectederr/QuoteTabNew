package adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import org.zakariya.stickyheaders.SectioningAdapter;

import de.hdodenhof.circleimageview.CircleImageView;
import digitalbath.quotes.QuotesByAuthor;
import digitalbath.quotetabnew.R;
import helpers.main.Constants;
import models.authors.AuthorFields;
import models.authors.PopularAuthors;

/**
 * Created by Spaja on 26-Dec-16.
 */

public class AuthorsAdapter extends SectioningAdapter {

    private PopularAuthors mDataSet;
    private int numberOfSections;
    private Context context;
    private int lastPosition = -1;

    public AuthorsAdapter(PopularAuthors dataSet, Context context) {

        this.context = context;
        this.mDataSet = dataSet;

        numberOfSections = mDataSet.getAuthorGroup().size();

    }

    private class ItemViewHolder extends SectioningAdapter.ItemViewHolder {

        TextView authorName;
        TextView authorInfo;
        CircleImageView authorImage;

        ItemViewHolder(View itemView) {
            super(itemView);
            authorName = (TextView) itemView.findViewById(R.id.author_name);
            authorInfo = (TextView) itemView.findViewById(R.id.author_info);
            authorImage = (CircleImageView) itemView.findViewById(R.id.author_image);
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
        return numberOfSections;
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

        ivh.authorName.setText(authorFields.getName());

        ivh.authorInfo.setText(authorFields.getProfessionName() + " - "
                + authorFields.getQuotesCount() + " quotes");

        Glide.with(((ItemViewHolder) viewHolder).authorImage.getContext())
                    .load(Constants.IMAGES_URL + authorFields.getImageUrl())
                    .placeholder(R.drawable.avatar)
                    .error(R.drawable.avatar)
                    .into(((ItemViewHolder) viewHolder).authorImage);

        ivh.itemView.setOnClickListener(new OnAuthorClickListener(mDataSet.getAuthorGroup()
                .get(sectionIndex).getAuthors().get(itemIndex).getId()));

        setAnimation(ivh.itemView, viewHolder.getAdapterPosition());
    }

    private void setAnimation(View viewToAnimate, int position) {

        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.abc_slide_in_bottom);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public void onBindHeaderViewHolder(SectioningAdapter.HeaderViewHolder viewHolder, int sectionIndex,
                                       int headerType) {

        HeaderViewHolder hvh = (HeaderViewHolder) viewHolder;
        hvh.itemView.setBackgroundColor(0x55ffffff);
        hvh.header.setText(mDataSet.getAuthorGroup().get(sectionIndex).getReferences().getLetter());
    }

    private class OnAuthorClickListener implements View.OnClickListener{

        String authorID;

        OnAuthorClickListener(String authorID) {
            this.authorID = authorID;
        }

        @Override
        public void onClick(View v) {
            Intent i = new Intent(context, QuotesByAuthor.class);
            i.putExtra(Constants.AUTHOR_ID, authorID);
            context.startActivity(i);
        }
    }
}
