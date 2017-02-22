package listeners;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import activities.quotes.QuoteActivity;
import models.quotes.Quote;

/**
 * Created by unexpected_err on 18/02/2017.
 */

public class OnQuoteClickListener implements View.OnClickListener {

    private Quote mQuote;
    private Activity mActivity;

    public OnQuoteClickListener(Activity activity, Quote quote) {
        this.mQuote = quote;
        this.mActivity = activity;
    }

    @Override
    public void onClick(View view) {

        Intent intent = new Intent(mActivity, QuoteActivity.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable("quote", mQuote);
        intent.putExtras(bundle);

        mActivity.startActivity(intent);

    }
}
