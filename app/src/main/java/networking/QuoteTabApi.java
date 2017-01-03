package networking;

import models.authors.Authors;
import models.dashboard.DashboardData;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by unexpected_err on 17/10/2016.
 */

public interface QuoteTabApi {

    String ENDPOINT = "https://www.quotetab.com/";

    @GET("api/v1.0")
    Call<DashboardData> getDashboardData();

    @GET("api/v1.0/authors")
            Call<Authors> getAuthors();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    QuoteTabApi quoteTabApi = retrofit.create(QuoteTabApi.class);
}
