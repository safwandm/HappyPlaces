package com.example.happyplaces.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.happyplaces.GoogleMapsActivity
import com.example.happyplaces.R
import com.example.happyplaces.model.HappyPlaceModel
import kotlinx.android.synthetic.main.activity_happy_detail.*
import kotlinx.android.synthetic.main.activity_happy_detail.*

class DetailActivity : AppCompatActivity() {

    private var HappyDetailActivity: HappyPlaceModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_happy_detail)

        HappyDetailActivity =
            intent.getParcelableExtra(MainActivity.EXTRA_PLACE_DETAILS)

        HappyDetailActivity?.let {
            setSupportActionBar(toolbar_detail_place)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = it.title

            toolbar_detail_place.setNavigationOnClickListener {
                onBackPressed()
            }

            iv_place_image.setImageURI(Uri.parse(it.image))
            tv_description.text = it.description
            tv_location.text = it.location

            btn_view_on_map.setOnClickListener {
                val intent = Intent(this, GoogleMapsActivity::class.java)
                intent.putExtra(MainActivity.EXTRA_PLACE_DETAILS, HappyDetailActivity)
                startActivity(intent)
            }
        }
    }
}
