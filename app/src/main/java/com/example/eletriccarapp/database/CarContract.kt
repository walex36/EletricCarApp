package com.example.eletriccarapp.database

import android.provider.BaseColumns

object CarContract {

    object CarEntry : BaseColumns {
        const val TB_NAME = "car"
        const val TB_COLUMN_ID_REMOTE = "idRemote"
        const val TB_COLUMN_NAME = "name"
        const val TB_COLUMN_PRICE = "price"
        const val TB_COLUMN_BATTERY = "battery"
        const val TB_COLUMN_POWER = "power"
        const val TB_COLUMN_RECHARGE = "recharge"
        const val TB_COLUMN_URL_IMAGE = "url_image"

    }

    const val SQL_TABLE = "CREATE TABLE ${CarEntry.TB_NAME} (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "${CarEntry.TB_COLUMN_ID_REMOTE} INTEGER," +
            "${CarEntry.TB_COLUMN_NAME} TEXT," +
            "${CarEntry.TB_COLUMN_PRICE} REAL," +
            "${CarEntry.TB_COLUMN_BATTERY} TEXT," +
            "${CarEntry.TB_COLUMN_POWER} TEXT," +
            "${CarEntry.TB_COLUMN_RECHARGE} TEXT," +
            "${CarEntry.TB_COLUMN_URL_IMAGE} TEXT)"

    const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXIST ${CarEntry.TB_NAME}"
}