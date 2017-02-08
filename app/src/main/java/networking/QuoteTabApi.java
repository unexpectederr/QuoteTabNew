package networking;

import models.authors.AuthorsByLetter;
import models.authors.PopularAuthors;
import models.quotes.Quotes;
import models.dashboard.DashboardData;
import models.quotes.TopQuotes;
import models.topics.Topics;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by unexpected_err on 17/10/2016.
 */

public interface QuoteTabApi {

    String ENDPOINT = "https://www.quotetab.com/";
    String BASE_URL = "api/v1.0/";

    @GET("api/v1.0")
    Call<DashboardData> getDashboardData();

    @GET(BASE_URL + "authors")
    Call<PopularAuthors> getAuthors();

    @GET(BASE_URL + "quotes/by-{authorID}/{page}")
    Call<Quotes> getQuotes(@Path("authorID") String authorID, @Path("page") int page);

    @GET(BASE_URL + "quotes/about-{tag}/{page}")
    Call<Quotes> getQuotesByTag(@Path("tag") String quoteTag, @Path("page") int page);

    @GET(BASE_URL + "topics")
    Call<Topics> getTopics();

    @GET(BASE_URL + "topics/{page}")
    Call<Topics> getTopics(@Path("page") int page);

    @GET(BASE_URL + "top-quotes")
    Call<TopQuotes> getTopQuotes();

    @GET(BASE_URL + "top-quotes/{page}")
    Call<TopQuotes> getTopQuotes(@Path("page") int page);

    @GET(BASE_URL + "authors/{letter}/{page}")
    Call<AuthorsByLetter> getAuthorsByLetter(@Path("letter") String letter, @Path("page") int page);

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    QuoteTabApi quoteTabApi = retrofit.create(QuoteTabApi.class);
}
