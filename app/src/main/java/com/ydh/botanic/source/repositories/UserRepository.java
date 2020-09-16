package com.ydh.botanic.source.repositories;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.ydh.botanic.models.ErrorMessage;
import com.ydh.botanic.models.ResponseModel;
import com.ydh.botanic.models.body.BodyChangePassword;
import com.ydh.botanic.models.body.BodyLogin;
import com.ydh.botanic.models.body.BodyRegistration;
import com.ydh.botanic.source.remote.responses.ChangePasswordResponse;
import com.ydh.botanic.source.remote.responses.LoginResponse;
import com.ydh.botanic.source.remote.responses.RegistrationResponse;
import com.ydh.botanic.source.remote.services.AccountService;
import com.ydh.botanic.utils.ApiUtil;
import com.ydh.botanic.utils.UserSessionManager;
import com.ydh.botanic.utils.singleton.SingletonAccessToken;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository extends BaseRepository{

    private AccountService accountService;

    private Context context;

    public UserRepository(Context context) {
        accountService = ApiUtil.getAccountService();
        this.context = context;
    }


    public LiveData<ResponseModel<RegistrationResponse>> postUserRegister (BodyRegistration bodyRegistration) {
        final MutableLiveData<ResponseModel<RegistrationResponse>> data = new MutableLiveData<>();
        accountService.accountRegister(bodyRegistration)
                .enqueue(new Callback<RegistrationResponse>() {
                    @Override
                    public void onResponse(Call<RegistrationResponse> call, Response<RegistrationResponse> response) {
                        ResponseModel<RegistrationResponse> responseModel = new ResponseModel<>();
                        if((response.code() == SUCCESS_CODE)) {
                            responseModel.setResponse(response.body());
//                            SingletonAccessToken.setAccessTokenReceived(responseModel.getData().getToken());
                            responseModel.setCode(response.code());
                        } else{
                            if(response.errorBody() != null){
                                responseModel.setErrorMessage(serializeErrorBody(response.errorBody()));
                            } else {
                                ErrorMessage errorMessage = new ErrorMessage();
                                errorMessage.setKey(UNEXPECTED_ERROR_KEY);
                                errorMessage.setError_message(UNEXPECTED_ERROR_MESSAGE);
                                responseModel.setErrorMessage(errorMessage);
                            }
                        }
                        data.setValue(responseModel);
                    }
                    @Override
                    public void onFailure(Call<RegistrationResponse> call, Throwable t) {
                        data.setValue(null);
                    }
                });
        return data;
    }

    public LiveData<ResponseModel<LoginResponse>> postUserLogin (BodyLogin bodyLogin) {
        final MutableLiveData<ResponseModel<LoginResponse>> data = new MutableLiveData<>();
        accountService.login(bodyLogin.getUsername(), bodyLogin.getPassword())
                .enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        ResponseModel<LoginResponse> responseModel = new ResponseModel<>();
                        if((response.code() == SUCCESS_CODE)) {
                            Log.e("TAG", "response 33: "+new Gson().toJson(response.body()) );
                            responseModel.setResponse(response.body());

                            UserSessionManager.setLoggedInKeyEmail(context, responseModel.getResponse().getEmail());
                            UserSessionManager.setLoggedInKeyToken(context, responseModel.getResponse().getToken());
                            UserSessionManager.setLoggedInUser(context, responseModel.getResponse().getUsername());
                            UserSessionManager.setLoggedInStatus(context,true);

                            SingletonAccessToken.setAccessTokenReceived(UserSessionManager.getLoggedInKeyToken(context));

                            responseModel.setCode(response.code());
                        } else{
                            if(response.errorBody() != null){
                                responseModel.setErrorMessage(serializeErrorBody(response.errorBody()));
                            } else {
                                ErrorMessage errorMessage = new ErrorMessage();
                                errorMessage.setKey(UNEXPECTED_ERROR_KEY);
                                errorMessage.setError_message(UNEXPECTED_ERROR_MESSAGE);
                                responseModel.setErrorMessage(errorMessage);
                            }
                        }
                        data.setValue(responseModel);
                    }
                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        data.setValue(null);
                    }
                });
        return data;
    }

    public LiveData<ResponseModel<ChangePasswordResponse>> postChangePassword (String token, BodyChangePassword bodyChangePassword) {
        final MutableLiveData<ResponseModel<ChangePasswordResponse>> data = new MutableLiveData<>();
        accountService.changePassword(bodyChangePassword)
                .enqueue(new Callback<ChangePasswordResponse>() {
                    @Override
                    public void onResponse(Call<ChangePasswordResponse> call, Response<ChangePasswordResponse> response) {
                        ResponseModel<ChangePasswordResponse> responseModel = new ResponseModel<>();
                        if((response.code() == SUCCESS_CODE)) {
                            Log.e("TAG", "response 33: "+new Gson().toJson(response.body()) );
                            responseModel.setResponse(response.body());
                            responseModel.getResponse().getActive();


//                            UserSessionManager.setLoggedInKeyEmail(context, responseModel.getResponse().getEmail());
//                            UserSessionManager.setLoggedInKeyToken(context, responseModel.getResponse().getToken());
//                            UserSessionManager.setLoggedInUser(context, responseModel.getResponse().getUsername());
//                            UserSessionManager.setLoggedInStatus(context,true);

//                            SingletonAccessToken.setAccessTokenReceived(UserSessionManager.getLoggedInKeyToken(context));

                            responseModel.setCode(response.code());
                        } else{
                            System.out.println("No response");
                            if(response.errorBody() != null){
                                responseModel.setErrorMessage(serializeErrorBody(response.errorBody()));
                            } else {
                                ErrorMessage errorMessage = new ErrorMessage();
                                errorMessage.setKey(UNEXPECTED_ERROR_KEY);
                                errorMessage.setError_message(UNEXPECTED_ERROR_MESSAGE);
                                responseModel.setErrorMessage(errorMessage);
                            }
                        }
                        data.setValue(responseModel);
                    }

                    @Override
                    public void onFailure(Call<ChangePasswordResponse> call, Throwable t) {
                        data.setValue(null);

                    }

                });
        return data;
    }
}
