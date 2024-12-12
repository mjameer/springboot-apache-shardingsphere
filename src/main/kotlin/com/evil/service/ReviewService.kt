package com.evil.service

import com.evil.entity.Review
import com.evil.exceptions.NotFoundException
import com.evil.repo.ReviewRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ReviewService(val reviewRepository: ReviewRepository) {

    companion object {
        private val log: Logger = LoggerFactory.getLogger(this::class.java)
    }

    fun findAll(): List<Review> {
        return reviewRepository.findAll()
    }

    fun findAllByAuthor(author: String): List<Review> {
        return reviewRepository.findAllByAuthor(author)
    }

    fun findAllByCourseId(courseId: Int): List<Review> {
        return reviewRepository.findAllByCourseId(courseId)
    }

    fun findAllByCreatedAt(date: LocalDate): List<Review> {
        return reviewRepository.findAllByCreatedAt(date)
    }

    fun findById(id: Int): Review {
        return reviewRepository.findById(id).orElseThrow { NotFoundException(Review::class, "id", id.toString()) }
    }

    fun save(review: Review): Review {
        return reviewRepository.save(review)
    }

    fun deleteById(id: Int) = reviewRepository.deleteById(id)


}
