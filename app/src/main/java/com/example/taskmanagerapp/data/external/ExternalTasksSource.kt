package com.example.taskmanagerapp.data.external

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

    fun get(): Flow<List<TaskExternalModel>> {
        return dataStore.data
            .map { deserializeTasks(it[tasksKey] ?: EMPTY_JSON_ARRAY) }
    }

    suspend fun refresh(data: List<TaskExternalModel>) {
        dataStore.edit { it[tasksKey] = serializeTasks(data) }
    }

    suspend fun add(data: List<TaskExternalModel>) {
        val cachedData = get().first().toMutableList()
        refresh(cachedData.addAllWithUniqueId(data))
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
            while (isFound) {
                val coincidence = mutableOldList.find { it.id == newElement.id }
                coincidence?.let { mutableOldList.remove(coincidence) } ?: run { isFound = true }
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