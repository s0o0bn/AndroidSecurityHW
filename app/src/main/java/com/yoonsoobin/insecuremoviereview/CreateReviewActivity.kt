package com.yoonsoobin.insecuremoviereview

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.RatingBar
import com.yoonsoobin.insecuremoviereview.dao.ReviewDao
import com.yoonsoobin.insecuremoviereview.databinding.ActivityCreateReviewBinding
import com.yoonsoobin.insecuremoviereview.entity.Review
import com.yoonsoobin.insecuremoviereview.util.ToastUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class CreateReviewActivity : AppCompatActivity() {
    private lateinit var reviewDao: ReviewDao
    private lateinit var ratingBar: RatingBar
    private lateinit var editTitle: EditText
    private lateinit var editContent: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityCreateReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        reviewDao = (application as CustomApplication).database.reviewDao()

        ratingBar = binding.movieRatingBar
        editTitle = binding.editMovieTitle
        editContent = binding.editReviewContent

        val username: String? = intent.getStringExtra("username")
        ToastUtil.makeToast(this, String.format("로그인한 사용자: %s", username)).show()
        binding.saveBtn.setOnClickListener { saveReview(this, username) }
    }

    private fun saveReview(context: Context, username: String?) {
        val title: String = editTitle.text.toString()
        val content: String = editContent.text.toString()
        val score: Float = ratingBar.rating

        if (title.isEmpty()) {
            ToastUtil.makeToast(context, "제목을 입력해 주세요").show()
        } else {
            CoroutineScope(IO).launch {
                val review = Review(0L, username.orEmpty(), title, score, content)
                reviewDao.create(review)
                finish()
            }
        }
    }
}