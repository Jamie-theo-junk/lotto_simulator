package com.jamie.lotterysimulator.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jamie.lotterysimulator.R
import com.jamie.lotterysimulator.databinding.ActivityAddSlotBinding
import com.jamie.lotterysimulator.model.Constants.SlotList

class AddSlot : AppCompatActivity(), OnClickListener{
    private lateinit var binding: ActivityAddSlotBinding

    private lateinit var slotOne: CardView
    private lateinit var slotTwo: CardView
    private lateinit var slotThree: CardView
    private lateinit var slotFour: CardView
    private lateinit var slotFive: CardView
    private lateinit var slotSix:CardView
    private lateinit var context: Context

    private lateinit var confirmButton:CardView

    private lateinit var cardView: CardView

    private lateinit var chosenSlots:List<CardView>



    private lateinit var selectedNumbers: HashMap<Int, Int>
    private var _selectedNumberCount = 0
    private var selectedNumberCount:Int
        get() = _selectedNumberCount
        set(value) {
            _selectedNumberCount = value
            if (value == 6) {
                binding.confirmButton.visibility = View.VISIBLE
            } else {
                binding.confirmButton.visibility = View.GONE
            }
        }

    companion object{
        private const val TAG = "AddSlot"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAddSlotBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        init(this)

    }

    private fun init(context: Context) {
        confirmButton = binding.confirmButton
        confirmButton.setOnClickListener(this)

        Log.d(TAG, "init: entered the init method")
        selectedNumbers = hashMapOf(
            1 to 0,
            2 to 0,
            3 to 0,
            4 to 0,
            5 to 0,
            6 to 0
        )//initiates the selectedNumbers hash map



        for ((key, value) in selectedNumbers) {
            Log.d(TAG, "init KEY: $key")
            Log.d(TAG,"init VALUE: $value")
        }//test to see if the values are as i made them


        slotOne = binding.chosenCard1
        slotTwo = binding.chosenCard2
        slotThree = binding.chosenCard3
        slotFour = binding.chosenCard4
        slotFive = binding.chosenCard5
        slotSix = binding.chosenCard6


        chosenSlots = listOf(slotOne,slotTwo,slotThree,slotFour,slotFive,slotSix)//puts the chosen slots in a list

        for(slot in chosenSlots){
            slot.visibility = View.INVISIBLE

        }

    }



    fun slotNumbers(view: View) {
        Log.d(TAG, "slotNumbers: entered the method")

        cardView = view as CardView

        val id = view.id
        val name = resources.getResourceEntryName(id)
        val slotNumber = name.filter { it.isDigit() }.toInt() // Extract number from ID name
        Log.d(TAG, "slotNumbers: $slotNumber")
        // Check if the slotNumber is already selected
        val existingKey = selectedNumbers.entries.find { it.value == slotNumber }?.key
        Log.d(TAG, "slotNumbers:Existing Key $existingKey")
        if (existingKey != null && existingKey!= 0) {
            Log.d(TAG,"slotNumbers: Entered the existing key if statement")
            // Deselect the number
            selectedNumbers[existingKey] = 0
            selectedNumberCount--
            val resId = resources.getIdentifier("chosen_${existingKey}_text", "id", packageName)
            val textPick = findViewById<TextView>(resId)
            textPick.text = ""

            chosenSlots[existingKey - 1].visibility = View.INVISIBLE

            cardView.setCardBackgroundColor(
                ContextCompat.getColor(this, R.color.grey)
            )

            Log.d(TAG, "Deselected slotNumber $slotNumber from key $existingKey")
        }
        else if (selectedNumberCount < 6) {
            // Find first available key (with value 0)
            val availableKey = selectedNumbers.entries.find { it.value == 0 }?.key
            Log.d(TAG, "slotNumbers: available key: $availableKey")
            if (availableKey != null) {
                Log.d(TAG, "slotNumbers: Entered available key if STATEMENT")
                selectedNumbers[availableKey] = slotNumber

                val resId = resources.getIdentifier("chosen_${availableKey}_text", "id", packageName)
                val textPick = findViewById<TextView>(resId)
                textPick.text = slotNumber.toString()

                chosenSlots[availableKey-1].visibility = View.VISIBLE
                selectedNumberCount++
                cardView.setCardBackgroundColor(
                    ContextCompat.getColor(this, R.color.light_yellow)
                )

                Log.d(TAG, "Selected slotNumber $slotNumber into key $availableKey")
            } else {
                Log.d(TAG, "No available slot found")
            }
        } else {
            Log.d(TAG, "Maximum selection reached or already selected")
        }
        Log.d(TAG, "slotNumbers: count $selectedNumberCount")
        Log.d(TAG, "slotNumbers: ------------------------")


    }

    override fun onClick(view: View?) {
        var chosenValues: ArrayList<Int> = arrayListOf()
        for((key,value) in selectedNumbers){
             chosenValues.add(value)
        }

        SlotList.setSlotNumbers(chosenValues)
        val slot = SlotList.getSlotNumbers()
        for ((slotNo, winning) in slot.withIndex()){
            Log.d(TAG, "onClick: slotNo $slotNo")
            Log.d(TAG, "onClick: winning $winning")
        }
        val toMainActivity = Intent(this, MainActivity::class.java)
        startActivity(toMainActivity)
    }


}
