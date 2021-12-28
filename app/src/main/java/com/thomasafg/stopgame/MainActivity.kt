package com.thomasafg.stopgame

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

class MainActivity : AppCompatActivity() {

    data class City(val city_name:String)

    private fun searchCity(){
        val citySearch: String = editNomeCidade.text.toString()
        val key: String = "e66fe6f9"
        val url = "https://api.hgbrasil.com/weather?array_limit=1&fields=only_results,city_name&key=${key}&city_name=${citySearch}"
        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(Request.Method.GET, url,{
                response ->
                    val type: Type? = object : TypeToken<City>() {}.type
                    val city = GsonBuilder().create().fromJson(response, type) as City
                    if (cleanString(citySearch) == cleanString(city.city_name)){
                        textLetra.text = "Válido"
                        textLetra.setTextColor(ContextCompat.getColorStateList(this, R.color.green))
                    }else{
                        textLetra.text = "Inválido"
                        textLetra.setTextColor(ContextCompat.getColorStateList(this, R.color.red))
                    }
        }) {
            Toast.makeText(this@MainActivity, "Consultas do dia expiradas!", Toast.LENGTH_LONG).show()
        }
        queue.add(stringRequest)
    }

    private fun cleanString(str: String): String {
        return Normalizer.normalize(str, Normalizer.Form.NFD).replace("[^\\p{ASCII}]", "")
    }

    private fun sortChar(){
        btnStop.setEnabled(false)
        //postDelayed
            textLetra.text = (('A'..'Z')).random().toString()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnStop.setOnClickListener {
            sortChar()
        }

        btnVerificar.setOnClickListener {
            searchCity()
        }
    }
}