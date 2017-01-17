package helpers;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import digitalbath.quotetabnew.R;

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

    public static Animation getAnimationUp(Context context) {
        return AnimationUtils.loadAnimation(context, R.anim.abc_grow_fade_in_from_bottom);
    }

    public static Animation getAnimationDown(Context context) {
        return AnimationUtils.loadAnimation(context, R.anim.abc_shrink_fade_out_from_bottom);
    }

    public static Typeface getRalewayLigt(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "raleway_light.ttf");
    }

    public static Typeface getRalewayRegular(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "raleway_regular.ttf");
    }

    public static Typeface getRalewayBold(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "raleway_bold.ttf");
    }
}
