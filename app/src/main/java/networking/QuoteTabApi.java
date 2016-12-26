package networking;

import models.dashboard.DashboardData;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by unexpected_err on 17/10/2016.
 */

public interface QuoteTabApi {

    @GET("api/v1.0")
    Call<DashboardData> getDashboardData();
}
