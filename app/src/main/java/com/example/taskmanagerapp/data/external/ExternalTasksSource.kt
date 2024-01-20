package com.example.taskmanagerapp.data.external

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class ExternalTasksSource(
    private val dataStore: DataStore<Preferences>,
    private val gson: Gson
) {
    private val tasksKey = stringPreferencesKey("tasks")

    fun getFlow(): Flow<List<TaskExternalModel>> {
        return dataStore.data
            .map { deserializeTasks(it[tasksKey] ?: EMPTY_JSON_ARRAY) }
    }

    suspend fun getList(): List<TaskExternalModel> {
        return getFlow().first()
    }

    /** Id uniqueness. */
    suspend fun add(data: List<TaskExternalModel>) {
        Log.d("Tasks", "incoming: ${data.size}")
        val cachedData = getFlow().first().toMutableList()
        Log.d("Tasks", "cached: ${cachedData.size}")
        val new = cachedData.addAllWithUniqueId(data)
        Log.d("Tasks", "new: ${new.size}")
        refresh(new)
    }

    suspend fun refresh(data: List<TaskExternalModel>) {
        dataStore.edit { it[tasksKey] = serializeTasks(data) }
    }

    suspend fun clear() {
        dataStore.edit { it.remove(tasksKey) }
    }

    private fun List<TaskExternalModel>.addAllWithUniqueId(
        newList: List<TaskExternalModel>
    ): List<TaskExternalModel> {
        val mutableOldList = this.toMutableList()
        newList.forEach { newElement ->
            var isFound = false
            while (!isFound) {
                val isRemoved = mutableOldList.removeIf { it.id == newElement.id }
                isFound = !isRemoved
            }
        }
        mutableOldList.addAll(newList)
        return mutableOldList
    }

    private fun serializeTasks(tasks: List<TaskExternalModel>): String {
        return gson.toJson(tasks)
    }

    private fun deserializeTasks(jsonTasks: String): List<TaskExternalModel> {
        val tasksType = object : TypeToken<List<TaskExternalModel>>() {}.type
        return gson.fromJson(jsonTasks, tasksType)
    }

    companion object {
        private const val EMPTY_JSON_ARRAY = "[]"
    }
}