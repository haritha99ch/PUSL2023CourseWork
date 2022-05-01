package com.example.login

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.makeramen.roundedimageview.RoundedImageView

class SliderAdapter internal constructor(
    slideritem: MutableList<Slideritem>,
    viewPager2: ViewPager2
): RecyclerView.Adapter<SliderAdapter.SliderViewHolder>(){

    private val slideritems:List<Slideritem>
    private val viewPager2 : ViewPager2

    init {
        this.slideritems= slideritem
        this.viewPager2 = viewPager2
    }

    class SliderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val imageView: RoundedImageView = itemView.findViewById(R.id.imageSlide)

        /**
         * Atribui uma imagem
         */
        fun image(slideritem : Slideritem){
            imageView.setImageResource(slideritem.image)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        return SliderViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.slide_item_container,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        holder.image(slideritems[position])
        if (position == slideritems.size - 2) {
            viewPager2.post(runnable)
        }
    }

    override fun getItemCount(): Int {
        return slideritems.size
    }

    private val runnable = Runnable {
        slideritem.addAll(slideritems)
        notifyDataSetChanged()
    }


}

