package com.example.vocabularylist.bookmark

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vocabularylist.R
import com.example.vocabularylist.adapters.BookmarkAdapter
import com.example.vocabularylist.adapters.StudyVocasListAdapter
import com.example.vocabularylist.study_vocas.VocaCard
import com.example.vocabularylist.vocas.VocaModel
import com.example.vocabularylist.vocas.Vocas

class Bookmark : AppCompatActivity() {
    private lateinit var ivBackInBookmark : ImageView
    private lateinit var tvDefaultBookmark : TextView
    private lateinit var svMarkedVocas : ScrollView
    private lateinit var rvPictureListOfBookmark : RecyclerView

    private val vocas = Vocas()
    private val bookmark = vocas.getBookmarkVocas()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bookmark)

        ivBackInBookmark = findViewById(R.id.ivBackInBookmark)
        tvDefaultBookmark = findViewById(R.id.tvDefaultBookmark)
        svMarkedVocas = findViewById(R.id.svMarkedVocas)
        rvPictureListOfBookmark = findViewById(R.id.rvPictureListOfBookmark)

        ivBackInBookmark.setOnClickListener {
            onBackPressed()
        }

        if(bookmark.isEmpty()){
            tvDefaultBookmark.visibility = View.VISIBLE
            svMarkedVocas.visibility = View.GONE
        } else{
            tvDefaultBookmark.visibility = View.GONE
            svMarkedVocas.visibility = View.VISIBLE
            setBookmarkVocas()
        }

    }

    private fun setBookmarkVocas(){
        val bookmarkAdapter = BookmarkAdapter(this, bookmark)
        rvPictureListOfBookmark.adapter = bookmarkAdapter
        rvPictureListOfBookmark.layoutManager = GridLayoutManager(this, 2)

        bookmarkAdapter.setOnClickListener(object : BookmarkAdapter.OnClickListener{
            override fun onClick(idx: Int) {
                val voca = VocaCard(idx, bookmark, vocas.getBookmarkVocas(), false)
                voca.show(supportFragmentManager, "Animal_Voca")
            }
        })
    }
}