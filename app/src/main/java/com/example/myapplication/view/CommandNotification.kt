package com.example.myapplication.view

class CommandNotification {

    companion object {

        val commandBannerMessages: Map<Commands, NotificationMessage> = mapOf(
            Commands.LOCK to NotificationMessage.VEHICLE_LOCKED,
            Commands.UNLOCK to NotificationMessage.VEHICLE_UNLOCKED,
            Commands.START to NotificationMessage.VEHICLE_STARTED
        )

    }


}