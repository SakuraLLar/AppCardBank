package dev.sakura.bank.api

import dev.sakura.bank.api.models.BinInfo
import retrofit2.http.GET
import retrofit2.http.Path

interface BankApi {
    @GET("/{bin}")
    suspend fun getBinInfo(@Path("bin") bin: String): BinInfo
}
