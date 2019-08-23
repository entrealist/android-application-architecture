package com.rosberry.sample.workmanager.domain

/**
 * @author mmikhailov on 16/04/2019.
 */
enum class Frequency(val id: Long, val periodInDays: Long) {
    EVERYDAY(1, 1),
    EVERY_SECOND_DAY(2, 2),
    EVERY_THIRD_DAY(3, 3),
    EVERY_FOURTH_DAY(4, 4),
    EVERY_FIFTH_DAY(5, 5),
    EVERY_SIXTH_DAY(6, 6),
    EVERY_SEVENTH_DAY(7, 7),
}