package dev.sakura.appcardbank

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.sakura.bank.api.BankApi
import dev.sakura.bank.api.utils.BinApiClient
import dev.sakura.bank.data.BinRepository
import dev.sakura.bank.database.AppDatabase
import dev.sakura.bank.database.dao.BinHistoryDAO
import dev.sakura.bank_main.FetchBinInfoUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BankMainModule {

    @Provides
    @Singleton
    fun providerBankApi(): BankApi = BinApiClient.bankApi

    @Provides
    @Singleton
    fun provideBinRepository(api: BankApi, binHistoryDao: BinHistoryDAO): BinRepository =
        BinRepository(api, binHistoryDao)

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        )
            .build()

    @Provides
    @Singleton
    fun provideFetchBinInfoUseCase(repository: BinRepository): FetchBinInfoUseCase =
        FetchBinInfoUseCase(repository)

    @Provides
    @Singleton
    fun provideBinHistoryDao(appDatabase: AppDatabase): BinHistoryDAO =
        appDatabase.binHistoryDAO()
}
