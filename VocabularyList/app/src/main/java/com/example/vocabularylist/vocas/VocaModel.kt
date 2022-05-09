package com.example.vocabularylist.vocas

data class VocaModel(
        var id : Int,
        val word : String,
        val src : Int,
        val srcPath : String,
        var isBookmarkClicked : Boolean
)