package com.thomasafg.stopgame

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.reflect.Type
import java.text.Normalizer
import android.media.MediaPlayer
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager


class MainActivity : AppCompatActivity() {

    data class City(val city_name:String)

    private fun searchCity(){
        val citySearch: String = editNomeCidade.text.toString()
        val key = "ea14e5fb"
        val url = "https://api.hgbrasil.com/weather?array_limit=1&fields=only_results,city_name&key=${key}&city_name=${citySearch}"
        val queue = Volley.newRequestQueue(this)
        btnVerificar.isEnabled = false
        txtChecagem.text = getString(R.string.txt_loading)
        txtChecagem.setTextColor(ContextCompat.getColorStateList(this, R.color.teal_200))
        val stringRequest = StringRequest(Request.Method.GET, url,{
                response ->
                    val type: Type? = object : TypeToken<City>() {}.type
                    val city = GsonBuilder().create().fromJson(response, type) as City
                    if (cleanString(citySearch) == cleanString(city.city_name)){
                        txtChecagem.text = getString(R.string.txt_valido)
                        txtChecagem.setTextColor(ContextCompat.getColorStateList(this, R.color.green))
                        MediaPlayer.create(this, R.raw.acertou).start()
                        btnVerificar.isEnabled = true
                    }else{
                        txtChecagem.text = getString(R.string.txt_invalido)
                        txtChecagem.setTextColor(ContextCompat.getColorStateList(this, R.color.red))
                        MediaPlayer.create(this, R.raw.errou).start()
                        btnVerificar.isEnabled = true
                    }
        }) {
            btnVerificar.isEnabled = true
            Toast.makeText(this@MainActivity, "Consultas do dia expiradas!", Toast.LENGTH_LONG).show()
        }
        queue.add(stringRequest)
    }

    private fun cleanString(str: String): String {
        return Normalizer.normalize(str, Normalizer.Form.NFD).replace("[^\\p{ASCII}]", "").uppercase()
    }

    private fun sortChar(){
        btnStop.isEnabled = false
        val timer = object: CountDownTimer(5000, 300) {
            override fun onTick(millisUntilFinished: Long) {
                textLetra.text = (('A'..'Z')).random().toString()
            }
            override fun onFinish() {
                btnStop.isEnabled = true
            }
        }
        timer.start()
    }

    private fun hideSoftKeyBoard(context: Context, view: View) {
        try {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        } catch (e: Exception) {
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        txtChecagem.text = getString(R.string.txt_loading)
        txtChecagem.setTextColor(ContextCompat.getColorStateList(this, R.color.teal_200))
        btnStop.setOnClickListener {
            sortChar()
        }
        editNomeCidade.isSingleLine = true
        editNomeCidade.setOnKeyListener { _, keyCode, keyEvent ->
            if (keyEvent.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                searchCity()
                hideSoftKeyBoard(this, editNomeCidade)
                true
            } else false
        }

        btnVerificar.setOnClickListener {
            searchCity()
        }
    }
}