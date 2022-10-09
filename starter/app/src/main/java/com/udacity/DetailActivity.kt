package com.udacity

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    companion object {
        const val FILE_NAME = "FILE_NAME"
        const val FILE_STATUS = "FILE_STATUS"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val fileName = findViewById<TextView>(R.id.file_name_text)
        val fileStatus = findViewById<TextView>(R.id.file_status_text)
        val okButton = findViewById<Button>(R.id.ok_button)

      //  fileName.text = savedInstanceState!!.getString(FILE_NAME)
        //fileStatus.text = getString(savedInstanceState!!.getInt(FILE_STATUS))

        okButton.setOnClickListener { onNavigateUp() }
        setSupportActionBar(toolbar)
    }


}
