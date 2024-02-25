package com.example.eletriccarapp.database

import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns
import android.util.Log
import com.example.eletriccarapp.database.CarContract.CarEntry.TB_COLUMN_BATTERY
import com.example.eletriccarapp.database.CarContract.CarEntry.TB_COLUMN_ID_REMOTE
import com.example.eletriccarapp.database.CarContract.CarEntry.TB_COLUMN_NAME
import com.example.eletriccarapp.database.CarContract.CarEntry.TB_COLUMN_POWER
import com.example.eletriccarapp.database.CarContract.CarEntry.TB_COLUMN_PRICE
import com.example.eletriccarapp.database.CarContract.CarEntry.TB_COLUMN_RECHARGE
import com.example.eletriccarapp.database.CarContract.CarEntry.TB_COLUMN_URL_IMAGE
import com.example.eletriccarapp.database.CarContract.CarEntry.TB_NAME
import com.example.eletriccarapp.domain.entities.Car

class CarRepository(private val context: Context) {

    fun saveIfNotExist(car: Car): Long? {
        val carVerify: Car? = findCarById(car.idRemote)

        carVerify?.let {
            return null
        }

        return save(car)
    }

    private fun save(car: Car): Long {
        try {
            val dbHelper = CarDbHelper(context)
            val db = dbHelper.writableDatabase

            val values = ContentValues().apply {
                put(TB_COLUMN_ID_REMOTE, car.idRemote)
                put(TB_COLUMN_NAME, car.name)
                put(TB_COLUMN_PRICE, car.price)
                put(TB_COLUMN_BATTERY, car.battery)
                put(TB_COLUMN_POWER, car.power)
                put(TB_COLUMN_RECHARGE, car.recharge)
                put(TB_COLUMN_URL_IMAGE, car.image)
            }

            return db.insert(TB_NAME, null, values)
        } catch (e: Exception) {
            e.message?.let { Log.e("Error insert db", it) }
            throw e
        }
    }

    fun delete(idLocal: Int): Boolean {
        try {
            val dbHelper = CarDbHelper(context)
            val db = dbHelper.writableDatabase

            val filter = "${BaseColumns._ID} = ?"
            val filterValue = arrayOf(idLocal.toString())

            var qntDeleted = db.delete(TB_NAME, filter, filterValue)

            return qntDeleted > 0

        } catch (e: Exception) {
            e.message?.let { Log.e("Error delete db", it) }
            throw e
        }
    }

    fun findCarById(idRemote: Int): Car? {
        try {
            val dbHelper = CarDbHelper(context)
            val db = dbHelper.writableDatabase
            val columns = arrayOf(
                BaseColumns._ID,
                TB_COLUMN_ID_REMOTE,
                TB_COLUMN_NAME,
                TB_COLUMN_PRICE,
                TB_COLUMN_BATTERY,
                TB_COLUMN_POWER,
                TB_COLUMN_RECHARGE,
                TB_COLUMN_URL_IMAGE
            )
            val filter = "$TB_COLUMN_ID_REMOTE = ?"
            val filterValue = arrayOf(idRemote.toString())

            val cursor = db.query(
                TB_NAME,
                columns,
                filter,
                filterValue,
                null,
                null,
                null
            )
            var itemCar: Car? = null
            with(cursor) {
                while (moveToNext()) {
                    val id = getLong(getColumnIndexOrThrow(BaseColumns._ID))
                    val idRemote = getLong(getColumnIndexOrThrow(TB_COLUMN_ID_REMOTE))
                    val name = getString(getColumnIndexOrThrow(TB_COLUMN_NAME))
                    val price = getLong(getColumnIndexOrThrow(TB_COLUMN_PRICE))
                    val battery = getString(getColumnIndexOrThrow(TB_COLUMN_BATTERY))
                    val power = getString(getColumnIndexOrThrow(TB_COLUMN_POWER))
                    val recharge = getString(getColumnIndexOrThrow(TB_COLUMN_RECHARGE))
                    val image = getString(getColumnIndexOrThrow(TB_COLUMN_URL_IMAGE))

                    itemCar = Car(
                        id = id.toInt(),
                        idRemote = idRemote.toInt(),
                        name = name,
                        price = price.toFloat(),
                        battery = battery,
                        power = power,
                        recharge = recharge,
                        isFavorite = true,
                        image = image
                    )
                }
            }

            cursor.close()

            return itemCar
        } catch (e: Exception) {
            e.message?.let { Log.e("Error find db", it) }
            throw e
        }
    }

    fun getAll(): ArrayList<Car> {
        try {
            val dbHelper = CarDbHelper(context)
            val db = dbHelper.writableDatabase
            val columns = arrayOf(
                BaseColumns._ID,
                TB_COLUMN_ID_REMOTE,
                TB_COLUMN_NAME,
                TB_COLUMN_PRICE,
                TB_COLUMN_BATTERY,
                TB_COLUMN_POWER,
                TB_COLUMN_RECHARGE,
                TB_COLUMN_URL_IMAGE
            )

            val cursor = db.query(
                TB_NAME,
                columns,
                null,
                null,
                null,
                null,
                null
            )
            var listCars: ArrayList<Car> = arrayListOf()
            with(cursor) {
                while (moveToNext()) {
                    val id = getLong(getColumnIndexOrThrow(BaseColumns._ID))
                    val idRemote = getLong(getColumnIndexOrThrow(TB_COLUMN_ID_REMOTE))
                    val name = getString(getColumnIndexOrThrow(TB_COLUMN_NAME))
                    val price = getLong(getColumnIndexOrThrow(TB_COLUMN_PRICE))
                    val battery = getString(getColumnIndexOrThrow(TB_COLUMN_BATTERY))
                    val power = getString(getColumnIndexOrThrow(TB_COLUMN_POWER))
                    val recharge = getString(getColumnIndexOrThrow(TB_COLUMN_RECHARGE))
                    val image = getString(getColumnIndexOrThrow(TB_COLUMN_URL_IMAGE))

                    val car = Car(
                        id = id.toInt(),
                        idRemote = idRemote.toInt(),
                        name = name,
                        price = price.toFloat(),
                        battery = battery,
                        power = power,
                        recharge = recharge,
                        isFavorite = true,
                        image = image
                    )
                    listCars.add(car)
                }
            }

            cursor.close()

            return listCars
        } catch (e: Exception) {
            e.message?.let { Log.e("Error get cars db", it) }
            throw e
        }
    }
}