package helpers.main;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

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

    public static Bitmap createBitmapFromView(Activity context, String quoteText, String authorName,
                                              Bitmap quoteImage, int quoteLines) {

        View v;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.share_quote_container, null);

        TextView quoteTextTextView = (TextView) v.findViewById(R.id.quote_text);
        quoteTextTextView.setText(quoteText);

        TextView authorNameTextView = (TextView) v.findViewById(R.id.quote_author);
        authorNameTextView.setText("- " + authorName + " -");

        ImageView image = (ImageView) v.findViewById(R.id.card_image);
        image.setImageBitmap(quoteImage);

        DisplayMetrics metrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int specWidth = View.MeasureSpec.makeMeasureSpec(metrics.widthPixels, View.MeasureSpec.EXACTLY);

        int specHeight = View.MeasureSpec.makeMeasureSpec((int) (context.getResources()
                .getDimension(R.dimen.share_layout_height) + (quoteLines - 1) * context.getResources()
                .getDimension(R.dimen.share_layout_quote_line_height)), View.MeasureSpec.EXACTLY);

        v.measure(specWidth, specHeight);

        Bitmap bitmap = Bitmap.createBitmap(v.getMeasuredWidth(), v.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());
        v.draw(canvas);
        return bitmap;
    }

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

    public static void revealLayout(View viewToReveal, View view, ImageView closeBtn, boolean unReveal) {

        ((RevealFrameLayout) viewToReveal.getParent()).setVisibility(View.VISIBLE);

        // get the center for the clipping circle
        final int cx = (view.getLeft() + view.getRight()) / 2;
        final int cy = (view.getTop() + view.getBottom()) / 2;

        // get the final radius for the clipping circle
        int dx = Math.max(cx, viewToReveal.getWidth() - cx);
        int dy = Math.max(cy, viewToReveal.getHeight() - cy);
        final float finalRadius = (float) Math.hypot(dx, dy);

        if (unReveal) {
            AppHelper.unRevealLayout(viewToReveal, cx, cy, finalRadius);
            return;
        }

        // Android native animator
        final Animator animator =
                ViewAnimationUtils.createCircularReveal(viewToReveal, cx, cy, 0, finalRadius);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(600);
        animator.start();

        if (closeBtn != null)
            closeBtn.setOnClickListener(new OnCloseRevealedLayoutListener(viewToReveal,
                    cx, cy, finalRadius));
    }

    public static void unRevealLayout(final View viewToUnReveal, int cx, int cy, float finalRadius) {

        Animator animatorReverse = ViewAnimationUtils
                .createCircularReveal(viewToUnReveal, cx, cy, finalRadius, 0);

        animatorReverse.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorReverse.setDuration(600);
        animatorReverse.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ((RevealFrameLayout) viewToUnReveal.getParent()).setVisibility(View.INVISIBLE);
            }
        }, 600);
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

    public static void createAndSaveImage(Activity context, String quoteText,
                                          String authroName, Bitmap quoteImage, int quoteLines) {

       // if (tags != null && actionButtons != null) {
//            tags.setVisibility(View.GONE);
//            actionButtons.setVisibility(View.GONE);
     //   }
        //relativeLayout.setDrawingCacheEnabled(true);
        Bitmap bm = AppHelper.createBitmapFromView(context, quoteText, authroName, quoteImage, quoteLines);

        if (bm != null) {

            ReadAndWriteToFile.saveImage(bm, context);

            Toast toast = Toast.makeText(context, "Quote saved to Gallery",
                    Toast.LENGTH_LONG);
            toast.show();
//            relativeLayout.setDrawingCacheEnabled(false);
//            if (tags != null && actionButtons != null) {
//                tags.setVisibility(View.VISIBLE);
//                actionButtons.setVisibility(View.VISIBLE);
//            }

        } else {
            AppHelper.showToast("Something went wrong!", context);
        }
    }
}
