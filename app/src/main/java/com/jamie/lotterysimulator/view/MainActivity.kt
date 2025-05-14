package com.jamie.lotterysimulator.view

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jamie.lotterysimulator.R
import com.jamie.lotterysimulator.databinding.ActivityMainBinding
import com.jamie.lotterysimulator.model.Constants.SlotList
import com.jamie.lotterysimulator.model.Slot
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var slotRecycler: RecyclerView
    private lateinit var generateBtn: CardView

    private lateinit var winningTicket:Slot

    private lateinit var slotOne:TextView
    private lateinit var slotTwo:TextView
    private lateinit var slotThree:TextView
    private lateinit var slotFour:TextView
    private lateinit var slotFive:TextView
    private lateinit var slotSix: TextView





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        init()
    }
    private fun init(){

        SlotList.boilerPlate()

        slotRecycler = binding.slots
        val slotData = SlotList.getSlotNumbers()

        if(slotData!!.isNotEmpty()||slotData!=null) {
            val customAdapter = SlotRecyclerRecyclerAdapter(slotData,this)

            slotRecycler.layoutManager = LinearLayoutManager(this)
            slotRecycler.adapter = customAdapter
        }
        generateBtn = binding.createNewButton

        slotOne = binding.lottoNoOne
        slotTwo = binding.lottoNoTwo
        slotThree = binding.lottoNoThree
        slotFour = binding.lottoNoFour
        slotFive = binding.lottoNoFive
        slotSix = binding.lottoNoSix

        var slotnumbers = arrayListOf(
            Random.nextInt(0,36),
            Random.nextInt(0,36),
            Random.nextInt(0,36),
            Random.nextInt(0,36),
            Random.nextInt(0,36),
            Random.nextInt(0,36))//generates numbers between one and 36

        winningTicket = Slot(slotnumbers, 3)//creates the winning when the user first enters

        slotOne.text = winningTicket.slots[0].toString()
        slotTwo.text = winningTicket.slots[1].toString()
        slotThree.text = winningTicket.slots[2].toString()
        slotFour.text = winningTicket.slots[3].toString()
        slotFive.text = winningTicket.slots[4].toString()
        slotSix.text = winningTicket.slots[5].toString()

        generateBtn.setOnClickListener {
            for (i in 0..5){
                slotnumbers[i] = Random.nextInt(0,36)
            }
            winningTicket.slots = slotnumbers
            slotOne.text = winningTicket.slots[0].toString()
            slotTwo.text = winningTicket.slots[1].toString()
            slotThree.text = winningTicket.slots[2].toString()
            slotFour.text = winningTicket.slots[3].toString()
            slotFive.text = winningTicket.slots[4].toString()
            slotSix.text = winningTicket.slots[5].toString()
        }


    }
}