package com.fatec.lddm_merge_skills.db

import com.fatec.lddm_merge_skills.db.dto.*
import com.fatec.lddm_merge_skills.model.Course
import com.fatec.lddm_merge_skills.repository.CourseRepository
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

class ExposedCourseRepository : CourseRepository {

    override suspend fun getAll(): List<Course> = newSuspendedTransaction {
        Courses.selectAll().map { it.toCourse() }
    }

    override suspend fun getById(id: Int): Course? = newSuspendedTransaction {
        Courses.selectAll()
            .where { Courses.id eq id }
            .map { it.toCourse() }
            .singleOrNull()
    }

    override suspend fun create(course: Course): Course = newSuspendedTransaction {
        val insertStatement = Courses.insert {
            course.toInsertDTO().applyTo(it)
        }
        insertStatement.resultedValues!!.first().toCourse()
    }

    override suspend fun update(id: Int, course: Course): Course = newSuspendedTransaction {
        Courses.update({ Courses.id eq id }) { course.toInsertDTO().applyTo(it) }
        getById(id) ?: course
    }

    override suspend fun delete(id: Int): Unit = newSuspendedTransaction {
        Courses.deleteWhere { Courses.id eq id }
    }
}