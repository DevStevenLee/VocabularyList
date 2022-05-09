package com.example.vocabularylist.review_vocas

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.vocabularylist.R
import com.example.vocabularylist.main.MainActivity

class ReviewResult(
        private val countCorrect: Int)  : DialogFragment(), View.OnClickListener {

    private lateinit var tvResultInReview : TextView
    private lateinit var tvDetailOfResultInReview : TextView
    private lateinit var ivBackInReviewResult : ImageView
    private lateinit var ivHomeInReviewResult : ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.review_result, container, false)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        tvResultInReview = view.findViewById(R.id.tvResultInReview)
        tvDetailOfResultInReview = view.findViewById(R.id.tvDetailOfResultInReview)
        ivBackInReviewResult = view.findViewById(R.id.ivBackInReviewResult)
        ivHomeInReviewResult = view.findViewById(R.id.ivHomeInReviewResult)

        when {
            countCorrect == 10 -> {
                tvResultInReview.text = "훌륭합니다!"
            } countCorrect >= 5 -> {
                tvResultInReview.text = "잘했습니다!"
            } else -> {
                tvResultInReview.text = "노력이 필요합니다!"
            }
        }

        tvDetailOfResultInReview.text = "10개 중 ${countCorrect}개 맞췄습니다."

        ivBackInReviewResult.setOnClickListener(this)
        ivHomeInReviewResult.setOnClickListener(this)

        return view
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.ivBackInReviewResult -> {
                val mIntent = Intent(requireContext(), Review::class.java)
                mIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)

                startActivity(mIntent)
            }

            R.id.ivHomeInReviewResult -> {
                val mIntent = Intent(requireContext(), MainActivity::class.java)
                mIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)

                startActivity(mIntent)
            }
        }
    }
}