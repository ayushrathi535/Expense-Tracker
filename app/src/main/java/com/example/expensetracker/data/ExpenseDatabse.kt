package com.example.expensetracker.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.expensetracker.data.converter.DateConverter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Database(entities = [ExpenseEntity::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class ExpenseDatabase : RoomDatabase() {

    abstract fun expenseDao(): ExpenseDao

    companion object {

        const val DATABASE_NAME = "expense_db"

        @JvmStatic
        fun getDatabase(context: Context): ExpenseDatabase {
            return Room.databaseBuilder(
                context,
                ExpenseDatabase::class.java,
                DATABASE_NAME
            )
//                .addCallback(object : Callback() {
//                override fun onCreate(db: SupportSQLiteDatabase) {
//                    super.onCreate(db)
//                    CoroutineScope(Dispatchers.IO).launch {
//                        initLoadData(context)
//                    }
//                }
//            })
                .build()
        }

    private suspend fun initLoadData(context: Context) {
        val expenseDao = getDatabase(context).expenseDao()
       // val currentTime = System.currentTimeMillis()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
     //   val currentDate = dateFormat.format(Date(currentTime))

        val currentDate= Date()

        expenseDao.insertExpense(
            ExpenseEntity(
                id = 1,
                title = "Netflix",
                amount = 340.40,
                date = currentDate,
                category = "SCHOOL",
                accountType = "Income"
            )
        )
        expenseDao.insertExpense(
            ExpenseEntity(
                id = 2,
                title = "Movie",
                amount = 550.00,
                date = currentDate,
                category = "SCHOOL",
                accountType = "Income"
            )
        )
        expenseDao.insertExpense(
            ExpenseEntity(
                id = 3,
                title = "Burger",
                amount = 30.30,
                date = currentDate,
                category = "FOOD_DRINK",
                accountType = "Expense"
            )
        )
    }
}
    }


