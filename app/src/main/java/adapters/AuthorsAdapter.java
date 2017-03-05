package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import digitalbath.quotetab.R;
import de.hdodenhof.circleimageview.CircleImageView;
import helpers.main.Constants;
import helpers.main.ReadAndWriteToFile;
import listeners.OnAuthorClickListener;
import listeners.OnFavoriteAuthorClickListener;
import models.authors.Author;
import models.authors.AuthorDetails;

/**
 * Created by Spaja on 31-Jan-17.
 */

public class AuthorsAdapter extends RecyclerView.Adapter<AuthorsAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<Author> mDataSet;
    private ArrayList<Author> favoriteAuthors;
    private boolean isFromAllAuthors;
    private int mLastPosition = -1;
    private RelativeLayout emptyList;
    private RecyclerView recyclerView;

    public AuthorsAdapter(Context context, ArrayList<Author> authors, boolean isFromAllAuthors,
                          RecyclerView recyclerView, RelativeLayout emptyList) {

        this.mContext = context;
        this.mDataSet = authors;
        this.isFromAllAuthors = isFromAllAuthors;
        this.emptyList = emptyList;
        this.recyclerView = recyclerView;

        favoriteAuthors = ReadAndWriteToFile.getFavoriteAuthors(context);

        if (recyclerView != null && emptyList != null && authors.size() != 0) {
            emptyList.setVisibility(View.GONE);
        } else if (recyclerView != null && emptyList != null && authors.size() == 0) {
            recyclerView.setVisibility(View.GONE);
            emptyList.setVisibility(View.VISIBLE);
        }
    }

    public void addAuthors(ArrayList<Author> authors) {

        mDataSet.addAll(authors);
        notifyItemRangeInserted(mDataSet.size() - authors.size(), authors.size());

    }

    class MyViewHolder extends RecyclerView.ViewHolder {


        TextView authorName;
        TextView authorInfo;
        CircleImageView authorImage;
        ImageView favoriteIcon;

        MyViewHolder(View itemView) {

            super(itemView);
            authorName = (TextView) itemView.findViewById(R.id.author_name);
            authorInfo = (TextView) itemView.findViewById(R.id.author_info);
            authorImage = (CircleImageView) itemView.findViewById(R.id.author_image);
            favoriteIcon = (ImageView) itemView.findViewById(R.id.author_favorite);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.fav_authors_list_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.authorName.setText(mDataSet.get(position).getAuthorName());

        holder.authorInfo.setText(mDataSet.get(position).getProfession() + " - "
                + mDataSet.get(position).getQuotesCount() + " quotes");

        Glide.with(holder.authorImage.getContext())
                .load(Constants.IMAGES_URL + mDataSet.get(position).getAuthorId() + ".jpg")
                .dontAnimate()
                .placeholder(R.drawable.avatar)
                .error(R.drawable.avatar)
                .into(holder.authorImage);

        holder.favoriteIcon.setImageResource(R.drawable.ic_author_empty);

        for (int i = 0; i < favoriteAuthors.size(); i++) {

            if (mDataSet.get(position).getAuthorId()
                    .equals(favoriteAuthors.get(i).getAuthorId())) {

                holder.favoriteIcon.setImageResource(R.drawable.ic_author);
                mDataSet.get(position).setFavorite(true);
            }
        }

        if (isFromAllAuthors) {

            holder.favoriteIcon.setOnClickListener(new OnFavoriteAuthorClickListener(mContext,
                    mDataSet.get(position), mDataSet, holder.favoriteIcon, this, false, null, null));
        } else {

            holder.favoriteIcon.setOnClickListener(new OnFavoriteAuthorClickListener(mContext,
                    mDataSet.get(position), mDataSet, holder.favoriteIcon, this, true, recyclerView, emptyList));
        }

        holder.itemView.setOnClickListener(new OnAuthorClickListener(mContext,
                mDataSet.get(position).getAuthorId()));

        setAnimation(holder.itemView, holder.getAdapterPosition());
    }

    private void setAnimation(View viewToAnimate, int position) {

        if (position > mLastPosition) {
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.abc_slide_in_bottom);
            viewToAnimate.startAnimation(animation);
            mLastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

}
