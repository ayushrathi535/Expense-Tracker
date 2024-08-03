package com.example.expensetracker.utilities

enum class Month(val displayName: String) {
    JANUARY("January"),
    FEBRUARY("February"),
    MARCH("March"),
    APRIL("April"),
    MAY("May"),
    JUNE("June"),
    JULY("July"),
    AUGUST("August"),
    SEPTEMBER("September"),
    OCTOBER("October"),
    NOVEMBER("November"),
    DECEMBER("December");

    companion object {
        fun fromDisplayName(displayName: String): Month? {
            return values().find { it.displayName == displayName }
        }

        fun fromOrdinal(ordinal: Int): Month? {
            return values().getOrNull(ordinal)
        }
    }
}
