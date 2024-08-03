package com.example.expensetracker.presentation.statsScreen.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.data.ExpenseDao
import com.example.expensetracker.data.ExpenseEntity
import com.example.expensetracker.utilities.FilterType
import com.example.expensetracker.utilities.Month
import com.example.expensetracker.utilities.SortOrder
import com.example.expensetracker.utilities.TransactionType
import com.example.expensetracker.utilities.minusDays
import com.example.expensetracker.utilities.normalizeDate
import com.example.expensetracker.utilities.plusDays
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class StatsViewModel(
    private val dao: ExpenseDao
) : ViewModel() {

    private val _selectedFilter = MutableStateFlow(FilterType.MONTH)
    val selectedFilter: StateFlow<FilterType> = _selectedFilter

    private val _selectedType = MutableStateFlow(TransactionType.EXPENSE)
    val selectedType: StateFlow<TransactionType> = _selectedType

    private val _selectedMonth = MutableStateFlow(Month.AUGUST)
    val selectedMonth: StateFlow<Month> = _selectedMonth

    private val _sortOrder = MutableStateFlow(SortOrder.HIGH_TO_LOW)
    val sortOrder: StateFlow<SortOrder> = _sortOrder

    private val _currentDate = MutableStateFlow(Date())
    val currentDate: StateFlow<Date> = _currentDate

    private val _graphSelectedDate = MutableStateFlow(Date())
    val graphSelectedDate: StateFlow<Date> = _graphSelectedDate

    private var _filteredTransaction = MutableStateFlow(emptyList<ExpenseEntity>())
    val filteredTransaction: StateFlow<List<ExpenseEntity>> = _filteredTransaction

    init {
        getFilteredTransaction()

    }


    fun getDailyTransactionByUserSelectedDate() {
        viewModelScope.launch(Dispatchers.IO) {
            val dateFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH)
            val dateString = _graphSelectedDate.value.toString()
            val date: Date? = dateFormat.parse(dateString)
            //   val normalizedDate = normalizeDate(currentDate.value)
            if (sortOrder.value == SortOrder.HIGH_TO_LOW) {
                dao.getDailyTransactionOrderByDateASC(date!!, _selectedType.value.title)
                    .collectLatest {
                        _filteredTransaction.value = it
                    }
            } else {
                dao.getDailyTransactionOrderByDateDES(date!!, _selectedType.value.title)
                    .collectLatest {
                        _filteredTransaction.value = it
                    }
            }
        }
    }

    fun getFilteredTransaction(filterType: FilterType = selectedFilter.value) {
        Log.e("StatsViewModel", "getFilteredTransaction: ${_selectedType.value.title}")
        viewModelScope.launch(Dispatchers.IO) {
            if (_selectedType.value == TransactionType.INCOME) {
                filterTransaction(filterType, TransactionType.INCOME.title)
            } else {
                filterTransaction(filterType, TransactionType.EXPENSE.title)
            }
        }
    }

    private fun filterTransaction(
        filterType: FilterType,
        title: String
    ) {
        when (filterType) {
            FilterType.MONTH -> filterByMonth(title)
            FilterType.DAY -> filterByDate(title)
            FilterType.WEEK -> filterByWeek(title)
            FilterType.YEAR -> filterByYear(title)
        }
    }

    private fun filterByYear(title: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (sortOrder.value == SortOrder.HIGH_TO_LOW) {
                dao.getExpensesByTypeDESC(selectedType.value.title).collectLatest {
                    _filteredTransaction.value = it
                }
            } else {
                dao.getExpensesByTypeASC(selectedType.value.title).collectLatest {
                    _filteredTransaction.value = it
                }
            }

        }
    }


    private fun filterByMonth(title: String) {

        viewModelScope.launch(Dispatchers.IO) {
//            dao.getMonthlyExpTransaction(title).collectLatest {
//                _filteredTransaction.value=it
//            }

            if (sortOrder.value == SortOrder.HIGH_TO_LOW) {
                dao.getCurrentMonthTransactionsByDesc(
                    currentDate.value.time,
                    selectedType.value.title
                )
                    .collectLatest {
                        Log.e("StatsViewModel", "filterByMonth: ${it.size}")
                        _filteredTransaction.value = it
                    }
            } else {
                dao.getCurrentMonthTransactionsByAsc(
                    currentDate.value.time,
                    selectedType.value.title
                )
                    .collectLatest {
                        Log.e("StatsViewModel", "filterByMonth: ${it.size}")
                        _filteredTransaction.value = it
                    }
            }

        }
    }

    private fun filterByDate(title: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val normalizedDate = normalizeDate(currentDate.value)
            if (sortOrder.value == SortOrder.HIGH_TO_LOW) {
                dao.getDailyTransactionOrderByDESC(normalizedDate, title).collectLatest {
                    Log.e("StatsViewModel", "${currentDate.value} ${selectedType.value.title}")
                    Log.e("StatsViewModel", "getFilteredTransaction: ${it.size}")
                    _filteredTransaction.value = it
                }
            } else {
                dao.getDailyTransactionOrderByASC(normalizedDate, title).collectLatest {
                    Log.e("StatsViewModel", "${currentDate.value} ${selectedType.value.title}")
                    Log.e("StatsViewModel", "getFilteredTransaction: ${it.size}")
                    _filteredTransaction.value = it
                }
            }
        }
    }

    private fun filterByWeek(title: String = selectedType.value.title) {

        viewModelScope.launch(Dispatchers.IO) {
            val normalizedDate = normalizeDate(currentDate.value)
            if (sortOrder.value == SortOrder.HIGH_TO_LOW) {
                dao.getWeekLyTransactionOrderByDESC(currentDate.value.time, title).collectLatest {
                    _filteredTransaction.value = it
                    Log.e("StatsViewModel", "getFilteredTransaction: ${it.size}")
                }
            } else {
                dao.getWeekLyTransactionOrderByASC(currentDate.value.time, title).collectLatest {
                    _filteredTransaction.value = it
                    Log.e("StatsViewModel", "getFilteredTransaction: ${it.size}")
                }
            }
        }
    }


    fun currentDate(date: Date) {
        _graphSelectedDate.value = date
        getDailyTransactionByUserSelectedDate()
    }

    fun updateSortOrder(order: SortOrder) {
        _sortOrder.value = order
        getFilteredTransaction()
        Log.e("StatsViewModel", "updateSortOrder: ${_sortOrder.value}")
    }

    fun updateMonth(month: Month) {
        _selectedMonth.value = month
    }

    fun updateFilter(filterType: FilterType) {
        _selectedFilter.value = filterType
        getFilteredTransaction(filterType)
    }

    fun updateType(transactionType: TransactionType) {
        _selectedType.value = transactionType
        getFilteredTransaction()
    }
}