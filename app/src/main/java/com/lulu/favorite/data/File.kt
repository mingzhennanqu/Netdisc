package com.lulu.favorite.data

data class HomePageFile(
    var id: Int,
    var name: String,
    var file_type: String,
    var address: String
)

data class HomePageFileAll(
    var  code : String,
    var  msg : String,
    var  success: Boolean,
    var  header :String,
    var body : List<HomePageFile>,
    var  date : String
)
