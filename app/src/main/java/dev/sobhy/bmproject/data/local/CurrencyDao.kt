package dev.sobhy.bmproject.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.sobhy.bmproject.data.model.Currency

@Dao
interface CurrencyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrency(currency: Currency)
    @Delete
    suspend fun deleteCurrency(currency: Currency)

    @Query("select * from currency")
    suspend fun getSavedCurrencies(): List<Currency>

    @Query("select * from currency where code = :code")
    suspend fun getCurrenciesByCode(code: String): Currency

    @Query("update currency set amount = 0")
    suspend fun setAmountZeroToAllRecord();
}