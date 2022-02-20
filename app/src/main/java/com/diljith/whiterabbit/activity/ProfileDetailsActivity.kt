package com.diljith.whiterabbit.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.diljith.whiterabbit.R
import com.diljith.whiterabbit.model.Employee
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile_details.*

class ProfileDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_details)
        setProfile()
    }

    private fun getData(): Employee? {
        val extras = intent.extras
        if (extras != null) {
            return extras.getParcelable<Employee>("data")
        }
        return null
    }

    @SuppressLint("SetTextI18n")
    private fun setProfile() {
        val d = getData()
        if (d != null) {
            tvEmpName.text = d.name
            tvUserName.text = "User name : " + d.username
            tvEmail.text = "E mail : " + d.email
            tvPhone.text = "Phone : " + d.phone
            tvWebSite.text = "Web site : " + d.website

            if (d.address != null) {
                tvStreet.text = "Street : " + d.address.street
                tvSuite.text = "Suite : " + d.address.suite
                tvPinCode.text = "Zip code : " + d.address.zipcode
                tvCity.text = "City : " + d.address.city
            }

            if (d.company != null) {
                tvCompanyName.text = "Name : " + d.company.name
                tvBs.text = "Catch phrase : " + d.company.catchPhrase
                tvCatchPhrase.text = "BS : " + d.company.catchPhrase
            }

            val builder = Picasso.Builder(this)
            builder.listener { picasso, uri, exception ->

                exception.printStackTrace()
            }
            builder.build()
                .load(d.profile_image)
                .error(R.drawable.ic_baseline_camera_alt_24)
                .placeholder(R.drawable.ic_baseline_camera_alt_24)
                .into(icProfile, object : Callback.EmptyCallback() {
                    override fun onSuccess() {
                        //  progressBar.setVisibility(View.GONE)
                    }

                    override fun onError() {


                    }
                })

        }

    }
}