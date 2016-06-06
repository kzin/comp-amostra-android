package onhands.com.deliverycompamostra.webservice;

import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by rodrigocavalcante on 5/30/16.
 */
public class WebService {

    private static WebService instance;
    private Retrofit retrofit;
    private WebServiceCall service;

    public WebServiceCall getService() {
        return service;
    }

    public static synchronized WebService getInstance() {
        if (instance == null) {
            instance = new WebService();
        }

        return instance;
    }

    public WebService() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        retrofit = new Retrofit.Builder()
                .baseUrl("https://www.fctradecenter.com/delivery/api/")
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .client(client)
                .build();

        service = retrofit.create(WebServiceCall.class);
    }
}
