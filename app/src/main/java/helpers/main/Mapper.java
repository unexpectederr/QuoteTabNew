package helpers.main;

import com.google.android.gms.auth.api.Auth;

import java.util.ArrayList;

import models.authors.AuthorDetails;
import models.authors.AuthorDetailsFromQuote;
import models.authors.AuthorFields;

/**
 * Created by Spaja on 02-Mar-17.
 */

public class Mapper {

    public static AuthorDetails getAuthorFromQuote(AuthorDetailsFromQuote authorDetailsFromQuote) {

        AuthorDetails author = new AuthorDetails();
        AuthorFields fields = new AuthorFields();

        ArrayList<String> name = new ArrayList<>();
        ArrayList<String> authorId = new ArrayList<>();
        ArrayList<String> imageUrl = new ArrayList<>();
        ArrayList<Integer> quotesCount = new ArrayList<>();
        ArrayList<String> professionName = new ArrayList<>();

        if (authorDetailsFromQuote.getAuthor() != null) {

            name.add(authorDetailsFromQuote.getAuthor().getAuthorName());
            authorId.add(authorDetailsFromQuote.getAuthor().getAuthorId());
            imageUrl.add(authorDetailsFromQuote.getAuthor().getAuthorImageUrl());
            quotesCount.add(authorDetailsFromQuote.getAuthor().getQuotesCount());
            professionName.add(authorDetailsFromQuote.getAuthor().getProfession().getProfessionName());
            author.setId(authorDetailsFromQuote.getAuthor().getAuthorId());

        } else {

            name.add(authorDetailsFromQuote.getAuthorFieldsFromQuote().getAuthorName());
            authorId.add(authorDetailsFromQuote.getAuthorFieldsFromQuote().getAuthorId());
            imageUrl.add(authorDetailsFromQuote.getAuthorFieldsFromQuote().getAuthorImageUrl());
            quotesCount.add(authorDetailsFromQuote.getAuthorFieldsFromQuote().getQuotesCount());
            professionName.add(authorDetailsFromQuote.getAuthorFieldsFromQuote().getProfession().getProfessionName());
            author.setId(authorDetailsFromQuote.getAuthorFieldsFromQuote().getAuthorId());
        }

        fields.setName(name);
        fields.setAuthorId(authorId);
        fields.setImageUrl(imageUrl);
        fields.setQuotesCount(quotesCount);
        fields.setProfessionName(professionName);

        author.setFavorite(authorDetailsFromQuote.isFavorite());
        author.setAuthorFields(fields);

        return author;
    }
}
