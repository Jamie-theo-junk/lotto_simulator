package com.jamie.lotterysimulator.model.Constants


import android.util.Log
import com.jamie.lotterysimulator.model.Slot

object SlotList {

    private var slotnumbers:ArrayList<Slot>? = null

    private const val TAG = "SlotList"

    fun getSlotNumbers(): ArrayList<Slot>? {
            return slotnumbers
    }

    fun setSlotNumbers(slotnumbers:ArrayList<Slot>){
        this.slotnumbers = slotnumbers
    }


    fun boilerPlate(){
        Log.d(TAG,"boilerPlate")

        val slotOne = Slot(arrayListOf(3,4,36,5,12,34),0)
        val slotTwo = Slot(arrayListOf(32,4,4,5,12,3),0)
        val slotThree = Slot(arrayListOf(32,4,4,5,12,3),0)
        val slotFour = Slot(arrayListOf(32,4,4,5,12,3),0)
        val slotFive= Slot(arrayListOf(32,4,4,5,12,3),0)
        val slotSix = Slot(arrayListOf(32,4,4,5,12,3),0)

        slotnumbers = arrayListOf(slotOne,slotTwo,slotThree,slotFour,slotFive,slotSix)

    }

}