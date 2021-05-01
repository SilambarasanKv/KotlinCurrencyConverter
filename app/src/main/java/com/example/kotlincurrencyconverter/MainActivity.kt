@file:Suppress("DEPRECATION")

package com.example.kotlincurrencyconverter

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fun get (view: View) {

            val downloadData = Download()

            try {

                val url = "http://data.fixer.io/api/latest?access_key=712ee312dea9cfc8fd7976c836bf8dbb&format=1"
                val chosenBase = editText.text.toString()

                downloadData.execute(url+chosenBase)

            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

    }

    inner class Download : AsyncTask<String,Void,String>() {

        override fun doInBackground(vararg params: String?): String {

            var result = ""
            var url: URL
            val httpURLConnection: HttpURLConnection

            try {

                url = URL(params[0])
                httpURLConnection = url.openConnection() as HttpURLConnection
                val inputStream = httpURLConnection.inputStream
                val inputStreamReader = InputStreamReader(inputStream)

                var data = inputStreamReader.read()

                while (data > 0) {
                    val character = data.toChar()
                    result += character

                    data = inputStreamReader.read()

                }

                return result

            } catch (e: Exception) {
                e.printStackTrace()
                return result
            }
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)

            try {
                val jSONObject = JSONObject(result)
                println(jSONObject)
                val base = jSONObject.getString("base")
                println(base)
                val date = jSONObject.getString("date")
                println(date)
                val rates = jSONObject.getString("rate")
                println(rates)

                val newjSONObject = JSONObject(rates)
                val chf = newjSONObject.getString("CHF")
                println(chf)
                val czk = newjSONObject.getString("CZK")
                val tl = newjSONObject.getString("TRY")

                tryText.text = "CHF: " + chf
                czkText.text = "CZK: " + czk
                tryText.text = "TRY: " + tl

            } catch(e: Exception) {
                e.printStackTrace()
            }



        }

    }

}