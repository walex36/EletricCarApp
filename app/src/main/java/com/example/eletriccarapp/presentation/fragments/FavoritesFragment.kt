package com.example.eletriccarapp.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.eletriccarapp.R
import com.example.eletriccarapp.database.CarRepository
import com.example.eletriccarapp.domain.entities.Car
import com.example.eletriccarapp.presentation.adapters.CarAdapter

class FavoritesFragment : Fragment() {

    private lateinit var listCar: RecyclerView
    private lateinit var tvEmpyt: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.favorites_fragments, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView(view)
        setUpList()
    }

    override fun onResume() {
        super.onResume()
        setUpList()
    }

    private fun setUpView(view: View) {
        listCar = view.findViewById(R.id.rv_cars)
        tvEmpyt = view.findViewById(R.id.tv_empyt)
    }

    private fun setUpList() {
        val cars = getCarsFavorite()
        val adapter = CarAdapter(cars)
            listCar.adapter = adapter

        if (cars.isNotEmpty()) {
            tvEmpyt.visibility = View.GONE
            listCar.visibility = View.VISIBLE
        }else{
            tvEmpyt.visibility = View.VISIBLE
            listCar.visibility = View.GONE
        }

        adapter.carItemLister = {
            CarRepository(requireContext()).delete(it.id)
            setUpList()
        }
    }

    private fun getCarsFavorite(): ArrayList<Car> {
        return CarRepository(requireContext()).getAll()
    }

}