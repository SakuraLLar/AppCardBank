package dev.sakura.bank.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.sakura.bank.database.entity.BinHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BinHistoryDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(binHistoryEntity: BinHistoryEntity)

    @Query("SELECT * FROM bin_history ORDER BY timestamp DESC")
    fun getAll(): Flow<List<BinHistoryEntity>>

    @Query("DELETE FROM bin_history")
    suspend fun deleteAll()
}
