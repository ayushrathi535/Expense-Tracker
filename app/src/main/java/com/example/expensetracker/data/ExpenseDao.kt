package com.example.expensetracker.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.expensetracker.utilities.TransactionType
import kotlinx.coroutines.flow.Flow
import java.util.Date


@Dao
interface ExpenseDao {

    @Query("SELECT * From expense_table")
    fun getExpenses(): Flow<List<ExpenseEntity>>

    @Query("DELETE FROM expense_table")
    fun deleteData()

    @Query("SELECT * FROM expense_table WHERE accountType = :type Order BY amount DESC")
    fun getExpensesByTypeDESC(type: String): Flow<List<ExpenseEntity>>

    @Query("SELECT * FROM expense_table WHERE accountType = :type Order BY amount ASC")
    fun getExpensesByTypeASC(type: String): Flow<List<ExpenseEntity>>
//
@Query("SELECT * FROM expense_table WHERE date = :entryDate AND accountType = :accountType Order By amount ASC")
fun getDailyTransactionOrderByDateASC(entryDate: Date, accountType: String): Flow<List<ExpenseEntity>>

    @Query("SELECT * FROM expense_table WHERE date = :entryDate AND accountType = :accountType Order By amount DESC")
    fun getDailyTransactionOrderByDateDES(entryDate: Date, accountType: String): Flow<List<ExpenseEntity>>

    @Query("SELECT * FROM expense_table WHERE strftime('%Y-%m-%d', date / 1000, 'unixepoch') = :entryDate AND accountType = :accountType Order By amount DESC")
    fun getDailyTransactionOrderByDESC(entryDate: String, accountType: String): Flow<List<ExpenseEntity>>

    @Query("SELECT * FROM expense_table WHERE strftime('%Y-%m-%d', date / 1000, 'unixepoch') = :entryDate AND accountType = :accountType Order By amount ASC")
    fun getDailyTransactionOrderByASC(entryDate: String, accountType: String): Flow<List<ExpenseEntity>>

    @Query(" SELECT * FROM expense_table WHERE strftime('%Y', date / 1000, 'unixepoch') " +
            "=strftime('%Y', :currentDate / 1000, 'unixepoch') AND " +
            "strftime('%m', date / 1000, 'unixepoch') = strftime('%m', :currentDate / 1000, 'unixepoch')" +
            " AND accountType = :accountType  Order By amount DESC")
    fun getCurrentMonthTransactionsByDesc(currentDate: Long, accountType: String): Flow<List<ExpenseEntity>>



    @Query(" SELECT * FROM expense_table WHERE strftime('%Y', date / 1000, 'unixepoch') " +
            "=strftime('%Y', :currentDate / 1000, 'unixepoch') AND " +
            "strftime('%m', date / 1000, 'unixepoch') = strftime('%m', :currentDate / 1000, 'unixepoch')" +
            " AND accountType = :accountType  Order By amount ASC")
    fun getCurrentMonthTransactionsByAsc(currentDate: Long, accountType: String): Flow<List<ExpenseEntity>>


    @Query("""
        SELECT * FROM expense_table
        WHERE date BETWEEN 
        (strftime('%s', datetime(:today / 1000, 'unixepoch', 'start of day', 'weekday 0', '-6 days')) * 1000) 
        AND 
        (strftime('%s', datetime(:today / 1000, 'unixepoch', 'start of day', 'weekday 0', '+1 day')) * 1000)
    AND accountType = :accountType Order By amount DESC""")
    fun getWeekLyTransactionOrderByDESC(today:Long,accountType: String):Flow<List<ExpenseEntity>>


    @Query("""
        SELECT * FROM expense_table
        WHERE date BETWEEN 
        (strftime('%s', datetime(:today / 1000, 'unixepoch', 'start of day', 'weekday 0', '-6 days')) * 1000) 
        AND 
        (strftime('%s', datetime(:today / 1000, 'unixepoch', 'start of day', 'weekday 0', '+1 day')) * 1000)
    AND accountType = :accountType Order By amount ASC""")
    fun getWeekLyTransactionOrderByASC(today:Long,accountType: String):Flow<List<ExpenseEntity>>

    @Query("SELECT * FROM expense_table WHERE accountType = :accountType")
    fun getTransactionByAccount(accountType: String): Flow<List<ExpenseEntity>>

    @Insert
    suspend fun insertExpense(expenseEntity: ExpenseEntity)

    @Delete
    suspend fun deleteExpense(expenseEntity: ExpenseEntity)

    @Update
    suspend fun updateExpense(expenseEntity: ExpenseEntity)

}