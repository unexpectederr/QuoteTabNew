package activities.quotes;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
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
import listeners.SaveImageToFileClickListener;
import models.comments.Comment;
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
    ImageView mSaveIcon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quote);

        initializeFirebaseAuthentication();

        mQuote = (Quote) getIntent()
                .getExtras().getSerializable("quote");

        primary_key = mQuote.getQuoteId();

        initializeToolbar();

        bindHeader();

        initializeComments();

    }

    private void initializeComments() {

        mDatabase = FirebaseDatabase.getInstance().getReference();

        final EditText commentInput = (EditText) findViewById(R.id.comment_input);

        findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference quotes = mDatabase.child("quotes");

                Comment comment = new Comment();
                comment.setUsername(mUser.getDisplayName());
                comment.setUserId(mUser.getUid());
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

        ImageView favoriteIcon = (ImageView) findViewById(R.id.favorite_icon);

        if (mQuote.isFavorite()) {

            favoriteIcon.setImageResource(R.drawable.ic_favorite);

        } else {

            favoriteIcon.setImageResource(R.drawable.ic_favorite_empty);
        }

        ArrayList<Quote> favoriteQuotes = ReadAndWriteToFile
                .getFavoriteQuotes(this);

        favoriteIcon.setOnClickListener(new OnFavoriteQuoteClickListener(this, favoriteQuotes,
                favoriteIcon, mQuote, null, false, null, null));
    }

    private void bindHeader() {

        TextView quoteText = (TextView) findViewById(R.id.quoteText);
        ImageView cardImage = (ImageView) findViewById(R.id.card_image);
        TextView authorName = (TextView) findViewById(R.id.card_author_name);

        AppController.loadImageIntoView(this, mQuote.getImageId(),
                cardImage, false, false);

        quoteText.setText(mQuote.getQuoteText());
        quoteText.setTypeface(AppHelper.getRalewayLight(quoteText.getContext()));

        authorName.setText("- " + mQuote.getAuthor().getAuthorName() + " -");
        authorName.setOnClickListener(new OnAuthorClickListener(this, mQuote
                .getAuthor().getAuthorId()));

        ImageView shareIcon = (ImageView) findViewById(R.id.share_quote_icon);

        shareIcon.setOnClickListener(new OnShareClickListener(this,
                mQuote.getQuoteText(), mQuote.getAuthor().getAuthorName(),
                Constants.COVER_IMAGES_URL + mQuote.getImageId() + ".jpg", quoteText));

        mSaveIcon = (ImageView) findViewById(R.id.download_icon);

        mSaveIcon.setOnClickListener(new SaveImageToFileClickListener(this, mQuote.getQuoteText(),
                mQuote.getAuthor().getAuthorName(),
                Constants.COVER_IMAGES_URL + mQuote.getImageId() + ".jpg", quoteText.getLineCount(),
                null));

        String[] tags = mQuote.getCategories().split(" ");
        LinearLayout quoteTags = (LinearLayout) findViewById(R.id.tags);

        if (tags[0].trim().length() != 0) {

            for (int i = 0; i < 4; i++) {

                TextView tag = (TextView) quoteTags.getChildAt(i);

                if (tags.length > i && tags[i] != null) {

                    tag.setVisibility(View.VISIBLE);
                    tag.setText(tags[i]);
                    tag.setOnClickListener(new OnTagClickListener
                            (this, tags[i], false));

                } else {

                    tag.setVisibility(View.GONE);

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

                String userId = "";

                if (mUser == null) {

                    signInCont.setVisibility(View.VISIBLE);
                    postCommentCont.setVisibility(View.GONE);

                } else {

                    signInCont.setVisibility(View.GONE);
                    postCommentCont.setVisibility(View.VISIBLE);

                    userId = mUser.getUid();
                }

                LinearLayout emptyListCont = (LinearLayout) findViewById(R.id.empty_list_favorites);
                RecyclerView recycler = (RecyclerView) findViewById(R.id.comments_recycler);
                recycler.setLayoutManager(new LinearLayoutManager(QuoteActivity.this));

                CommentsAdapter adapter = new CommentsAdapter(QuoteActivity.this, mDatabase
                        .child("quotes").child(primary_key), recycler, emptyListCont, userId);

                recycler.setAdapter(adapter);
            }
        };
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (!task.isSuccessful()) {
                        }

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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode == Constants.SAVE_IMAGE_PERMISSION && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mSaveIcon.performClick();
        }
    }
}
