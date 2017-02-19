package helpers.main;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import digitalbath.quotetab.R;
import io.codetail.animation.ViewAnimationUtils;
import io.codetail.widget.RevealFrameLayout;
import listeners.OnCloseRevealedLayoutListener;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;

/**
 * Created by unexpected_err on 17/10/2016.
 */

public class AppHelper {

    public static void showToast(String text, Context context) {

        if (context == null)
            return;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.custom_toast, null);

        TextView textView = (TextView) layout.findViewById(R.id.textToShow);
        textView.setText(text);

        Toast toast = new Toast(context.getApplicationContext());
        toast.setGravity(Gravity.FILL_HORIZONTAL | Gravity.BOTTOM, 0, 150);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();

    }

    public static void showMaterialTip(View v, final Activity context, String title,
                                       String message, final String tip, int icon) {

        new MaterialTapTargetPrompt.Builder(context)
                .setTarget(v)
                .setPrimaryText(title)
                .setSecondaryText(message)
                .setIcon(icon)
                .setOnHidePromptListener(new MaterialTapTargetPrompt.OnHidePromptListener() {
                    @Override
                    public void onHidePrompt(MotionEvent event, boolean tappedTarget) {
                        SharedPreferences.Editor editor = PreferenceManager
                                .getDefaultSharedPreferences(context).edit();
                        editor.putBoolean(tip, true);
                        //editor.commit();
                    }

                    @Override
                    public void onHidePromptComplete() {

                    }
                })
                .show();
    }

    public static void expand(final View v, int duration, int targetHeight) {

        int prevHeight  = v.getHeight();

        v.setVisibility(View.VISIBLE);
        ValueAnimator valueAnimator = ValueAnimator.ofInt(prevHeight, targetHeight);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.getLayoutParams().height = (int) animation.getAnimatedValue();
                v.requestLayout();
            }
        });
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }

    public static void collapse(final View v, int duration, int targetHeight) {

        int prevHeight  = v.getHeight();
        ValueAnimator valueAnimator = ValueAnimator.ofInt(prevHeight, targetHeight);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.getLayoutParams().height = (int) animation.getAnimatedValue();
                v.requestLayout();
            }
        });
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }

    public static String getTimeDifference(String timePublished) {

        String newsTimeStamp = "";

        if (timePublished == null)
            return newsTimeStamp;

        SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
        dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));
        SimpleDateFormat dateFormatLocal = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");

        Date now = null;

        try {
            now = dateFormatLocal.parse(dateFormatGmt.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date articleDate = null;

        try {
            articleDate = dateFormatLocal.parse(dateFormatGmt.format(new Date(timePublished)));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (articleDate != null) {

            long diff = now.getTime() - articleDate.getTime();

            if (diff <= 0)
                return "0 minutes ago";

            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);

            if (diffDays > 0) {
                if (diffHours > 11)
                    diffDays += 1;
                newsTimeStamp = Long.toString(diffDays) + (" days ago");
            } else if (diffHours > 0) {
                if (diffMinutes > 29)
                    diffHours += 1;
                newsTimeStamp = Long.toString(diffHours) + (" hours ago");
            } else
                newsTimeStamp = Long.toString(diffMinutes).replace("-", "") + (" minutes ago");

        }

        return newsTimeStamp;
    }

    public static void revealLayout(View authorInfo, View view, ImageView closeBtn) {

        ((RevealFrameLayout) authorInfo.getParent()).setVisibility(View.VISIBLE);

        // get the center for the clipping circle
        final int cx = (view.getLeft() + view.getRight()) / 2;
        final int cy = (view.getTop() + view.getBottom()) / 2;

        // get the final radius for the clipping circle
        int dx = Math.max(cx, authorInfo.getWidth() - cx);
        int dy = Math.max(cy, authorInfo.getHeight() - cy);
        final float finalRadius = (float) Math.hypot(dx, dy);

        // Android native animator
        final Animator animator =
                ViewAnimationUtils.createCircularReveal(authorInfo, cx, cy, 0, finalRadius);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(600);
        animator.start();

        closeBtn.setOnClickListener(new OnCloseRevealedLayoutListener(authorInfo, cx, cy, finalRadius));
    }

    public static Animation getAnimationUp(Context context) {
        return AnimationUtils.loadAnimation(context, R.anim.abc_grow_fade_in_from_bottom);
    }

    public static Animation getAnimationDown(Context context) {
        return AnimationUtils.loadAnimation(context, R.anim.abc_shrink_fade_out_from_bottom);
    }

    public static Typeface getRalewayLight(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "raleway_light.ttf");
    }

    public static Typeface getRalewayRegular(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "raleway_regular.ttf");
    }

    public static Typeface getRalewayBold(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "raleway_bold.ttf");
    }
}
