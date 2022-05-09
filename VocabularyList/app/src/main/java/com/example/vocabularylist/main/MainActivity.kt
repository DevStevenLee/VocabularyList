package com.example.vocabularylist.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.example.vocabularylist.R
import com.example.vocabularylist.add_new_vocabulary.AddNewVocabulary
import com.example.vocabularylist.bookmark.Bookmark
import com.example.vocabularylist.review_vocas.Review
import com.example.vocabularylist.study_vocas.StudyVocas


class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var ivMoveToStudyVocas : ImageView
    private lateinit var ivMoveToReviewVocas : ImageView
    private lateinit var ivMoveToBookmark : ImageView
    private lateinit var ivMoveToAddNewVocabulary : ImageView

    companion object{
        var mContext : Context? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ivMoveToStudyVocas = findViewById(R.id.ivMoveToStudyVocas)
        ivMoveToReviewVocas = findViewById(R.id.ivMoveToReviewVocas)
        ivMoveToBookmark = findViewById(R.id.ivMoveToBookmark)
        ivMoveToAddNewVocabulary = findViewById(R.id.ivMoveToAddNewVocabulary)

        ivMoveToStudyVocas.setOnClickListener(this)
        ivMoveToReviewVocas.setOnClickListener(this)
        ivMoveToBookmark.setOnClickListener(this)
        ivMoveToAddNewVocabulary.setOnClickListener(this)

        mContext = applicationContext
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.ivMoveToStudyVocas -> {
                val mIntent = Intent(this@MainActivity, StudyVocas::class.java)
                startActivity(mIntent)
            }

            R.id.ivMoveToReviewVocas -> {
                val mIntent = Intent(this@MainActivity, Review::class.java)
                mIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                startActivity(mIntent)
            }

            R.id.ivMoveToBookmark -> {
                val mIntent = Intent(this@MainActivity, Bookmark::class.java)
                startActivity(mIntent)
            }

            R.id.ivMoveToAddNewVocabulary -> {
                val mIntent = Intent(this@MainActivity, AddNewVocabulary::class.java)
                startActivity(mIntent)
            }
        }
    }
}