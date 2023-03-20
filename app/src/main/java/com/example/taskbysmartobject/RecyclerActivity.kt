package com.example.taskbysmartobject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskbysmartobject.databinding.ActivityRecyclerBinding
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class RecyclerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecyclerBinding
    var list= mutableListOf<String>()
    private var mInterstitialAd: InterstitialAd? = null
    lateinit var adRequest:AdRequest


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityRecyclerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        list.add("Pakistan")
        list.add("Afghanistan")
        list.add("Malaysia")
        list.add("India")
        list.add("Sri Lanka")
        list.add("Bangladesh")
        list.add("England")
        list.add("Qatar")
        list.add("Italy")
        list.add("Germany")

        binding.backbtn.setOnClickListener {
            onBackPressed()
            finish()
        }

        adRequest=AdRequest.Builder().build()
//        loadInterstitialAd()

    }

    override fun onStart() {
        super.onStart()

        binding.waqarRecView.apply {
            layoutManager=LinearLayoutManager(this@RecyclerActivity)
            adapter=CountryAdapter(list){
                    loadInterstitialAd()
            }
        }
    }
    private fun loadInterstitialAd() {
        InterstitialAd.load(this,getString(R.string.inter_ad_unit_id),adRequest,object :
            InterstitialAdLoadCallback(){
            override fun onAdFailedToLoad(adError: LoadAdError) {
                super.onAdFailedToLoad(adError)
                Log.d("waqar", "onAdFailedToLoad: ${adError.responseInfo}")
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                super.onAdLoaded(interstitialAd)
                Log.d("waqar", "onAdLoaded: ${interstitialAd.adUnitId}")

                interstitialAd.show(this@RecyclerActivity)

            }
        })
    }
}