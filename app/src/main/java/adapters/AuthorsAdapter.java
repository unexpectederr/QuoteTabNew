package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import activities.quotetabnew.R;
import de.hdodenhof.circleimageview.CircleImageView;
import helpers.main.Constants;
import helpers.other.ReadAndWriteToFile;
import listeners.OnAuthorClickListener;
import listeners.OnFavoriteAuthorClickListener;
import models.authors.AuthorDetails;

/**
 * Created by Spaja on 31-Jan-17.
 */

public class AuthorsAdapter extends RecyclerView.Adapter<AuthorsAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<AuthorDetails> mDataSet;
    private ArrayList<AuthorDetails> favoriteAuthors;
    private boolean isFromAllAuthors;

    public AuthorsAdapter(Context context, ArrayList<AuthorDetails> Authors, boolean isFromAllAuthors) {

        this.context = context;
        this.mDataSet = Authors;
        favoriteAuthors = ReadAndWriteToFile.getFavoriteAuthors(context);
        this.isFromAllAuthors = isFromAllAuthors;

    }

    public void addAuthors(ArrayList<AuthorDetails> authors) {

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

        holder.authorName.setText(mDataSet.get(position).getAuthorFields().getName());

        holder.authorInfo.setText(mDataSet.get(position).getAuthorFields().getProfessionName() + " - "
                + mDataSet.get(position).getAuthorFields().getQuotesCount() + " quotes");

        Glide.with(holder.authorImage.getContext())
                .load(Constants.IMAGES_URL + mDataSet.get(position).getAuthorFields()
                        .getImageUrl()).dontAnimate()
                .placeholder(R.drawable.avatar)
                .error(R.drawable.avatar)
                .into(holder.authorImage);

        holder.favoriteIcon.setImageResource(R.drawable.ic_author_empty);

        for (int i = 0; i < favoriteAuthors.size(); i++) {

            if (mDataSet.get(position).getId().equals(favoriteAuthors.get(i).getId())) {

                holder.favoriteIcon.setImageResource(R.drawable.ic_author);
                mDataSet.get(position).setFavorite(true);
            }
        }

        if (isFromAllAuthors) {

            holder.favoriteIcon.setOnClickListener(new OnFavoriteAuthorClickListener(context,
                    mDataSet.get(position), mDataSet, holder.favoriteIcon, this, false));
        } else {

            holder.favoriteIcon.setOnClickListener(new OnFavoriteAuthorClickListener(context,
                    mDataSet.get(position), mDataSet, holder.favoriteIcon, this, true));
        }

        holder.itemView.setOnClickListener(new OnAuthorClickListener(context, mDataSet.get(position).getId()));
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

}
