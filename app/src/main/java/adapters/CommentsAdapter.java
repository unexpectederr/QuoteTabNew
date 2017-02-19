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

import de.hdodenhof.circleimageview.CircleImageView;
import digitalbath.quotetab.R;
import helpers.main.AppHelper;
import models.Comment;

/**
 * Created by unexpected_err on 19/02/2017.
 */

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {

    ArrayList<Comment> mComments = new ArrayList<>();
    RecyclerView mRecyclerView;
    LinearLayout mEmptyListCont;
    private Context mContext;
    boolean isRecyclerInititlaized;

    public CommentsAdapter(Context context, DatabaseReference ref,
                           RecyclerView recycler, LinearLayout emptyListCont) {

        this.mRecyclerView = recycler;
        this.mEmptyListCont = emptyListCont;
        this.mContext = context;

        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {

                mComments.clear();

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Comment comment = postSnapshot.getValue(Comment.class);
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

                        if (isRecyclerInititlaized)
                            mRecyclerView.smoothScrollToPosition(mComments.size() - 1);

                        isRecyclerInititlaized = true;
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

        Comment comment = mComments.get(position);

        holder.text.setText(comment.getText());
        holder.username.setText(comment.getUsername());
        holder.time.setText(AppHelper.getTimeDifference(comment.getDate()));

        Glide.with(holder.avatar.getContext())
                .load(comment.getAvatar())
                .dontAnimate()
                .placeholder(R.drawable.avatar)
                .error(R.drawable.avatar)
                .into(holder.avatar);
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
        CircleImageView avatar;

        public ViewHolder(View itemView) {
            super(itemView);

            text = (TextView) itemView.findViewById(R.id.text);
            username = (TextView) itemView.findViewById(R.id.username);
            time = (TextView) itemView.findViewById(R.id.time_published);
            avatar = (CircleImageView) itemView.findViewById(R.id.avatar);
        }
    }
}