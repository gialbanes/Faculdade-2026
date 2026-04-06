package com.fatec.lddm_merge_skills.db.dto

import com.fatec.lddm_merge_skills.db.*
import com.fatec.lddm_merge_skills.model.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.statements.UpdateBuilder

// DTO: Course
@Serializable
data class CourseInsertDTO(
    val title: String,
    val description: String?,
    val icon: String?,
    val color: String?,
    @SerialName("total_lessons") val totalLessons: Int?
) {
    fun applyTo(builder: UpdateBuilder<*>) {
        builder[Courses.title] = title
        builder[Courses.description] = description
        builder[Courses.icon] = icon
        builder[Courses.color] = color
        builder[Courses.totalLessons] = totalLessons
    }
}

fun Course.toInsertDTO() = CourseInsertDTO(title, description, icon, color, totalLessons)

fun ResultRow.toCourse() = Course(
    id = this[Courses.id].value,
    title = this[Courses.title],
    description = this[Courses.description],
    icon = this[Courses.icon],
    color = this[Courses.color],
    totalLessons = this[Courses.totalLessons]
)

// DTO: Lesson
@Serializable
data class LessonInsertDTO(
    @SerialName("course_id") val courseId: Int,
    val title: String,
    val description: String?,
    val order: Int?
) {
    fun applyTo(builder: UpdateBuilder<*>) {
        builder[Lessons.courseId] = courseId
        builder[Lessons.title] = title
        builder[Lessons.description] = description
        builder[Lessons.order] = order
    }
}

fun Lesson.toInsertDTO() = LessonInsertDTO(courseId, title, description, order)

fun ResultRow.toLesson() = Lesson(
    id = this[Lessons.id].value,
    courseId = this[Lessons.courseId].value,
    title = this[Lessons.title],
    description = this[Lessons.description],
    order = this[Lessons.order]
)

// DTO: Question
@Serializable
data class QuestionInsertDTO(
    @SerialName("lesson_id") val lessonId: Int,
    val question: String,
    val code: String?,
    val options: List<String>,
    @SerialName("correct_answer") val correctAnswer: Int?,
    val order: Int?
) {
    fun applyTo(builder: UpdateBuilder<*>) {
        builder[Questions.lessonId] = lessonId
        builder[Questions.question] = question
        builder[Questions.code] = code
        builder[Questions.options] = options
        builder[Questions.correctAnswer] = correctAnswer
        builder[Questions.order] = order
    }
}

fun Question.toInsertDTO() = QuestionInsertDTO(lessonId, question, code, options, correctAnswer, order)

fun ResultRow.toQuestion() = Question(
    id = this[Questions.id].value,
    lessonId = this[Questions.lessonId].value,
    question = this[Questions.question],
    code = this[Questions.code],
    options = this[Questions.options],
    correctAnswer = this[Questions.correctAnswer],
    order = this[Questions.order]
)