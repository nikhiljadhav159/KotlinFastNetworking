package com.example.kotlinfastnetworking.model

import com.example.kotlinretrofit.model.Photo
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

/**
 * Created by nikhil.jadhav on 30-11-2017.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
class PhotoList {
        val hits:List<Photo> = emptyList()
}