package helpers.other;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by unexpected_err on 12/02/2017.
 */

public class ExpandableFrame extends RelativeLayout {

    boolean isExpanded = false;

    public ExpandableFrame(Context context) {
        super(context);
    }

    public ExpandableFrame(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExpandableFrame(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }
}
