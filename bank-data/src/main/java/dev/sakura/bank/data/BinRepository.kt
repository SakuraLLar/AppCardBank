package dev.sakura.bank.data

import android.os.Build
import androidx.annotation.RequiresExtension
import dev.sakura.bank.api.BankApi
import dev.sakura.bank.api.models.Bank
import dev.sakura.bank.api.models.BinInfo
import dev.sakura.bank.api.models.Country
import dev.sakura.bank.database.dao.BinHistoryDAO
import dev.sakura.bank.database.entity.BinHistoryEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BinRepository @Inject constructor(
    private val api: BankApi,
    private val binHistoryDAO: BinHistoryDAO,
) {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    suspend fun fetchBinInfo(bin: String): Result<BinInfo> {
        val cachedBin = binHistoryDAO.getByBin(bin)
        if (cachedBin != null) {
            return Result.success(
                BinInfo(
                    number = null,
                    scheme = cachedBin.scheme,
                    type = cachedBin.type,
                    brand = cachedBin.brand,
                    country = Country(name = cachedBin.country),
                    bank = Bank(name = cachedBin.bank),
                )
            )
        }

        return try {
            val response = api.getBinInfo(bin)

            binHistoryDAO.insert(
                BinHistoryEntity(
                    bin = bin,
                    scheme = response.scheme,
                    type = response.type,
                    brand = response.brand,
                    country = response.country?.name,
                    bank = response.bank?.name
                )
            )

            Result.success(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }

    }

    fun getHistory(): Flow<List<BinHistoryEntity>> {
        return binHistoryDAO.getAll()
    }

    suspend fun clearHistory() {
        binHistoryDAO.deleteAll()
    }
}
