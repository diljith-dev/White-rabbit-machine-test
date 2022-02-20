package com.diljith.whiterabbit

import com.diljith.whiterabbit.model.AddressData
import com.diljith.whiterabbit.model.Company
import com.google.gson.Gson




class Utils {

    fun wrapData(data: AddressData?): String? {
        if(data==null)
        {
            return ""
        }
        val gson = Gson()
        return gson.toJson(data)
    }
    fun wrapData(data: Company?): String? {
        if(data==null)
        {
            return ""
        }
        val gson = Gson()
        return gson.toJson(data)
    }

    fun getAddress(v: String): AddressData? {
        return Gson().fromJson(v, AddressData::class.java)
    }
    fun getCompany(v: String): Company? {
        return Gson().fromJson(v, Company::class.java)
    }

}