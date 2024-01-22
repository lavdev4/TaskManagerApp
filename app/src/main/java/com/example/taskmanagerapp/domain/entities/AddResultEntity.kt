package com.example.taskmanagerapp.domain.entities

sealed class AddResultEntity {
    data object Success : AddResultEntity()
    data object InvertedDateStartEnd : AddResultEntity()
    data object NoNameSpecified : AddResultEntity()
    data object NoStartDateSpecified : AddResultEntity()
    data object NoDescriptionSpecified : AddResultEntity()
    data object NoStatusSpecified : AddResultEntity()
}