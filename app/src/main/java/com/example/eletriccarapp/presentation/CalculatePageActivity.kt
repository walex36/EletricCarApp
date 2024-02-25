package com.example.eletriccarapp.presentation

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import com.example.eletriccarapp.R

class CalculatePageActivity : AppCompatActivity() {

    private lateinit var etPriceKwh: EditText
    private lateinit var etKmTravele: EditText
    private lateinit var tvResult: TextView

    private var priceKwh: Float = 0f
    private var kmTravele: Float = 0f
    private var resultAutonomy: Float = 0f
    private lateinit var btnCalculate: Button
    private lateinit var ibtnBackRoute: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculate_page)

        setUpView()
        setUpListeners()
        getSharedPref()
    }

    private fun setUpView() {
        etPriceKwh = findViewById(R.id.et_price_kwh)
        etKmTravele = findViewById(R.id.et_km_traveled)
        tvResult = findViewById(R.id.tv_result)


        setResult()

        btnCalculate = findViewById(R.id.btn_calculate)
        ibtnBackRoute = findViewById(R.id.ibtn_back_route)
    }

    private fun setUpListeners() {
        btnCalculate.setOnClickListener {
            priceKwh = etPriceKwh.text.toString().toFloat()
            kmTravele = etKmTravele.text.toString().toFloat()

            calculateAutonomy(priceKwh, kmTravele)
        }

        ibtnBackRoute.setOnClickListener{
            finish()
        }
    }

    private fun calculateAutonomy(priceKwh: Float, kmTravele: Float) {
        resultAutonomy = priceKwh / kmTravele
        setResult()
        saveSharedPref(resultAutonomy)
    }

    private fun setResult() {
        tvResult.text = "Result: R$$resultAutonomy"
    }

    private fun saveSharedPref(result: Float){
        val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()){
            putFloat(getString(R.string.saved_calculate), result)
            apply()
        }
    }

    private fun getSharedPref(){
        val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return
        resultAutonomy = sharedPref.getFloat(getString(R.string.saved_calculate), 0f)
        setResult()
    }
}