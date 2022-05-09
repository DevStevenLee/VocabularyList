package com.example.vocabularylist.study_vocas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vocabularylist.R
import com.example.vocabularylist.adapters.StudyVocasListAdapter
import com.example.vocabularylist.vocas.VocaModel
import com.example.vocabularylist.vocas.Vocas

class VocasList : AppCompatActivity() {
    private lateinit var ivBackInStudyVocasList : ImageView
    private lateinit var tvStudyVocasList : TextView
    private lateinit var ivStudyVocasList : ImageView
    private lateinit var tvDefaultVocaList : TextView
    private lateinit var svVocaList : ScrollView
    private lateinit var rvPictureListOfTopics : RecyclerView

    private val vocas = Vocas()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.study_vocas_list)

        ivBackInStudyVocasList = findViewById(R.id.ivBackInStudyVocasList)
        tvStudyVocasList = findViewById(R.id.tvStudyVocasList)
        ivStudyVocasList = findViewById(R.id.ivStudyVocasList)

        tvDefaultVocaList = findViewById(R.id.tvDefaultVocaList)
        svVocaList = findViewById(R.id.svVocaList)
        rvPictureListOfTopics = findViewById(R.id.rvPictureListOfTopics)

        ivBackInStudyVocasList.setOnClickListener {
            onBackPressed()
        }

        setVocasList()
    }

    private fun setVocasList(){
        var vocaList : ArrayList<VocaModel> = ArrayList()
        var isCostomVoca = false

        when {
            intent.hasExtra(StudyVocas.ANIMAL_VOCA_KEY) -> {
                vocaList = vocas.getAnimalVocas()

                tvStudyVocasList.text = "동물"
                ivStudyVocasList.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.sun))
                supportActionBar?.title = "동물"
            }

            intent.hasExtra(StudyVocas.FLAG_VOCA_KEY) -> {
                vocaList = vocas.getFlagVocas()

                tvStudyVocasList.text = "나라"
                ivStudyVocasList.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.rainbow))
            }

            intent.hasExtra(StudyVocas.BODY_VOCA_KEY) -> {
                vocaList = vocas.getBodyVocas()

                tvStudyVocasList.text = "신체"
                ivStudyVocasList.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.airplane))
            }

            intent.hasExtra(StudyVocas.COSTOM_VOCA_KEY) -> {
                vocaList = vocas.getCustomVocas()

                isCostomVoca = true

                tvStudyVocasList.text = "나만의 단어"
                ivStudyVocasList.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.umbrella))
            }
        }

        if(vocaList.isNotEmpty()){
            tvDefaultVocaList.visibility = View.GONE
            svVocaList.visibility = View.VISIBLE

            val studyVocasListAdapter = StudyVocasListAdapter(this, vocaList)
            rvPictureListOfTopics.adapter = studyVocasListAdapter
            rvPictureListOfTopics.layoutManager = GridLayoutManager(this, 2)

            studyVocasListAdapter.setOnClickListener(object : StudyVocasListAdapter.OnClickListener{
                override fun onClick(nowIdx : Int) {
                    val voca = VocaCard(nowIdx, vocaList, vocas.getBookmarkVocas(), isCostomVoca)
                    voca.show(supportFragmentManager, "Voca_Card")
                }
            })
        } else{
            tvDefaultVocaList.visibility = View.VISIBLE
            svVocaList.visibility = View.GONE
        }
    }
}