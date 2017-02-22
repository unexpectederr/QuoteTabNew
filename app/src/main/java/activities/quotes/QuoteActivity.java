package activities.quotes;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;

import digitalbath.quotetab.R;
import adapters.CommentsAdapter;
import helpers.main.AppController;
import helpers.main.AppHelper;
import helpers.main.Constants;
import helpers.main.ReadAndWriteToFile;
import listeners.OnAuthorClickListener;
import listeners.OnFavoriteQuoteClickListener;
import listeners.OnShareClickListener;
import listeners.OnTagClickListener;
import models.quotes.Comment;
import models.quotes.Quote;

/**
 * Created by unexpected_err on 18/02/2017.
 */

public class QuoteActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 99;

    String primary_key;

    Quote mQuote;
    DatabaseReference mDatabase;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    FirebaseUser mUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote);

        initializeFirebaseAuthentication();

        mQuote = (Quote) getIntent()
                .getExtras().getSerializable("quote");

        primary_key = mQuote.getQuoteDetails().getQuoteId();

        initializeToolbar();

        bindHeader();

        initializeComments();

    }

    private void initializeComments() {

        mDatabase = FirebaseDatabase.getInstance().getReference();

        LinearLayout emptyListCont = (LinearLayout) findViewById(R.id.empty_list);

        RecyclerView recycler = (RecyclerView) findViewById(R.id.comments_recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        CommentsAdapter adapter = new CommentsAdapter(this, mDatabase
                .child("quotes").child(primary_key), recycler, emptyListCont);

        recycler.setAdapter(adapter);

        final EditText commentInput = (EditText) findViewById(R.id.comment_input);

        findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference quotes = mDatabase.child("quotes");

                Comment comment = new Comment();
                comment.setUsername(mUser.getDisplayName());
                comment.setText(commentInput.getText().toString());
                comment.setDate(new Date().toString());
                comment.setAvatar(mUser.getPhotoUrl().toString());

                quotes.child(primary_key).push().setValue(comment);

                commentInput.setText("");
            }
        });

    }

    private void initializeToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ImageView shareIcon = (ImageView) findViewById(R.id.share_quote_icon);

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.header);
        shareIcon.setOnClickListener(new OnShareClickListener(this,
                mQuote.getQuoteDetails().getQuoteText(),
                mQuote.getQuoteDetails().getAuthorName(), relativeLayout));

        ImageView favoriteIcon = (ImageView) findViewById(R.id.favorite_icon);

        if (mQuote.isFavorite()) {

            favoriteIcon.setImageResource(R.drawable.ic_favorite);

        } else {

            favoriteIcon.setImageResource(R.drawable.ic_favorite_empty);
        }

        ArrayList<Quote> favoriteQuotes = ReadAndWriteToFile
                .getFavoriteQuotes(this);

        favoriteIcon.setOnClickListener(new OnFavoriteQuoteClickListener(this, favoriteQuotes,
                favoriteIcon, mQuote, null, false));
    }

    private void bindHeader() {

        TextView quoteText = (TextView) findViewById(R.id.quoteText);
        LinearLayout quoteTags = (LinearLayout) findViewById(R.id.quote_tags);
        ImageView cardImage = (ImageView) findViewById(R.id.card_image);
        TextView authorName = (TextView) findViewById(R.id.card_author_name);

        AppController.loadImageIntoView(this, mQuote.getImageId(),
                cardImage, false, false);

        quoteText.setText(mQuote.getQuoteDetails().getQuoteText());
        quoteText.setTypeface(AppHelper.getRalewayLight(quoteText.getContext()));

        authorName.setText("- " + mQuote.getQuoteDetails().getAuthorName() + " -");
        authorName.setOnClickListener(new OnAuthorClickListener(this, mQuote
                    .getQuoteDetails().getAuthorId()));

        String[] tags = mQuote.getQuoteDetails().getCategories().split(" ");

        quoteTags.removeAllViews();

        if (tags[0].trim().length() != 0) {
            for (int i = 0; i < tags.length; i++) {

                if (i < Constants.MAX_NUMBER_OF_QUOTES) {

                    TextView quoteTag = new TextView(this);
                    quoteTag.setBackgroundResource(R.drawable.background_outline_g);
                    quoteTag.setText(tags[i]);
                    quoteTag.setPadding(30, 15, 30, 15);

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT));

                    params.setMarginStart(12);

                    quoteTag.setLayoutParams(params);
                    quoteTag.setGravity(Gravity.CENTER);
                    quoteTag.setTextColor(this.getResources().getColor(R.color.light_gray));
                    quoteTag.setTypeface(AppHelper.getRalewayLight(this));
                    quoteTag.setOnClickListener(new OnTagClickListener(this, tags[i], false));

                    quoteTags.addView(quoteTag);
                }
            }
        }
    }

    //region FIREBASE_AUTH
    private void initializeFirebaseAuthentication() {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        final GoogleApiClient mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        int sd = 0;
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        Button signInButton = (Button) findViewById(R.id.sign_in_button);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                mUser = firebaseAuth.getCurrentUser();

                RelativeLayout signInCont = (RelativeLayout) findViewById(R.id.sign_in_cont);
                RelativeLayout postCommentCont = (RelativeLayout) findViewById(R.id.post_comment_cont);

                if (mUser == null) {

                    signInCont.setVisibility(View.VISIBLE);
                    postCommentCont.setVisibility(View.GONE);

                } else {

                    signInCont.setVisibility(View.GONE);
                    postCommentCont.setVisibility(View.VISIBLE);

                }
            }
        };
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (!task.isSuccessful()) {}

                    }
                });
    }
    //endregion

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {

            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            if (result.isSuccess()) {

                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);

            } else {
                // Google Sign In failed, update UI appropriately
                // ...
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
