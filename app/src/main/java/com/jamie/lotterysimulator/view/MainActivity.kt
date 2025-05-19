package com.jamie.lotterysimulator.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.jamie.lotterysimulator.R
import com.jamie.lotterysimulator.databinding.ActivityMainBinding
import com.jamie.lotterysimulator.model.Constants.SlotList
import com.jamie.lotterysimulator.model.Slot
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    companion object{
        private const val TAG = "MainActivity"
        private const val AD_UNIT_ID ="ca-app-pub-5672195872456028/7453456324"
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

    private lateinit var slotData:ArrayList<Slot>

    private var bottomAdView: AdView? = null

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

        initMobileAds()//method that initializes the ads
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



        var randomNumberArray:ArrayList<Int> = arrayListOf()//generates numbers between one and 36
        randomNumberArray =generateWinningTicket(randomNumberArray)
        for (random in randomNumberArray){
            Log.d(TAG, "init: randomness: $random")
        }
        SlotList.setWinningTicket(randomNumberArray)//creates the winning when the user first enters


        var winningTicket = SlotList.getWinningTicket()
        winningAmount(winningTicket)
        slotOne.text = winningTicket[0].toString()
        slotTwo.text = winningTicket[1].toString()
        slotThree.text = winningTicket[2].toString()
        slotFour.text = winningTicket[3].toString()
        slotFive.text = winningTicket[4].toString()


        generateBtn.setOnClickListener {
            randomNumberArray = generateWinningTicket(randomNumberArray)
            winningTicket = randomNumberArray
            slotOne.text = winningTicket[0].toString()
            slotTwo.text = winningTicket[1].toString()
            slotThree.text = winningTicket[2].toString()
            slotFour.text = winningTicket[3].toString()
            slotFive.text = winningTicket[4].toString()
            winningAmount(winningTicket)
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
            if (winningAmount == 5){
                var popup = Toast.makeText(this, "winner winner chicken dinner", Toast.LENGTH_LONG)
                popup.show()
            }
            winningAmount=0
            Log.d(TAG, "winningAmount: ${slotData[slotData.indexOf(ticket)].winningNumbers}")
            for((key, value) in slotData.withIndex()){
                Log.d(TAG, "----------------------------")
                Log.d(TAG, "winningAmount: key: $key")
                Log.d(TAG, "winningAmount: value: $value")
                Log.d(TAG, "----------------------------")
            }
        }



    }

    private fun generateWinningTicket( randomNumberArray:ArrayList<Int>):ArrayList<Int>{
        randomNumberArray.clear()
        while(randomNumberArray.size <5){
            val randomNumber  =  Random.nextInt(1,36)
            if(!randomNumberArray.contains(randomNumber)){
                randomNumberArray.add(randomNumber)

            }
        }
        return randomNumberArray
    }

    private fun initAd(){


        // Create a new ad view.
        val bottomAdView = AdView(this)
        bottomAdView.adUnitId = AD_UNIT_ID
        // Request an anchored adaptive banner with a width of 360.
        bottomAdView.setAdSize(AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, 360))
        this.bottomAdView = bottomAdView

        // Replace ad container with new ad view.
        binding.bottomAdContainer.removeAllViews()
        binding.bottomAdContainer.addView(bottomAdView)

        val adRequest = AdRequest.Builder().build()
        bottomAdView.loadAd(adRequest)

         }

    private fun initMobileAds() {
        CoroutineScope(Dispatchers.IO).launch {
            // Initialize the Google Mobile Ads SDK on a background thread.
            MobileAds.initialize(this@MainActivity) {}
            // [START_EXCLUDE silent]
            runOnUiThread {
                // Load an ad on the main thread.
                initAd()
            }
        }
    }
}