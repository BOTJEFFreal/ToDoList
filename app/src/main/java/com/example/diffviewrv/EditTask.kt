package com.example.diffviewrv

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.diffviewrv.Data.Data

class EditTask : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    var status = "Not Done"
    var duration = " "
    var mainDate = " "
    var weekDayName = " "
    var simpleDateID = " "
    //var dayDatevisibilityRev = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_task)

        val tvTitleTextInput = findViewById<TextView>(R.id.tvTitleTextInput)
        val tvDescTextInput = findViewById<TextView>(R.id.tvDescTextInput)
        val btnCancel = findViewById<Button>(R.id.btnCancel)
        val btnSave = findViewById<Button>(R.id.btnSave)
        val tvTaskStartDate = findViewById<TextView>(R.id.tvTaskStartDate)
        val tvDone = findViewById<TextView>(R.id.tvDone)

        val bundle: Bundle? = intent.extras
        val heading = bundle!!.getString("heading")
        val desc = bundle!!.getString("desc")
        duration = bundle!!.getString("duration").toString()
        val done = bundle!!.getString("done")
        mainDate = bundle!!.getString("mainDate").toString()
        weekDayName = bundle!!.getString("weekDayName").toString()
        val simpleDateID= bundle!!.getString("simpleDateID").toString()



        tvTitleTextInput.text = heading.toString()
        tvDescTextInput.text = desc.toString()
        tvTaskStartDate.text = duration
        tvDone.text = done.toString()

        tvDone.setOnClickListener{
            if(tvDone.text == "Done"){
            tvDone.text = "Not Done"
            status ="Not Done"
            }
            else{
                tvDone.text = "Done"
                status ="Done"
            }
        }

        btnSave.setOnClickListener {
            val titleText: String = tvTitleTextInput.text.toString()
            val descText: String = tvDescTextInput.text.toString()
            val dayDatevisibilityRev = bundle!!.getString("dayDatevisibilityRev").toBoolean()


            if(titleText != ""){

                val addedData = Data(RecyclerViewAdapter.VIEW_ACTIVITY,
                    titleText,
                    dayDatevisibilityRev,
                    status,
                    descText,
                    duration,
                    mainDate,
                    weekDayName,
                    simpleDateID,
                "editTask")
                val result = Intent()
                result.putExtra(DATA,addedData)
                setResult(Activity.RESULT_OK,result)
                finish()
            }
            else{
                Toast.makeText(this, "Enter Activity Name", Toast.LENGTH_SHORT).show()
            }
        }
        btnCancel.setOnClickListener{
            finish()
        }
    }
}