package onhands.com.deliverycompamostra.webservice;

import onhands.com.deliverycompamostra.response.CompanyResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * Created by rodrigocavalcante on 5/30/16.
 */
public interface WebServiceCall {

    @Headers("Api-Key : 92a0ec4919f6bd145f0aebdfde0b1111")
    @GET("company")
    Call<CompanyResponse> getCompanies();

}
