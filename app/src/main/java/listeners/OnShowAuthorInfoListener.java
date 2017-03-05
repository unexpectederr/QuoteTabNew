package listeners;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import digitalbath.quotetab.R;
import de.hdodenhof.circleimageview.CircleImageView;
import helpers.main.AppHelper;
import helpers.main.Constants;
import models.authors.Author;

/**
 * Created by unexpected_err on 02/02/2017.
 */

public class OnShowAuthorInfoListener implements View.OnClickListener {

    Activity mActivity;
    Author mAuthor;

    public OnShowAuthorInfoListener(Activity activity, Author author) {
        this.mActivity = activity;
        this.mAuthor = author;
    }

    @Override
    public void onClick(View view) {

        View authorInfo = mActivity.findViewById(R.id.author_info);
        ImageView closeInfo = (ImageView) authorInfo.findViewById(R.id.close_info);

        AppHelper.revealLayout(authorInfo, view, closeInfo, false);

        bindAuthorInfo();
    }

    private void bindAuthorInfo() {

        TextView infoText = (TextView) mActivity.findViewById(R.id.info_text);
        TextView infoTextName = (TextView) mActivity.findViewById(R.id.info_text_name);
        TextView infoTextBirthday = (TextView) mActivity.findViewById(R.id.info_text_birthday);
        TextView infoTextBirthplace = (TextView) mActivity.findViewById(R.id.info_text_birthplace);
        CircleImageView infoAuthorImage = (CircleImageView) mActivity.findViewById(R.id.info_author_image);

        infoText.setText(mAuthor.getDescription());
        infoTextName.setText(mAuthor.getAuthorName());
        infoTextBirthplace.setText(mAuthor.getBirthPlace());
        infoTextBirthday.setText("Born on " + mAuthor.getBornDay() + "."
                + mAuthor.getBornMonth()
                + "."
                + mAuthor.getBornYear());

        Glide.with(mActivity)
                .load(Constants.IMAGES_URL + mAuthor.getAuthorId() + ".jpg")
                .dontAnimate()
                .placeholder(R.drawable.avatar)
                .error(R.drawable.avatar)
                .into(infoAuthorImage);
    }
}
