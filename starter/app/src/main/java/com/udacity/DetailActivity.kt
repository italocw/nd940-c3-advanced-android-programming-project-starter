package com.udacity

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
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
        val fileStatus = findViewById<StatusTextView>(R.id.file_status_text)

        fileName.text = intent.extras!!.getString(FILE_NAME)
        fileStatus.status = intent.extras!!.getInt(FILE_STATUS)

        val motionLayout = findViewById<MotionLayout>(R.id.content_detail_motion_layout)

        motionLayout.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(motionLayout: MotionLayout, i: Int, i1: Int) {}

            override fun onTransitionChange(
                motionLayout: MotionLayout,
                i: Int,
                i1: Int,
                v: Float
            ) {
            }

            override fun onTransitionCompleted(motionLayout: MotionLayout, i: Int) {
                finish()
            }

            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {}
        })

        setSupportActionBar(toolbar)
    }

}
