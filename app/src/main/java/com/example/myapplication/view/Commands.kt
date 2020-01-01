package com.example.myapplication.view

enum class Commands(val friendlyName: NotificationMessage) {
    START(NotificationMessage.VEHICLE_STARTED),
    LOCK(NotificationMessage.VEHICLE_LOCKED),
    UNLOCK(NotificationMessage.VEHICLE_UNLOCKED),
}