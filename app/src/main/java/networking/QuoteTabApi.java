package networking;

import models.authors.PopularAuthors;
import models.authors.Quotes;
import models.dashboard.DashboardData;
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

    @GET("api/v1.0")
    Call<DashboardData> getDashboardData();

    @GET("api/v1.0/authors")
    Call<PopularAuthors> getAuthors();

    @GET("api/v1.0/quotes/by-{authorID}")
    Call<Quotes> getQuotes(@Path("authorID") String authorID);

    @GET("api/v1.0/quotes/about-{tag}")
    Call<Quotes> getQuotesByTag(@Path("tag") String quoteTag);

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    QuoteTabApi quoteTabApi = retrofit.create(QuoteTabApi.class);
}
