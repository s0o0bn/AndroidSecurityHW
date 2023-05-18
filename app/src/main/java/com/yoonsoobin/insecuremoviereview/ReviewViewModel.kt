package com.yoonsoobin.insecuremoviereview

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.yoonsoobin.insecuremoviereview.dao.ReviewDao
import com.yoonsoobin.insecuremoviereview.vo.ReviewItem
import java.lang.IllegalArgumentException

class ReviewViewModel(private val reviewDao: ReviewDao) : ViewModel() {
    fun getAll(username: String): LiveData<List<ReviewItem>> =
        reviewDao.getAll(username).asLiveData()
}

class ReviewViewModelFactory ( private val reviewDao: ReviewDao ) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReviewViewModel::class.java)) {
            return ReviewViewModel(reviewDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}