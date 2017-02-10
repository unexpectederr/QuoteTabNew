package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import activities.quotetabnew.R;
import de.hdodenhof.circleimageview.CircleImageView;
import helpers.main.Constants;
import models.authors.AuthorDetails;
import models.authors.AuthorFields;
import models.dashboard.PopularAuthor;

/**
 * Created by Spaja on 09-Feb-17.
 */

public class DashboardAuthorAdapter extends RecyclerView.Adapter<DashboardAuthorAdapter.MyViewHolder> {

    private ArrayList<PopularAuthor> mDataSet;
    private Context context;

    public DashboardAuthorAdapter(ArrayList<PopularAuthor> mDataSet, Context context) {

        this.mDataSet = mDataSet;
        this.context = context;

    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView authorName;
        CircleImageView authorImage;

        MyViewHolder(View itemView) {
            super(itemView);

            authorName = (TextView) itemView.findViewById(R.id.dashboard_author_name);
            authorImage = (CircleImageView) itemView.findViewById(R.id.dashboard_author_image);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.dashboard_author_item, parent, false);
        return new DashboardAuthorAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

//        Glide.with(context).load(Constants.IMAGES_URL + mDataSet.get(position).getImageUrl())
//                .dontAnimate()
//                .error(R.drawable.avatar)
//                .placeholder(R.drawable.avatar)
//                .into(holder.authorImage);

        holder.authorName.setText(mDataSet.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

}
