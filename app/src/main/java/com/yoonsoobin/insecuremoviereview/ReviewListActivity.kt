package com.yoonsoobin.insecuremoviereview

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.yoonsoobin.insecuremoviereview.databinding.ActivityReviewListBinding
import com.yoonsoobin.insecuremoviereview.util.ToastUtil

class ReviewListActivity : AppCompatActivity() {
    private val reviewViewModel: ReviewViewModel by viewModels() {
        ReviewViewModelFactory(
           (application as CustomApplication).database.reviewDao()
        )
    }
    private val adapter: ReviewAdapter by lazy {
        ReviewAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityReviewListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username: String? = intent.getStringExtra("username")
        ToastUtil.makeToast(this, String.format("로그인 사용자: %s", username)).show()
        binding.createReview.setOnClickListener { createReview(this, username) }

        val recyclerView = binding.reviewList
        recyclerView.apply {
            this.layoutManager = LinearLayoutManager(this@ReviewListActivity)
            this.adapter = this@ReviewListActivity.adapter
        }
        reviewViewModel.getAll(username.orEmpty()).observe(this) {
            adapter.submitList(it)
        }
    }

    private fun createReview(context: Context, username: String?) {
        val nextIntent = Intent(context, CreateReviewActivity::class.java)
        nextIntent.putExtra("username", username)
        startActivity(nextIntent)
    }
}