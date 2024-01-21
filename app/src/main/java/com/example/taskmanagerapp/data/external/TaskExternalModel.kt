package com.example.taskmanagerapp.data.external

import com.google.gson.annotations.SerializedName

data class TaskExternalModel(
    val id: Int,
    @SerializedName("date_start")
    val dateStart: Long,
    @SerializedName("date_finish")
    val dateFinish: Long? = null,
    val name: String,
    val description: String,
    val actual: Boolean
)