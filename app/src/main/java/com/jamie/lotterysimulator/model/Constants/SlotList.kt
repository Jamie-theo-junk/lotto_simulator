package com.jamie.lotterysimulator.model.Constants


import android.util.Log
import com.jamie.lotterysimulator.model.Slot

object SlotList {

    private var slotNumbers:ArrayList<Slot> = arrayListOf()
    private var WinningTicket:ArrayList<Int> = arrayListOf()
    private const val TAG = "SlotList"

    fun getSlotNumbers(): ArrayList<Slot> {
            return slotNumbers
    }

    fun setSlotNumbers(newSlotNumbers: ArrayList<Int>){
            val newSlot = Slot(newSlotNumbers,0)
            slotNumbers.add(newSlot)
    }

    fun getWinningTicket():ArrayList<Int>{
        return WinningTicket

    }

    fun setWinningTicket(newWinningTicket:ArrayList<Int>){
        WinningTicket = newWinningTicket

    }

}