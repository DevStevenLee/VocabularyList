package com.example.vocabularylist.study_vocas

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.vocabularylist.R
import com.example.vocabularylist.vocas.VocaModel
import com.example.vocabularylist.vocas.Vocas
import java.util.*
import kotlin.collections.ArrayList

class VocaCard(
        private val idx: Int,
        private val vocaList: ArrayList<VocaModel>,
        private val bookmarks: ArrayList<VocaModel>,
        private val isCostomVoca: Boolean)
    : DialogFragment(), View.OnClickListener {

    private lateinit var tvDeleteVoca : TextView
    private lateinit var ivStudyCloseVoca : ImageView
    private lateinit var ivStudyVoca : ImageView
    private lateinit var ivAddDeleteStarButton : ImageView
    private lateinit var ivLeftButtonInVocaCard : ImageView
    private lateinit var ivRightButtonInVocaCard : ImageView
    private lateinit var tvStudyVoca : TextView
    private lateinit var ivWordSpeech : ImageView

    private var tts: TextToSpeech? = null

    private val vocas = Vocas()
    private var voca : VocaModel = vocaList[idx]
    private var nowIdx : Int = idx

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.voca_card, container,false)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        tvDeleteVoca = view.findViewById(R.id.tvDeleteVoca)
        ivStudyCloseVoca = view.findViewById(R.id.ivStudyCloseVoca)
        ivStudyVoca = view.findViewById(R.id.ivStudyVoca)
        ivAddDeleteStarButton = view.findViewById(R.id.ivAddDeleteStarButton)
        ivLeftButtonInVocaCard = view.findViewById(R.id.ivLeftButtonInVocaCard)
        ivRightButtonInVocaCard = view.findViewById(R.id.ivRightButtonInVocaCard)
        tvStudyVoca = view.findViewById(R.id.tvStudyVoca)
        ivWordSpeech = view.findViewById(R.id.ivWordSpeech)

        setVocas()

        tts = TextToSpeech(requireContext()){
            if(it == TextToSpeech.SUCCESS){
                val result = tts?.setLanguage(Locale.KOREAN)
                if(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                    Toast.makeText(requireContext(), "Language not supported", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "TTS init failed", Toast.LENGTH_SHORT).show()
            }
        }

        tvDeleteVoca.setOnClickListener(this)
        ivStudyCloseVoca.setOnClickListener(this)
        ivAddDeleteStarButton.setOnClickListener(this)
        ivLeftButtonInVocaCard.setOnClickListener(this)
        ivRightButtonInVocaCard.setOnClickListener(this)
        ivWordSpeech.setOnClickListener(this)

        return view
    }

    private fun setVocas(){
        if(isCostomVoca){
            tvDeleteVoca.visibility = View.VISIBLE
        } else {
            tvDeleteVoca.visibility = View.GONE
        }

        if(voca.srcPath.isEmpty()){
            ivStudyVoca.setImageDrawable(ContextCompat.getDrawable(requireContext(), voca.src))
        } else {
            ivStudyVoca.setImageURI(Uri.parse(voca.srcPath))
        }

        tvStudyVoca.text = voca.word


        for(bookmark in bookmarks){
            if(voca.srcPath.isEmpty() && voca.src == bookmark.src){
                voca.id = bookmark.id
                voca.isBookmarkClicked = true
                break
            } else if(voca.srcPath.isNotEmpty() && voca.srcPath == bookmark.srcPath){
                voca.id = bookmark.id
                voca.isBookmarkClicked = true
                break
            }
        }

        if(voca.isBookmarkClicked){
            ivAddDeleteStarButton.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_star_24))
        } else {
            ivAddDeleteStarButton.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_star_border_24))
        }
    }

    private fun speak(str: String){
        tts?.speak(str, TextToSpeech.QUEUE_FLUSH, null, null)
    }


    override fun onClick(v: View?) {
        when(v?.id){
            R.id.tvDeleteVoca -> {
                vocas.bookmarkDatabaseHandler.deleteVoca(voca)
                vocas.customDatabaseHandler.deleteVoca(voca)
                
                dismiss()
            }

            R.id.ivStudyCloseVoca -> {
                dismiss()
            }

            R.id.ivAddDeleteStarButton -> {

                if(voca.isBookmarkClicked){
                    voca.isBookmarkClicked = !voca.isBookmarkClicked

                    ivAddDeleteStarButton.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_star_border_24))

                    vocas.bookmarkDatabaseHandler.deleteVoca(voca)

                    voca.id = 0
                } else {
                    voca.isBookmarkClicked = !voca.isBookmarkClicked

                    ivAddDeleteStarButton.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_star_24))

                    vocas.bookmarkDatabaseHandler.addVoca(voca)
                }
            }

            R.id.ivLeftButtonInVocaCard -> {
                nowIdx--
                if(nowIdx <= 0) { nowIdx = vocaList.size - 1 }
                voca = vocaList[nowIdx]

                setVocas()
            }

            R.id.ivRightButtonInVocaCard -> {
                nowIdx++
                if(nowIdx >= vocaList.size) { nowIdx = 0 }
                voca = vocaList[nowIdx]

                setVocas()
            }

            R.id.ivWordSpeech -> {
                speak(voca.word)
            }
        }
    }
}