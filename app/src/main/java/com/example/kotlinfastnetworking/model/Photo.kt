package com.example.kotlinretrofit.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.io.Serializable

/**
 * Created by nikhil.jadhav on 30-11-2017.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
open class Photo :Serializable{

        val id:String = ""
        val likes:Int = 0
        val favorites:Int =0
        val previewURL:String=""
        val webformatURL:String =""
}