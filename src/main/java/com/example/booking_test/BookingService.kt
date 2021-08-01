package com.example.booking_test
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface BookingService {
    companion object{
        public val API_URL="http://f681e8ea4827.ngrok.io"
    }

    @GET("v1/booking")
    fun get_bookedList(@Query("format") json:String): Call<List<BookInfo>>

    @POST("v1/booking/")
    fun book_test(@Query("format") json:String, @Body book:BookInfo): Call<BookInfo>

    @DELETE("v1/booking/{id}/")
    fun delete_booking(@Path("id") id:Int, @Query("format") json:String):Call<BookInfo>
}