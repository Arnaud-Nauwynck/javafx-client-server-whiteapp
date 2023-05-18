package fr.an.whiteapps.clisrvwhiteapp.common.restapi;

import fr.an.whiteapps.clisrvwhiteapp.common.restapi.dto.TestEchoRequestDTO;
import fr.an.whiteapps.clisrvwhiteapp.common.restapi.dto.TestEchoResponseDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;

/**
 * interface for sample http client, using Retrofit library
 * (cf https://github.com/square/retrofit)
 */
public interface SampleRetrofitRestApi {

    static final String BASE = "/api/v1/endpoint";

    @GET(BASE + "/health-check")
    public Call<Void> healthCheck();

    /**
     * to check you can send-request/receive-response
     */
    @PUT(BASE + "/test-echo")
    public Call<TestEchoResponseDTO> testEcho(@Body TestEchoRequestDTO req);

}
