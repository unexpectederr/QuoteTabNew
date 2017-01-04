package adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.zakariya.stickyheaders.SectioningAdapter;

import digitalbath.quotetabnew.R;
import helpers.AppController;
import models.authors.Authors;

/**
 * Created by Spaja on 26-Dec-16.
 */

public class AuthorsAdapter extends SectioningAdapter {

    private Authors mDataset;
    private int numberOfSections;
    private int numberOfItemsInSection;

    public AuthorsAdapter(Authors mDataSet) {
        numberOfSections = mDataSet.getPopularAuthors().size();
        for (int i = 0; i < mDataSet.getPopularAuthors().size(); i++) {
            numberOfItemsInSection = mDataSet.getPopularAuthors().get(i).getResults().size();
        }
        this.mDataset = mDataSet;
    }

    private class ItemViewHolder extends SectioningAdapter.ItemViewHolder {

        TextView authorName;
        ImageView authorImage;

        ItemViewHolder(View itemView) {
            super(itemView);
            authorName = (TextView) itemView.findViewById(R.id.author_name);
            authorImage = (ImageView) itemView.findViewById(R.id.author_image);
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
        return numberOfItemsInSection;
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
    public void onBindItemViewHolder(SectioningAdapter.ItemViewHolder viewHolder, int sectionIndex, int itemIndex, int itemType) {
        ItemViewHolder ivh = (ItemViewHolder) viewHolder;

        ivh.authorName.setText(mDataset.getPopularAuthors().get(sectionIndex).getResults().get(itemIndex).getFields().getName().get(0));
        if (mDataset.getPopularAuthors().get(sectionIndex).getResults().get(itemIndex).getFields().getImageUrl() != null) {
            Glide.with(((ItemViewHolder) viewHolder).authorImage.getContext())
                    .load(AppController.IMAGES_URL + mDataset.getPopularAuthors().get(sectionIndex).getResults().get(itemIndex).getFields().getImageUrl().get(0))
                    .placeholder(R.drawable.avatar)
                    .into(((ItemViewHolder) viewHolder).authorImage);
        } else {
            Glide.with(((ItemViewHolder) viewHolder).authorImage.getContext())
                    .load(R.drawable.avatar)
                    .into(((ItemViewHolder) viewHolder).authorImage);
        }
    }

    @Override
    public void onBindHeaderViewHolder(SectioningAdapter.HeaderViewHolder viewHolder, int sectionIndex, int headerType) {
        HeaderViewHolder hvh = (HeaderViewHolder) viewHolder;
        hvh.itemView.setBackgroundColor(0x55ffffff);
        hvh.header.setText(mDataset.getPopularAuthors().get(sectionIndex).getReferences().getLetter());
    }
}
