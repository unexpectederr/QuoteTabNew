package helpers.other;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import java.util.Random;

import activities.quotetabnew.R;
import helpers.main.AppController;

/**
 * Created by unexpected_err on 20/01/2017.
 */

public class RotatingImages extends RelativeLayout {

    private TranslateAnimation anim =  new TranslateAnimation(0, 39, 0, 0);
    private TranslateAnimation anim1 =  new TranslateAnimation(0, -39, 0, 0);
    private TranslateAnimation anim2 =  new TranslateAnimation(0, 19, 0, 39);
    private TranslateAnimation anim3 =  new TranslateAnimation(0, 39, 0, 19);

    private TranslateAnimation[] animations = {anim, anim1, anim2, anim3};

    private ImageView mainImageOne, mainImageTwo;
    
    private Handler customHandler;

    private boolean flag = true;

    private int lastIndex;

    //private int[] drawableImages = { R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.img4,
      //      R.drawable.img5, R.drawable.img6 };

    public RotatingImages(Context context) {
        super(context);
    }

    public RotatingImages(Context context, AttributeSet attrs) {
        super(context, attrs);

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT, (int)
                        getContext().getResources().getDimension(R.dimen.app_bar_height_header_images));

        lp.rightMargin = (int)
                getContext().getResources().getDimension(R.dimen.app_bar_images_margins);

        lp.leftMargin = (int)
                getContext().getResources().getDimension(R.dimen.app_bar_images_margins);

        mainImageOne = new ImageView(getContext());
        mainImageOne.setScaleType(ImageView.ScaleType.CENTER_CROP);
        mainImageOne.setLayoutParams(lp);
        setImageBitmap(mainImageOne);

        mainImageTwo = new ImageView(getContext());
        mainImageTwo.setScaleType(ImageView.ScaleType.CENTER_CROP);
        mainImageTwo.setLayoutParams(lp);
        setImageBitmap(mainImageTwo);

        addView(mainImageOne);
        addView(mainImageTwo);

        customHandler = new Handler();
        customHandler.postDelayed(updateTimerThread, 0);

    }

    private Runnable updateTimerThread = new Runnable() {

        public void run() {

            if (checkContext())
                return;

            if (flag) {

                mainImageOne.bringToFront();
                handleHeaderImagesLifecycle(mainImageOne);

                mainImageTwo.clearAnimation();
                setImageBitmap(mainImageTwo);

            } else {

                mainImageTwo.bringToFront();
                handleHeaderImagesLifecycle(mainImageTwo);

                mainImageOne.clearAnimation();
                setImageBitmap(mainImageOne);

            }

            customHandler.postDelayed(this, 6000);
        }

    };

    private void handleHeaderImagesLifecycle(ImageView img) {

        flag = !flag;

        AnimationSet animationSet = new AnimationSet(false);
        animationSet.addAnimation(getScaleAnimation());
        animationSet.addAnimation(getAlphaAnimation());
        animationSet.addAnimation(getTranslateAnimation());

        img.startAnimation(animationSet);

    }

    private void setImageBitmap(ImageView img) {

        int index = AppController.getBitmapIndex();

        while (lastIndex == index) {
            index = AppController.getBitmapIndex();
        }

        lastIndex = index;

        AppController.loadImageIntoView(((ContextWrapper) getContext()).getBaseContext(),
                index, img, true, false);

    }

    private boolean checkContext() {

        if (((ContextWrapper) getContext()).getBaseContext() instanceof Activity
                && ((Activity) ((ContextWrapper) getContext()).getBaseContext()).isFinishing()) {
            customHandler.removeCallbacks(updateTimerThread);
            return true;
        }

        return false;
    }

    private TranslateAnimation getTranslateAnimation() {

        int randomNumberForTranslate = new Random().nextInt(4);

        TranslateAnimation translateAnimation = animations[randomNumberForTranslate];
        translateAnimation.setDuration(6000);

        return translateAnimation;
    }

    private ScaleAnimation getScaleAnimation() {

        ScaleAnimation scaleAnimation;

        if (flag) {

            scaleAnimation = new ScaleAnimation(1, 1.1f, 1, 1.1f,
                    Animation.RELATIVE_TO_SELF, (float) 0.9,
                    Animation.RELATIVE_TO_SELF, (float) 0.9);
            scaleAnimation.setDuration(6000);

        } else {

            scaleAnimation = new ScaleAnimation(1, 1.04f, 1, 1.04f,
                    Animation.RELATIVE_TO_SELF, (float) 0.9,
                    Animation.RELATIVE_TO_SELF, (float) 0.9);
            scaleAnimation.setDuration(6000);
        }

        return scaleAnimation;
    }

    private AlphaAnimation getAlphaAnimation() {

        AlphaAnimation fadeOutAnimation = new AlphaAnimation(1.00f, 0.00f);
        fadeOutAnimation.setDuration(700);
        fadeOutAnimation.setStartOffset(5300);

        return fadeOutAnimation;

    }

}
