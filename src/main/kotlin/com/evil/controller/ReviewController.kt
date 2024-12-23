package com.evil.controller

import com.evil.entity.Review
import com.evil.service.ReviewService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/api/v1/reviews")
class ReviewController(val reviewService: ReviewService) {

    @GetMapping
    fun getAllReviews(): ResponseEntity<List<Review>> {
        val reviews = reviewService.findAll()
        return ResponseEntity.ok(reviews)
    }

    @GetMapping("/filter", params = ["author"])
    fun getReviewsByAuthor(@RequestParam("author") author: String): ResponseEntity<List<Review>> {
        val reviews = reviewService.findAllByAuthor(author)
        return ResponseEntity.ok(reviews)
    }

    @GetMapping("/filter", params = ["courseId"])
    fun getReviewsByCourseId(@RequestParam("courseId") courseId: Int): ResponseEntity<List<Review>> {
        val reviews = reviewService.findAllByCourseId(courseId)
        return ResponseEntity.ok(reviews)
    }

    @GetMapping("/filter", params = ["date"])
    fun getReviewsByCreatedAt(@RequestParam("date") date: LocalDate): ResponseEntity<List<Review>> {
        val reviews = reviewService.findAllByCreatedAt(date)
        return ResponseEntity.ok(reviews)
    }

    @GetMapping("/{id}")
    fun getReviewById(@PathVariable("id") id: Int): ResponseEntity<Review> {
        val review = reviewService.findById(id)
        return ResponseEntity.ok(review)
    }

    @PostMapping
    fun createReview(@RequestBody review: Review): ResponseEntity<Review> {
        val savedReview = reviewService.save(review)
        return ResponseEntity.status(HttpStatus.CREATED).body(savedReview)
    }

    @DeleteMapping("/{id}")
    fun deleteReviewById(@PathVariable("id") id: Int): ResponseEntity<Unit> {
        reviewService.deleteById(id)
        return ResponseEntity.noContent().build()
    }

}
