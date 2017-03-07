package helpers.main;


import java.util.ArrayList;
import java.util.Random;

import models.authors.Author;
import models.authors.AuthorDetails;
import models.authors.AuthorDetailsFromQuote;
import models.authors.AuthorFields;
import models.quotes.Quote;
import models.quotes.QuoteReference;

/**
 * Created by Spaja on 02-Mar-17.
 */

public class Mapper {

    public static ArrayList<Quote> mapQuotes(ArrayList<QuoteReference> quoteReferences) {

        ArrayList<Quote> quotes = new ArrayList<>();

        for (int i = 0; i < quoteReferences.size(); i++) {

            if (quoteReferences.get(i).getQuoteDetails() != null) {

                Quote quote = new Quote();

                QuoteReference qr = quoteReferences.get(i);

                quote.setQuoteId(qr.getQuoteId());
                quote.setQuoteText(qr.getQuoteDetails().getQuoteText());
                quote.setCategories(qr.getQuoteDetails().getCategories());

                Author author = new Author();
                author.setAuthorId(qr.getQuoteDetails().getAuthorId());
                author.setAuthorName(qr.getQuoteDetails().getAuthorName());

                quote.setAuthor(author);

                quotes.add(quote);
            }
        }

        return quotes;
    }

    public static ArrayList<Quote> mapQuotesFromDashboardData(ArrayList<QuoteReference> quoteReferences) {

        ArrayList<Quote> quotes = new ArrayList<>();

        for (int i = 0; i < quoteReferences.size(); i++) {

            Quote quote = new Quote();

            QuoteReference qr = quoteReferences.get(i);

            quote.setQuoteId(qr.getAuthor().getQuoteId());
            quote.setQuoteText(qr.getAuthor().getQuoteText());
            quote.setImageId(new Random().nextInt(Constants.NUMBER_OF_COVERS));

            Author author = new Author();
            author.setAuthorId(qr.getAuthor().getAuthor().getAuthorId());
            author.setAuthorName(qr.getAuthor().getAuthor().getAuthorName());
            author.setQuotesCount(qr.getAuthor().getAuthor().getQuotesCount());

            if (qr.getAuthor().getAuthor().getProfession() != null)
                author.setProfession(qr.getAuthor().getAuthor().getProfession().getProfessionName());

            quote.setAuthor(author);

            quotes.add(quote);
        }

        return quotes;
    }

    public static ArrayList<Author> mapAuthors(ArrayList<AuthorDetails> authorDetails) {

        ArrayList<Author> authors = new ArrayList<>();

        for (int i = 0; i < authorDetails.size(); i++) {

            Author author = new Author();

            AuthorFields ad = authorDetails.get(i).getAuthorFields();

            if (ad != null) {
                author.setAuthorId(ad.getAuthorId());
                author.setAuthorName(ad.getName());
                author.setQuotesCount(ad.getQuotesCount());
                author.setProfession(ad.getProfessionName());
            }

            author.setLast(authorDetails.get(i).isLast());

            authors.add(author);
        }

        return authors;
    }

    public static Author mapAuthor(AuthorDetailsFromQuote authorDetailsFromQuote) {

        Author author = new Author();

        if (authorDetailsFromQuote.getAuthor() != null) {

            author.setAuthorName(authorDetailsFromQuote.getAuthor().getAuthorName());
            author.setAuthorId(authorDetailsFromQuote.getAuthor().getAuthorId());
            author.setQuotesCount(authorDetailsFromQuote.getAuthor().getQuotesCount());
            author.setProfession(authorDetailsFromQuote.getAuthor().getProfession().getProfessionName());

        } else {

            author.setAuthorName(authorDetailsFromQuote.getAuthorFieldsFromQuote().getAuthorName());
            author.setAuthorId(authorDetailsFromQuote.getAuthorFieldsFromQuote().getAuthorId());
            author.setQuotesCount(authorDetailsFromQuote.getAuthorFieldsFromQuote().getQuotesCount());
            author.setProfession(authorDetailsFromQuote.getAuthorFieldsFromQuote().getProfession().getProfessionName());
            author.setWikipediaUrl(authorDetailsFromQuote.getAuthorFieldsFromQuote().getWikipediaUrl());
            author.setBornDay(authorDetailsFromQuote.getAuthorFieldsFromQuote().getBornDay());
            author.setBornMonth(authorDetailsFromQuote.getAuthorFieldsFromQuote().getBornMonth());
            author.setBornYear(authorDetailsFromQuote.getAuthorFieldsFromQuote().getBornYear());
            author.setDescription(authorDetailsFromQuote.getAuthorFieldsFromQuote().getDescription());
        }

        return author;
    }
}
