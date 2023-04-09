package com.example.hackathon2;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface OpenAIApi {
    @Headers("Content-Type: application/json")
    @POST("https://api.openai.com/v1/models/text-davinci-002/completions")
    Call<GptResponse> generateResponse(@Body GptRequest request);
}
