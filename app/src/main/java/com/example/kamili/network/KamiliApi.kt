package com.example.kamili.network

import com.example.kamili.data.*
import retrofit2.http.*

interface KamiliApi {

    @GET("kamili.api.kamili_data.get_kamili_services")
    suspend fun getKamiliServices(): ServicesResponse

    @GET("kamili.api.kamili_data.get_house_sizes")
    suspend fun getKamiliHouseSizes(): HouseSizeResponse

    @GET("kamili.api.kamili_data.get_kamili_reviews")
    suspend fun getKamiliReviews(): ReviewsResponse

    @GET("kamili.api.kamili_data.get_kamili_articles")
    suspend fun getKamiliArticles(): ArticlesResponse

    @GET("kamili.api.kamili_data.get_kamili_bookings")
    suspend fun getKamiliBookings(@Query("email") email: String): BookingResponse


//        "Authorization: token 080c752d0b757ef:f2719b37264922f",
//    "X-Frappe-CSRF-Token: f6f447c16cdfc219c5381b4b59f9d64e2c98f359ae299326cf7b2f50")

    @FormUrlEncoded
    @POST("kamili.api.kamili_data.create_booking")
    suspend fun createKamiliBooking(
        @Field("client") client: String,
        @Field("house_size") house_size: String,
        @Field("location") location: String,
//        @Field("services[]") services: List<String>,
//        @Field("date") date: String,
//        @Field("time") time: String,
//        @Field("additional_info") additional_info: String,
//        @Field("status") status: String,
//        @Field("price") price: Int,
//        @Field("review_score") review_score: Int,
//        @Field("review_comment") review_comment: String,
    ): KamiliResponse

    @POST("kamili.api.kamili_data.login_request")
    @FormUrlEncoded
    suspend fun processLogin(@Field("email") email: String,@Field("password") password: String): KamiliResponse


    @POST("kamili.api.kamili_data.signin_request")
    @FormUrlEncoded
    suspend fun processSignin(@Field("fname") fname: String,@Field("sname") sname: String,@Field("email") email: String,@Field("password") password: String): KamiliResponse


}