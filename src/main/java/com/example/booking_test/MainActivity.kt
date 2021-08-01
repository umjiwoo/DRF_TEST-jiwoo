package com.example.booking_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import retrofit2.Retrofit
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory
import java.text.DateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    var retrofit = Retrofit.Builder().baseUrl(BookingService.API_URL).addConverterFactory(GsonConverterFactory.create()).build()
    var bookingService = retrofit.create(BookingService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        get.setOnClickListener {
            var comment = bookingService.get_bookedList("json")
            comment.enqueue(object : Callback<List<BookInfo>> {
                override fun onResponse(call: Call<List<BookInfo>>, response: Response<List<BookInfo>>) {
                    if (response.isSuccessful) {
                        var bookList=response.body()!!
                        bookListView.text=bookList.toString()
                        Log.e("test", bookList.toString())
                    }
                }
                override fun onFailure(call: Call<List<BookInfo>>, t: Throwable) {
                    Log.e("test","fail")
                }
            })
        }

        post.setOnClickListener {
            var data=BookInfo(Integer.parseInt(num.text.toString()),check_in.text.toString(),check_out.text.toString(),room_name.text.toString(),requirement.text.toString(),Calendar.getInstance().toString(),Calendar.getInstance().toString(),Integer.parseInt(subscriber.text.toString()))
            var comment=bookingService.book_test("json",data)

            comment.enqueue(object: Callback<BookInfo>{
                override fun onResponse(call: Call<BookInfo>, response: Response<BookInfo>) {
                    if(response.isSuccessful){
                        Log.e("book_test",data.id.toString()+" 예약 성공")
                    }
                    else{
                        val statusCode=response.code()
                        Log.e("book_test","상태 코드: $statusCode")
                    }
                }
                override fun onFailure(call: Call<BookInfo>, t: Throwable) {
                    Log.e("book_test",data.id.toString()+" 예약 실패")
               }
            })
        }

        delete.setOnClickListener {
            var id=Integer.parseInt(num.text.toString())
            var comment=bookingService.delete_booking(id,"json")
            comment.enqueue(object:Callback<BookInfo>{
                override fun onResponse(call: Call<BookInfo>, response: Response<BookInfo>) {
                    if(response.isSuccessful){
                        Log.e("delete_test",id.toString()+"번 예약 삭제 성공")
                    }
                    else{
                        val statusCode=response.code()
                        Log.e("delete_test","상태 코드: $statusCode")
                    }
                }

                override fun onFailure(call: Call<BookInfo>, t: Throwable) {
                    Log.e("delete_test","OnFailure+${t.message}")
                }
            })
        }
    }
}