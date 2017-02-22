package networking;

import models.authors.AuthorDetails;
import models.authors.Authors;
import models.authors.PopularAuthors;
import models.dashboard.DashboardData;
import models.images.ImageSuggestion;
import models.quotes.Quotes;
import models.quotes.TopQuotes;
import models.search.SearchResponse;
import models.topics.Topics;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by unexpected_err on 17/10/2016.
 */

public interface QuoteTabApi {

    String BASE_URL = "https://www.quotetab.com/";
    String ENDPOINT = "api/v1.0/";
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    QuoteTabApi quoteTabApi = retrofit.create(QuoteTabApi.class);

    @GET("api/v1.0")
    Call<DashboardData> getDashboardData();

    @GET(ENDPOINT + "authors")
    Call<PopularAuthors> getAuthors();

    @GET(ENDPOINT + "quotes/by-{authorID}/{page}")
    Call<Quotes> getQuotes(@Path("authorID") String authorID, @Path("page") int page);

    @GET(ENDPOINT + "quotes/about-{tag}/{page}")
    Call<Quotes> getQuotesByTag(@Path("tag") String quoteTag, @Path("page") int page);

    @GET(ENDPOINT + "topics")
    Call<Topics> getTopics();

    @GET(ENDPOINT + "topics/{page}")
    Call<Topics> getTopics(@Path("page") int page);

    @GET(ENDPOINT + "top-quotes")
    Call<TopQuotes> getTopQuotes();

    @GET(ENDPOINT + "top-quotes/{page}")
    Call<TopQuotes> getTopQuotes(@Path("page") int page);

    @GET(ENDPOINT + "authors/{letter}/{page}")
    Call<Authors> getAuthorsByLetter(@Path("letter") String letter, @Path("page") int page);

    @GET("photo-quote/images/load")
    Call<ImageSuggestion> getImageSugguestions(@Query("query") String query);

    @GET(ENDPOINT + "search")
    Call<SearchResponse> getSearchResults(@Query("authors") boolean authors,
                                               @Query("quotes") boolean quotes,
                                               @Query("topics") boolean topics,
                                               @Query("q") String query);

}
