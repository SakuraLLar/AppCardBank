package dev.sakura.bank_main

import dev.sakura.bank.api.models.BinInfo
import dev.sakura.bank.data.BinRepository
import javax.inject.Inject

class FetchBinInfoUseCase @Inject constructor(
    private val repository: BinRepository,
) {
    suspend operator fun invoke(bin: String): Result<BinInfo> {
        return repository.fetchBinInfo(bin)
    }

}
