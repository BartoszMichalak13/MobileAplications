package com.example.spaceinvaders

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MyDao {

    @Query("SELECT * FROM highestscore")
    fun getAll(): List<HighestScore>


//    @Query("SELECT score FROM highestscore WHERE uid = (:playerId)")
//    fun getScorebyUid(playerId: Int): Int

    @Query("SELECT * FROM highestscore WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<HighestScore>

//    @Query("SELECT * FROM HighestScore WHERE first_name LIKE :first AND " +
//            "last_name LIKE :last LIMIT 1")
//    fun findByName(first: String, last: String): HighestScore

    @Query("SELECT (SELECT COUNT(*) FROM highestscore) == 0")
    fun isEmpty(): Boolean

    @Insert
    fun insertAll(vararg scores: HighestScore)

    @Query("UPDATE highestscore SET score=:price WHERE uid = :id")
    fun update(price: Int, id: Int)

    @Delete
    fun delete(scores: HighestScore)

}