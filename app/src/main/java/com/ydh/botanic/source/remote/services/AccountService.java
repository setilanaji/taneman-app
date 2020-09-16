package com.ydh.botanic.source.remote.services;

import com.ydh.botanic.models.body.BodyChangePassword;
import com.ydh.botanic.models.body.BodyRegistration;
import com.ydh.botanic.source.remote.responses.ChangePasswordResponse;
import com.ydh.botanic.source.remote.responses.LoginResponse;
import com.ydh.botanic.source.remote.responses.RegistrationResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AccountService {

    @POST("check_if_account_exists/")
    Call<Void> checkAccount(@Query("email") String email);

    @POST("register/")
    Call<RegistrationResponse> accountRegister(@Body BodyRegistration bodyRegistration);

    @PATCH("change_password/")
    Call<ChangePasswordResponse> changePassword( @Body BodyChangePassword bodyChangePassword);

    @FormUrlEncoded
    @POST("login/")
    Call<LoginResponse> login(@Field("username") String username, @Field("password") String password);

    @GET("properties/")
    Call<Void> getProperties(@Header("Authorization") String token);


}
