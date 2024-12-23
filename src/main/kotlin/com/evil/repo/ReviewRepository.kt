package com.evil.repo

import com.evil.entity.Review
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.time.LocalDateTime

@Repository
interface ReviewRepository : JpaRepository<Review, Int> {
    @Query(nativeQuery = true, value = "select * from reviews r where date(r.created_at) = :date")
    fun findAllByCreatedAt(date: LocalDate): List<Review>

    @Query(
        nativeQuery = true,
        value = "select * from reviews_0 r where r.author = :author union all select * from reviews_1 r where r.author = :author"
    )
    fun findAllByAuthor(author: String): List<Review>
    fun findAllByCreatedAtBetween(startDateTime: LocalDateTime, endDateTime: LocalDateTime): List<Review>
    fun findAllByCourseId(courseId: Int): List<Review>
}
