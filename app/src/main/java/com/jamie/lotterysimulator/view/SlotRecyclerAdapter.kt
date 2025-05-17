package com.jamie.lotterysimulator.view

import android.content.Context
import android.content.Intent
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.jamie.lotterysimulator.R
import com.jamie.lotterysimulator.model.Slot

class SlotRecyclerRecyclerAdapter(private var items: List<Slot>, private val context: Context):
    RecyclerView.Adapter<SlotRecyclerRecyclerAdapter.ItemViewHolder>(){

    companion object{
        private val TAG = "ColourSelectionRecyclerAdapter"
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.slot_card_view, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val model = items[position]

        for (i in 0..5){

            Log.d(TAG, "onBindViewHolder: ")
            holder.lottoTextView[i].text = model.slots[i].toString()
        }

        holder.winningNumbers.text = model.winningNumbers.toString()

        holder.changeNumbers.setOnClickListener{
            var toChangeNumbers = Intent(context, ChangeNumbers::class.java)
            toChangeNumbers.putExtra("number", position)
            context.startActivity(toChangeNumbers)
        }

    }

    fun updateData(newList: List<Slot>) {
        items = newList
        notifyDataSetChanged()
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val lottoNoOne = itemView.findViewById<TextView>(R.id.lotto_no_one)
        val lottoNoTwo = itemView.findViewById<TextView>(R.id.lotto_no_two)
        val lottoNoThree = itemView.findViewById<TextView>(R.id.lotto_no_three)
        val lottoNoFour = itemView.findViewById<TextView>(R.id.lotto_no_four)
        val lottoNoFive = itemView.findViewById<TextView>(R.id.lotto_no_five)
        val lottoNoSix = itemView.findViewById<TextView>(R.id.lotto_no_six)
        val winningNumbers = itemView.findViewById<TextView>(R.id.winningNumbers)
        val changeNumbers = itemView.findViewById<CardView>(R.id.slotCardView)

        val lottoTextView = listOf(lottoNoOne, lottoNoTwo, lottoNoThree, lottoNoFour, lottoNoFive, lottoNoSix)

    }
}