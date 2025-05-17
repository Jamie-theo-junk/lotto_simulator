package com.jamie.lotterysimulator.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
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

    companion object{
        private const val TAG = "MainActivity"
    }


    private lateinit var binding: ActivityMainBinding

    private lateinit var slotRecycler: RecyclerView
    private lateinit var generateBtn: CardView
    private lateinit var addSlotBtn: CardView


    private lateinit var slotOne:TextView
    private lateinit var slotTwo:TextView
    private lateinit var slotThree:TextView
    private lateinit var slotFour:TextView
    private lateinit var slotFive:TextView
    private lateinit var slotSix: TextView

    private lateinit var slotData:ArrayList<Slot>






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
        slotRecycler = binding.slots
        slotData = SlotList.getSlotNumbers()

        val customAdapter = SlotRecyclerRecyclerAdapter(slotData,this)
        slotRecycler.layoutManager = LinearLayoutManager(this)
        slotRecycler.adapter = customAdapter

        generateBtn = binding.createNewButton
        addSlotBtn = binding.addSlotButton

        slotOne = binding.lottoNoOne
        slotTwo = binding.lottoNoTwo
        slotThree = binding.lottoNoThree
        slotFour = binding.lottoNoFour
        slotFive = binding.lottoNoFive
        slotSix = binding.lottoNoSix

        var randomNumberArray = arrayListOf(
            Random.nextInt(1,36),
            Random.nextInt(1,36),
            Random.nextInt(1,36),
            Random.nextInt(1,36),
            Random.nextInt(1,36),
            Random.nextInt(1,36))//generates numbers between one and 36

        SlotList.setWinningTicket(randomNumberArray)//creates the winning when the user first enters


        var winningTicket = SlotList.getWinningTicket()
        winningAmount(winningTicket)
        slotOne.text = winningTicket[0].toString()
        slotTwo.text = winningTicket[1].toString()
        slotThree.text = winningTicket[2].toString()
        slotFour.text = winningTicket[3].toString()
        slotFive.text = winningTicket[4].toString()
        slotSix.text = winningTicket[5].toString()

        generateBtn.setOnClickListener {
            for (i in 0..5){
                randomNumberArray[i] = Random.nextInt(0,36)
            }
            winningTicket = randomNumberArray
            slotOne.text = winningTicket[0].toString()
            slotTwo.text = winningTicket[1].toString()
            slotThree.text = winningTicket[2].toString()
            slotFour.text = winningTicket[3].toString()
            slotFive.text = winningTicket[4].toString()
            slotSix.text = winningTicket[5].toString()

            customAdapter.updateData(slotData)
        }

        addSlotBtn.setOnClickListener {
            val intent = Intent(this, AddSlot::class.java)
            startActivity(intent)
        }

    }

    private fun winningAmount(winningTicket:ArrayList<Int>){
        var winningAmount = 0
        for(ticket in slotData){
            for(slotVals in ticket.slots)
              for(j in winningTicket ){
                if(slotVals == j){
                    winningAmount++
                }
            }
            slotData[slotData.indexOf(ticket)].winningNumbers = winningAmount
            Log.d(TAG, "winningAmount: ${slotData[slotData.indexOf(ticket)].winningNumbers}")
        }

    }
}