package com.example.kotlinfastnetworking.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.ConnectionQuality
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interceptors.HttpLoggingInterceptor
import com.androidnetworking.interfaces.ParsedRequestListener
import com.example.kotlinfastnetworking.R
import com.example.kotlinfastnetworking.adapters.RvAdapter
import com.example.kotlinfastnetworking.model.PhotoList
import com.jacksonandroidnetworking.JacksonParserFactory
import java.util.concurrent.TimeUnit
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient


class MainActivity : AppCompatActivity(),View.OnClickListener{

    val TAG = MainActivity::class.java.simpleName!!
   lateinit var rvAdapter:RvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(tbMain as Toolbar)
        supportActionBar?.title = "kotlin fast networking"

        makeGetPhotosrequest()
    }

    private fun makeGetPhotosrequest() {
        AndroidNetworking.setParserFactory(JacksonParserFactory())

        val parsedReq = object: ParsedRequestListener<PhotoList>{
            override fun onError(anError: ANError?) {
                Log.d(TAG, anError.toString())
            }

            override fun onResponse(response: PhotoList?) {
                Log.d(TAG, response?.hits?.size.toString())
                val llm = LinearLayoutManager(this@MainActivity)
                llm.orientation = LinearLayout.VERTICAL
                rvMain.layoutManager =llm
                rvMain.adapter = RvAdapter(response?.hits,this@MainActivity)
            }

        }

      /*  val jsonListner: JSONObjectRequestListener = object : JSONObjectRequestListener {
            override fun onResponse(response: JSONObject?) {
                Log.d(TAG, response.toString())


            }

            override fun onError(anError: ANError?) {
                Log.d(TAG, anError.toString())
            }

        }*/

        //region This is used for logging the Retrofit request and response.
        val okHttpLog: HttpLoggingInterceptor = HttpLoggingInterceptor()
        okHttpLog.level = HttpLoggingInterceptor.Level.BODY
        //endregion

        val okHttpClient = OkHttpClient().newBuilder()
                .connectTimeout(2, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES)
                .writeTimeout(2, TimeUnit.MINUTES)
                .addInterceptor(okHttpLog)
                .build()



        AndroidNetworking.get("https://www.pixabay.com/api/?key=7029198-8bf9aba37d11fdec205b7403c&q=nature&image_type=photo")
                .setOkHttpClient(okHttpClient)
                .setPriority(Priority.HIGH)
                .build()
                .getAsObject(PhotoList::class.java,parsedReq)

        AndroidNetworking.setConnectionQualityChangeListener { currentConnectionQuality, currentBandwidth ->
            when (currentConnectionQuality) {
                ConnectionQuality.EXCELLENT -> {
                    Log.d(TAG, "Excellent")
                }

                ConnectionQuality.GOOD -> {
                    Log.d(TAG, "GOOD")
                }

                ConnectionQuality.MODERATE -> {
                    Log.d(TAG, "MODERATE")
                }

                ConnectionQuality.POOR -> {
                    Log.d(TAG, "POOR")
                }

                else -> {
                    Log.d(TAG, "Unknown")
                }
            }

            Log.d(TAG, "Bandwidth $currentBandwidth")

        }

    }

    override fun onClick(view: View?) {
        rvAdapter.getPhoto((view?.tag as RvAdapter.PhotoViewHolder).adapterPosition)
    }
}


