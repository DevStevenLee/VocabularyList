package com.example.vocabularylist.review_vocas

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.vocabularylist.R
import com.example.vocabularylist.vocas.VocaModel
import com.example.vocabularylist.vocas.Vocas
import java.util.*
import kotlin.collections.ArrayList

class Review : AppCompatActivity(), View.OnClickListener {
    private lateinit var ivBackInReview : ImageView
    private lateinit var ivVocaImageInReview : ImageView
    private lateinit var progressBarInReview : ProgressBar
    private lateinit var tvProgressBarInReview : TextView
    private lateinit var tvOptionOne : TextView
    private lateinit var tvOptionTwo : TextView
    private lateinit var tvOptionThree : TextView
    private lateinit var tvOptionFour : TextView
    private lateinit var btSubmitInReview : TextView

    private var voca = Vocas()
    private var animalVocas = voca.getAnimalVocas()
    private var bodyVocas = voca.getBodyVocas()
    private var flagVocas = voca.getFlagVocas()
    private var customVocas = voca.getCustomVocas()

    private var questions = ArrayList<VocaModel>()
    private var options = ArrayList<TextView>()

    private var mCurrentQuestion = 1
    private var clickedOption = -1
    private var countCorrect = 0

    private val nextQuestion = "다음"
    private val submitAnswer = "제출하기"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)

        ivBackInReview = findViewById(R.id.ivBackInReview)
        ivVocaImageInReview = findViewById(R.id.ivVocaImageInReview)
        progressBarInReview = findViewById(R.id.progressBarInReview)
        tvProgressBarInReview = findViewById(R.id.tvProgressBarInReview)
        tvOptionOne = findViewById(R.id.tvOptionOne)
        tvOptionTwo = findViewById(R.id.tvOptionTwo)
        tvOptionThree = findViewById(R.id.tvOptionThree)
        tvOptionFour = findViewById(R.id.tvOptionFour)
        btSubmitInReview = findViewById(R.id.btSubmitInReview)

        ivVocaImageInReview.setBackgroundResource(R.drawable.rounded_rectangle_for_voca)
        ivVocaImageInReview.clipToOutline = true

        options.add(tvOptionOne)
        options.add(tvOptionTwo)
        options.add(tvOptionThree)
        options.add(tvOptionFour)

        ivBackInReview.setOnClickListener {
            onBackPressed()
        }

        setQuestions()
        setContent()

        for(i : Int in 0 until options.size){
            options[i].setOnClickListener(this)
        }
        btSubmitInReview.setOnClickListener(this)
    }
    private fun selectedOptionView(v: TextView, newClickedOption : Int){
        for(i : Int in 0 until options.size){
            options[i].background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
        }
        btSubmitInReview.text = submitAnswer

        clickedOption = newClickedOption
        v.background = ContextCompat.getDrawable(this, R.drawable.selected_option_border_bg)
    }

    private fun setDefaultOptions(){
        for(i : Int in 0 until options.size){
            options[i].background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.tvOptionOne -> { selectedOptionView(tvOptionOne, 0) }
            R.id.tvOptionTwo -> { selectedOptionView(tvOptionTwo, 1) }
            R.id.tvOptionThree -> { selectedOptionView(tvOptionThree, 2) }
            R.id.tvOptionFour -> { selectedOptionView(tvOptionFour, 3) }
            R.id.btSubmitInReview -> {
                if(btSubmitInReview.text.toString() == nextQuestion){
                    mCurrentQuestion++

                    if(mCurrentQuestion > 10){
                        val reviewResult = ReviewResult(countCorrect)
                        reviewResult.show(supportFragmentManager, "Review_Result")
                    } else {
                        btSubmitInReview.text = submitAnswer
                        clickedOption = -1
                        setDefaultOptions()
                        setContent()
                    }
                }
                else if(clickedOption != -1){
                    setDefaultOptions()

                    if(options[clickedOption].text.toString() == questions[mCurrentQuestion-1].word){
                        options[clickedOption].background = ContextCompat.getDrawable(this, R.drawable.correct_option_border_bg)
                        countCorrect++
                    } else {
                        options[clickedOption].background = ContextCompat.getDrawable(this, R.drawable.wrong_option_border_bg)
                    }

                    btSubmitInReview.text = nextQuestion
                }
            }
        }
    }

    private fun setContent(){
        progressBarInReview.progress = mCurrentQuestion
        tvProgressBarInReview.text = "${mCurrentQuestion}/10"

        if(questions[mCurrentQuestion-1].srcPath.isEmpty()){
            ivVocaImageInReview.setImageDrawable(ContextCompat.getDrawable(this, questions[mCurrentQuestion-1].src))
        } else {
            ivVocaImageInReview.setImageURI(Uri.parse(questions[mCurrentQuestion-1].srcPath))
        }

        setOptions()
    }

    private fun setOptions(){
        val answer = questions[mCurrentQuestion-1].word
        val optionsList = ArrayList<String>()

        while(optionsList.size < 4){
            when((1..4).random()){
                1 -> {
                    if(animalVocas.isNotEmpty()){
                        val randNum = Random().nextInt(animalVocas.size)
                        optionsList.add(animalVocas[randNum].word)
                        animalVocas.removeAt(randNum)
                    }
                }
                2 -> {
                    if(bodyVocas.isNotEmpty()){
                        val randNum = Random().nextInt(bodyVocas.size)
                        optionsList.add(bodyVocas[randNum].word)
                        bodyVocas.removeAt(randNum)
                    }
                }
                3 -> {
                    if(flagVocas.isNotEmpty()){
                        val randNum = Random().nextInt(flagVocas.size)
                        optionsList.add(flagVocas[randNum].word)
                        flagVocas.removeAt(randNum)
                    }
                }
                4 -> {
                    if(customVocas.isNotEmpty()){
                        val randNum = Random().nextInt(customVocas.size)
                        optionsList.add(customVocas[randNum].word)
                        customVocas.removeAt(randNum)
                    }
                }
            }
        }

        optionsList.shuffle()
        val rand = (0..3).random()
        optionsList[rand] = answer

        for(i : Int in 0 until options.size) {
            options[i].text = optionsList[i]
        }
    }

    private fun setQuestions(){
        while(questions.size < 10){
            when((1..4).random()){
                1 -> {
                    if(animalVocas.isNotEmpty()){
                        val randNum = Random().nextInt(animalVocas.size)
                        questions.add(animalVocas[randNum])
                        animalVocas.removeAt(randNum)
                    }
                }
                2 -> {
                    if(bodyVocas.isNotEmpty()){
                        val randNum = Random().nextInt(bodyVocas.size)
                        questions.add(bodyVocas[randNum])
                        bodyVocas.removeAt(randNum)
                    }
                }
                3 -> {
                    if(flagVocas.isNotEmpty()){
                        val randNum = Random().nextInt(flagVocas.size)
                        questions.add(flagVocas[randNum])
                        flagVocas.removeAt(randNum)
                    }
                }
                4 -> {
                    if(customVocas.isNotEmpty()){
                        val randNum = Random().nextInt(customVocas.size)
                        questions.add(customVocas[randNum])
                        customVocas.removeAt(randNum)
                    }
                }
            }
        }

    }
}