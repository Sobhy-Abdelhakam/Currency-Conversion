package dev.sobhy.bmproject.data.local

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.sobhy.bmproject.AppContext
import dev.sobhy.bmproject.data.model.Currency

@Database(entities = [Currency::class], version = 1)
abstract class CurrencyDatabase: RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao

    companion object{
        @Volatile // to make other threads immediately see when a thread changes this instance
        private var instance: CurrencyDatabase? = null
        private val LOCK = Any()
        // in invoke fun whenever we create that instance of CurrencyDB then return the current instance
        //and if it null we will set that instance in synchronized block
        operator fun invoke() = instance ?: synchronized(LOCK){
            // inside this block code can't be accessed by other threads at the same time
            // also set our instance to the result of our create database
            instance ?: createDatabase().also {
                instance = it
            } // then our instance of that database class will then be used to access our Currency Dao
        }

        private fun createDatabase() =
            Room.databaseBuilder(
                AppContext.appContext,
                CurrencyDatabase::class.java,
                "currency_db"
            ).build()
    }
}