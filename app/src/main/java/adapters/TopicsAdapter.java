package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yayandroid.parallaxrecyclerview.ParallaxViewHolder;

import java.util.ArrayList;

import activities.quotetabnew.R;
import helpers.main.AppController;
import listeners.OnTagClickListener;
import models.topics.Topic;

/**
 * Created by Spaja on 17-Jan-17.
 */

public class TopicsAdapter extends RecyclerView.Adapter<TopicsAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Topic> mDataSet;

    public TopicsAdapter(Context context, ArrayList<Topic> mDataSet) {

        this.context = context;
        this.mDataSet = mDataSet;
    }

    class ViewHolder extends ParallaxViewHolder {

        TextView topicName;

        @Override
        public int getParallaxImageId() {
            return R.id.background_image;
        }

        ViewHolder(View itemView) {
            super(itemView);

            topicName = (TextView) itemView.findViewById(R.id.topic_name);
        }
    }


    @Override
    public TopicsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.topics_recycler_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TopicsAdapter.ViewHolder holder, int position) {

        if (mDataSet.get(position).getImageId() == 0)
            mDataSet.get(position).setImageId(AppController.getBitmapIndex());

        AppController.loadImageIntoView(context, mDataSet.get(position).getImageId(),
                holder.getBackgroundImage(), false, false);

        holder.topicName.setText(mDataSet.get(position).getSource().getTopicName());

        holder.itemView.setOnClickListener(new OnTagClickListener(
                context, holder.topicName.getText().toString().toLowerCase(), false));

        holder.getBackgroundImage().reuse();
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void addTopics(ArrayList<Topic> topics) {
        mDataSet.addAll(topics);
        notifyItemRangeInserted(mDataSet.size() - topics.size(), topics.size());
    }

}
