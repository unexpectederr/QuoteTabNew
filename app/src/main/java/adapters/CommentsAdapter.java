package adapters;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import digitalbath.quotetab.R;
import helpers.main.AppHelper;
import models.quotes.Comment;

/**
 * Created by unexpected_err on 19/02/2017.
 */

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {

    private ArrayList<Comment> mComments = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private LinearLayout mEmptyListCont;
    private Context mContext;
    private boolean isRecyclerInitialized;
    private DatabaseReference mDataBaseRef;

    public CommentsAdapter(Context context, DatabaseReference ref, RecyclerView recycler,
                           LinearLayout emptyListCont) {

        this.mRecyclerView = recycler;
        this.mEmptyListCont = emptyListCont;
        this.mContext = context;
        this.mDataBaseRef = ref;

        this.mDataBaseRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {

                mComments.clear();

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Comment comment = postSnapshot.getValue(Comment.class);
                    comment.setId(postSnapshot.getKey());
                    mComments.add(comment);
                }

                if (mComments.size() > 0) {
                    mRecyclerView.setVisibility(View.VISIBLE);
                    mEmptyListCont.setVisibility(View.GONE);
                } else {
                    mEmptyListCont.setVisibility(View.VISIBLE);
                    mEmptyListCont.startAnimation(AppHelper
                            .getAnimationUp(mContext));
                    return;
                }

                notifyDataSetChanged();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if (isRecyclerInitialized)
                            mRecyclerView.smoothScrollToPosition(mComments.size() - 1);

                        isRecyclerInitialized = true;
                    }
                }, 200);
            }

            @Override
            public void onCancelled(DatabaseError firebaseError) {
                mEmptyListCont.setVisibility(View.VISIBLE);
                mEmptyListCont.startAnimation(AppHelper
                        .getAnimationUp(mContext));
            }
        });
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final Comment comment = mComments.get(position);

        holder.text.setText(comment.getText());
        holder.username.setText(comment.getUsername());
        holder.time.setText(AppHelper.getTimeDifference(comment.getDate()));

        Glide.with(holder.avatar.getContext())
                .load(comment.getAvatar())
                .dontAnimate()
                .placeholder(R.drawable.avatar)
                .error(R.drawable.avatar)
                .into(holder.avatar);

        holder.numberOfLikes.setText(Integer.toString(comment.getLikes().size()));

        if (comment.getLikes().get(comment.getUserId()) != null &&
                comment.getLikes().get(comment.getUserId()) == true) {
            holder.like.setText("Unlike");
            holder.like.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        } else {
            holder.like.setText("Like");
            holder.like.setTextColor(mContext.getResources().getColor(R.color.main_color_dark));
        }

        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (comment.getLikes().get(comment.getUserId()) == null) {

                    Map<String, Object> taskMap = new HashMap<>();
                    taskMap.put(comment.getUserId(), true);

                    mDataBaseRef.child(comment.getId())
                            .child("likes").updateChildren(taskMap);

                } else {

                    mDataBaseRef.child(comment.getId())
                            .child("likes").child(comment.getUserId()).removeValue();
                }

            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment_list_item, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public int getItemCount() {
        return mComments.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView text;
        public TextView username;
        public TextView time;
        public TextView numberOfLikes;
        public CircleImageView avatar;
        public TextView like;

        ViewHolder(View itemView) {
            super(itemView);

            text = (TextView) itemView.findViewById(R.id.text);
            username = (TextView) itemView.findViewById(R.id.username);
            time = (TextView) itemView.findViewById(R.id.time_published);
            avatar = (CircleImageView) itemView.findViewById(R.id.avatar);
            numberOfLikes = (TextView) itemView.findViewById(R.id.likes);
            like = (TextView) itemView.findViewById(R.id.like);
        }
    }
}