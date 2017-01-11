package listeners;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Spaja on 10-Jan-17.
 */

public class OnFavoriteClickListener implements View.OnClickListener {

    private Context context;

    public OnFavoriteClickListener(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(context, "Favorite this quote", Toast.LENGTH_SHORT).show();
    }
}
