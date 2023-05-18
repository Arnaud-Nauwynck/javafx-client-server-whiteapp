package fr.an.whiteapps.clisrvwhiteapp.clientjavafx.model;

import fr.an.whiteapps.clisrvwhiteapp.common.restapi.SampleRetrofitRestApi;
import fr.an.whiteapps.clisrvwhiteapp.common.restapi.dto.TestEchoRequestDTO;
import fr.an.whiteapps.clisrvwhiteapp.common.restapi.dto.TestEchoResponseDTO;
import lombok.extern.slf4j.Slf4j;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.IOException;
import java.util.function.Supplier;

/**
 * client helper for calling server http api
 * (using retrofit + logs)
 */
@Slf4j
public class SampleHttpClient {

    private final SampleRetrofitRestApi httpApi;

    //---------------------------------------------------------------------------------------------

    public SampleHttpClient(Retrofit retrofit) {
        this.httpApi = retrofit.create(SampleRetrofitRestApi.class);
    }

    public static SampleHttpClient createDefault(String serverBaseUrl) {
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl(serverBaseUrl);
        Retrofit retrofit = retrofitBuilder
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        return new SampleHttpClient(retrofit);
    }

    //---------------------------------------------------------------------------------------------

    public void healthCheck() {
        logAndExecuteHttpCall("GET", "/health-check", null,
                httpApi::healthCheck);
    }

    public TestEchoResponseDTO testEcho(TestEchoRequestDTO req) {
        return logAndExecuteHttpCall("PUT", "/test-echo", (req != null)? req.toString() : "null",
                () -> httpApi.testEcho(req));
    }

    //---------------------------------------------------------------------------------------------

    protected <T> T logAndExecuteHttpCall(String httpMethod, String httpPath,
                                          String optParamsMessage,
                                          Supplier<Call<T>> callProvider) {
        final String httpMsg = "http " + httpMethod + " " + httpPath + ((optParamsMessage != null)? optParamsMessage : "");
        Call<T> call = callProvider.get();
        Response<T> resp;
        try {
            log.info("call " + httpMsg);
            resp = call.execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (!resp.isSuccessful()) {
            String err = "Failed " + httpMsg + ", response:" + resp.code();
            try {
                try (ResponseBody responseBody = resp.errorBody()) {
                    if (responseBody != null) {
                        err += " : " + responseBody.string();
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            throw new RuntimeException(err);
        }
        return resp.body();
    }
}
