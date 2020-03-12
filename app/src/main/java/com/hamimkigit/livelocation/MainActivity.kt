package com.hamimkigit.livelocation

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.*
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var tvLatitude: TextView
    private lateinit var tvLongitude: TextView
    private lateinit var tvAddress: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvLatitude = findViewById(R.id.tvLatitude)
        tvLongitude = findViewById(R.id.tvLongitude)
        tvAddress = findViewById(R.id.tvAddress)

        Log.d("MainActivityTag","Activity called")


        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            return
        }
        val location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        Log.d("MainActivityTag","Location is null")

        if (location != null) {
            Log.d("MainActivityTag","Location "+location.latitude)
            val geocoder = Geocoder(this)
            val addressList: List<Address>
            try {
                tvLatitude.text = (location.latitude.toString())
                tvLongitude.text = (location.longitude.toString())
                addressList = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                tvAddress.text = addressList[0].getAddressLine(0)
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
        locationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f, llistener)
    }

    private val llistener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            val geocoder = Geocoder(this@MainActivity)
            val addressList: List<Address>
            try {
                tvLatitude.text = (location.latitude.toString())
                tvLongitude.text = (location.longitude.toString())
                addressList = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                tvAddress.text = addressList[0].getAddressLine(0)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }
}
