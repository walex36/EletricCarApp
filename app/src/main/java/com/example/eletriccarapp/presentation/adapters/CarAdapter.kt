package com.example.eletriccarapp.presentation.adapters


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.eletriccarapp.R
import com.example.eletriccarapp.domain.entities.Car

@Suppress("PropertyName")
class CarAdapter(private val data: List<Car>) :
    RecyclerView.Adapter<CarAdapter.ViewHolder>() {

    var carItemLister: (Car) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.car_item, parent, false)

        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tv_name.text = data[position].name
        holder.tv_price.text = "R$ ${data[position].price}"
        holder.tv_power.text = data[position].power
        holder.tv_battery.text = data[position].battery
        holder.tv_recharge.text = data[position].recharge
        Glide.with(holder.img_image).load(data[position].image).into(holder.img_image)
        if (data[position].isFavorite) {
            holder.img_favorite.setImageResource(R.drawable.star)
        }

        holder.img_favorite.setOnClickListener {
            var car = data[position]
            car = setUpFavorite(car, holder)
            carItemLister(car)
        }
    }

    private fun setUpFavorite(
        car: Car,
        holder: ViewHolder,
    ) : Car{
        car.isFavorite = !car.isFavorite
        if (car.isFavorite) {
            holder.img_favorite.setImageResource(R.drawable.star)
        } else {
            holder.img_favorite.setImageResource(R.drawable.star_border)
        }
        return car
    }

    override fun getItemCount(): Int = data.size


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv_name: TextView
        val tv_price: TextView
        val tv_power: TextView
        val tv_battery: TextView
        val tv_recharge: TextView
        val img_image: ImageView
        val img_favorite: ImageView

        init {
            tv_name = view.findViewById(R.id.card_car_name)
            tv_price = view.findViewById(R.id.card_car_price)
            tv_battery = view.findViewById(R.id.card_car_battery)
            tv_power = view.findViewById(R.id.card_car_power)
            tv_recharge = view.findViewById(R.id.card_car_recharge)
            img_image = view.findViewById(R.id.card_car_image)
            img_favorite = view.findViewById(R.id.card_car_favorite)
        }
    }
}

