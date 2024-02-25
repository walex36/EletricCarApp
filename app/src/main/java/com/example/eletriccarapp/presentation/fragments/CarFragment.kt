package com.example.eletriccarapp.presentation.fragments

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isEmpty
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.eletriccarapp.R
import com.example.eletriccarapp.data.factory.CarsApi
import com.example.eletriccarapp.database.CarRepository
import com.example.eletriccarapp.domain.entities.Car
import com.example.eletriccarapp.presentation.adapters.CarAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CarFragment : Fragment() {

    private lateinit var listCar: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var llNoConnection: LinearLayout
    private lateinit var carsApi: CarsApi

    private lateinit var listCarsModel: List<Car>


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.car_fragments, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView(view)
        setUpRetrofit()
    }

    override fun onResume() {
        super.onResume()
        callService()
    }

    private fun setUpView(view: View) {
        listCar = view.findViewById(R.id.rv_cars)
        progressBar = view.findViewById(R.id.pb_loading)
        llNoConnection = view.findViewById(R.id.ll_empty_state)
    }

    private fun setUpList() {
        progressBar.visibility = View.GONE
        val adapterAux = CarAdapter(listCarsModel)

        listCar.apply {
            visibility = View.VISIBLE
            adapter = adapterAux
        }

        adapterAux.carItemLister = { car: Car ->
            val repository = CarRepository(requireContext())
            if (car.isFavorite) {
                repository.saveIfNotExist(car)
            } else {
                repository.delete(car.id)
            }

        }
    }

    private fun setUpRetrofit() {
        val builder = Retrofit.Builder()
            .baseUrl("https://walex36.github.io/mockCarsEletric/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        carsApi = builder.create(CarsApi::class.java)

    }

    private fun getAllCars() {
        carsApi.getAllCars().enqueue(object : Callback<List<Car>> {
            override fun onResponse(call: Call<List<Car>>, response: Response<List<Car>>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        validCarsIsFavorite(it)
                    }
                } else {
                    Toast.makeText(context, "Error when searching car list", Toast.LENGTH_LONG)
                        .show()
                }
            }

            override fun onFailure(call: Call<List<Car>>, t: Throwable) {
                Toast.makeText(context, "Error when searching car list", Toast.LENGTH_LONG)
                    .show()
            }

        })
    }

    private fun callService() {
        if (checkNetwork(context)) {
            progressBar.visibility = View.VISIBLE
            llNoConnection.visibility = View.GONE
            if (listCar.isEmpty()) {
                getAllCars()
            }else{
                validCarsIsFavorite(listCarsModel)
            }
        } else {
            llNoConnection.visibility = View.VISIBLE
        }
    }

    private fun validCarsIsFavorite(cars: List<Car>) {
        val result = arrayListOf<Car>()
        val repository = CarRepository(requireContext())

        for (car in cars) {
            if (car.isFavorite) {
                repository.saveIfNotExist(car)
            }

            val carLocalFavorite = repository.findCarById(car.idRemote)

            result.add(car.apply {
                id = carLocalFavorite?.id ?: 0
                isFavorite = carLocalFavorite != null
            })
        }

        listCarsModel = result
        setUpList()
    }

    private fun checkNetwork(context: Context?): Boolean {
        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false

            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION")
            val networkInfo = connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }


}