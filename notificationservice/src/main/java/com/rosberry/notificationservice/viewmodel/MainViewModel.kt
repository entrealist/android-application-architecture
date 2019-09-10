package com.rosberry.notificationservice.viewmodel

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.rosberry.notificationservice.extension.formatToLocalTimeString
import com.rosberry.notificationservice.extension.mutableLiveData
import com.rosberry.notificationservice.model.TimeRollItem
import com.rosberry.notificationservice.repository.MainRepository
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime

/**
 * @author mmikhailov on 2019-09-10.
 */
class MainViewModel : ViewModel() {

    private val mainRepository = MainRepository
    private val timetable = mutableLiveData(mainRepository.getInitialTimetable())

    val timeRollItems = Transformations.map(timetable) { srcList ->
        srcList.map { item ->
            TimeRollItem(item.key, item.value.formatToLocalTimeString(), item.value.hour,
                    item.value.minute)
        }
    }

    fun clickItemButton(itemId: Int) {
        val shouldAdd = itemId == timetable.value!!.entries.last().key

        if (shouldAdd) {
            timetable.value = timetable.value!!.toMutableMap()
                .also {
                    it[it.keys.last() + 1] = it.values.last()
                        .plusMinutes(1)
                }
        } else {
            timetable.value = timetable.value!!.toMutableMap()
                .also {
                    it.remove(itemId)
                }
        }
    }

    fun changeItemTime(itemId: Int, h: Int, m: Int) {
        timetable.value = timetable.value!!.toMutableMap()
            .apply {
                put(itemId, LocalDateTime.of(get(itemId)!!.toLocalDate(), LocalTime.of(h, m)) )
            }


        /*{ item ->
            if (itemId == item.key) {
                item.key to LocalDateTime.of(item.value.toLocalDate(), LocalTime.of(h, m))
            } else {
                item.key to item.value
            }
        }*/
    }

    /*private fun extractItemId(timetableItemTag: String) =
            timetableItemTag.substringAfterLast(
                    TAG_PREFIX_TIME_FIELD, "1").toInt()*/
}