package com.thomasafg.stopgame

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class VolleySingleton private constructor(context: Context) {
    val requestQueue: RequestQueue = Volley.newRequestQueue(context)

    companion object {
        private var mInstance: VolleySingleton? = null
        fun getInstance(
            context: Context
        ): VolleySingleton? {
            if (mInstance == null) {
                mInstance = VolleySingleton(context)
            }
            return mInstance
        }
    }
}