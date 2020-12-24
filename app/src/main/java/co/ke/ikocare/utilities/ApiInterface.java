package co.ke.ikocare.utilities;

import java.util.List;

import co.ke.ikocare.models.auth.Register;
import co.ke.ikocare.models.auth.VerifyCode;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("register")
    Call<Register> register(@Field("first_name") String first_name, @Field("last_name") String last_name,
                            @Field("email") String email, @Field("phone_number") String phone_number,
                            @Field("pin") String pin);

    @FormUrlEncoded
    @POST("verificationcode")
    Call<SendCode> sendcode(@Field("phone_number") String phone_number);

    @FormUrlEncoded
    @POST("verifycode")
    Call<VerifyCode> verifycode(@Field("phone_number") String phone_number, @Field("code") String code);

    @FormUrlEncoded
    @POST("login")
    Call<Register> login(@Field("phone_number") String phone_number, @Field("pin") String pin);

}
