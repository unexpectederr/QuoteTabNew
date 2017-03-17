package listeners;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ImageView;

import java.util.ArrayList;

import adapters.PopularAuthorsAdapter;
import helpers.main.ReadAndWriteToFile;
import digitalbath.quotetab.R;
import models.authors.Author;
import models.authors.AuthorDetails;
import models.authors.AuthorGroup;
import models.authors.PopularAuthors;

/**
 * Created by unexpected_err on 11/01/2017.
 */

public class OnSearchAuthorWatcher implements TextWatcher {

    private PopularAuthors mDataSet;
    private RecyclerView mRecyclerView;
    private ImageView mSearchIcon;
    private Context mContext;

    public OnSearchAuthorWatcher(PopularAuthors dataSet, RecyclerView recyclerView,
                                 ImageView searchIcon, Context context) {
        mDataSet = dataSet;
        mRecyclerView = recyclerView;
        mSearchIcon = searchIcon;
        mContext = context;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence query, int ia, int i1a, int i2a) {

        if (query.length() == 0)
            mSearchIcon.setImageResource(R.drawable.ic_search);
        else
            mSearchIcon.setImageResource(R.drawable.ic_close);

        ArrayList<AuthorGroup> authorGroup = new ArrayList<>();

        for (int i = 0; i < mDataSet.getAuthorGroup().size(); i++) {

            AuthorGroup author = mDataSet.getAuthorGroup().get(i);

            AuthorGroup newAuthorGroup = new AuthorGroup();
            newAuthorGroup.setReferences(author.getReferences());
            newAuthorGroup.setAuthors(new ArrayList<AuthorDetails>());

            for (int j = 0; j < author.getAuthors().size() - 1; j++) {
                if (author.getAuthors().get(j).getAuthorId().contains(query.toString().toLowerCase()))
                    newAuthorGroup.getAuthorDetailsList().add(author.getAuthorDetailsList().get(j));
            }

            if (newAuthorGroup.getAuthors().size() != 0) {

                AuthorDetails authorLast = new AuthorDetails();
                authorLast.setLast(true);
                newAuthorGroup.getAuthorDetailsList().add(authorLast);
                authorGroup.add(newAuthorGroup);
            }
        }

        PopularAuthors popularAuthors = new PopularAuthors();
        popularAuthors.setAuthorGroup(authorGroup);

        ArrayList<Author> favoriteAuthors = ReadAndWriteToFile.getFavoriteAuthors(mContext);
        PopularAuthorsAdapter adapter = new PopularAuthorsAdapter(popularAuthors, mContext, favoriteAuthors);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
