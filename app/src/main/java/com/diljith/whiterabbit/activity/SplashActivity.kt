package com.diljith.whiterabbit.activity

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import com.diljith.whiterabbit.R
import com.diljith.whiterabbit.database.DBOpenHelper
import com.diljith.whiterabbit.model.ApiResponse
import com.diljith.whiterabbit.network.ApiInterFace
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash)
        supportActionBar!!.hide()
        loadData()
    }

    private fun loadData() {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)
        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(3, TimeUnit.MINUTES)
            .connectTimeout(3, TimeUnit.MINUTES)
            .writeTimeout(3, TimeUnit.MINUTES)
            .addInterceptor(logging)
            .build()
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://www.mocky.io/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        val request: ApiInterFace = retrofit.create(ApiInterFace::class.java)
        val call: Call<ApiResponse> = request.getTodoListApi()
        call.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                val intent = Intent(this@SplashActivity, MainActivity::class.java)
                val dbHelper = DBOpenHelper(this@SplashActivity, null)
                response.body()?.let {
                    dbHelper.insertToDoData(it)
                    startActivity(intent)
                    finish()
                }


            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                println("onFAIL::: $t")
                Toast.makeText(this@SplashActivity, "Something went wrong", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }
}