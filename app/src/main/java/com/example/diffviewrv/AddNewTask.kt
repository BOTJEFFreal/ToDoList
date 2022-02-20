package com.example.diffviewrv

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.diffviewrv.Data.Data
import java.text.SimpleDateFormat
import java.util.*

class AddNewTask : AppCompatActivity(){

    var startHoursaved = 0
    var startMinutesaved = 0
    var endHoursaved = 0
    var endMinutesaved = 0
    var daysaved = " "
    var weekDayNamesaved = " "
    var textsimpleDateID = " "

    val monthName = arrayListOf<String>("JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE", "JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER")
    val dayName = arrayListOf<String>("SUN","MON","TUE","WED","THU","FRI","SAT")

    val calendar = Calendar.getInstance()
    
    var curretMonth = Calendar.getInstance().get(Calendar.MONTH)
    var curretDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
    var curretYear = Calendar.getInstance().get(Calendar.YEAR)
    val curretHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    val curretMinute = Calendar.getInstance().get(Calendar.MINUTE)
    val currentDayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_task)

        val tvTitleTextInput = findViewById<TextView>(R.id.tvTitleTextInput)
        val tvDescTextInput = findViewById<TextView>(R.id.tvDescTextInput)
        val btnCancel = findViewById<Button>(R.id.btnCancel)
        val btnSave = findViewById<Button>(R.id.btnSave)
        val tvTaskStartTime = findViewById<TextView>(R.id.tvTaskStartTime)
        val tvTaskStopTime = findViewById<TextView>(R.id.tvTaskStopTime)
        val tvTaskStartDate = findViewById<TextView>(R.id.tvTaskStartDate)

        

        tvTaskStartDate.setText("${monthName[curretMonth]} ${curretDay}th, $curretYear")
        tvTaskStartTime.setText("${curretHour}:${curretMinute}")
        tvTaskStopTime.setText("${curretHour+1}:${curretMinute}")

        startHoursaved =curretHour
        startMinutesaved =curretMinute
        endHoursaved = curretHour + 1
        endMinutesaved = curretMinute
        daysaved = curretDay.toString()
        weekDayNamesaved = dayName[currentDayOfWeek-1]



        tvTaskStartTime.setOnClickListener{
            val timeSetListner =TimePickerDialog.OnTimeSetListener { timePicker, startHour, startMinute ->
                calendar.set(Calendar.HOUR_OF_DAY,startHour)
                calendar.set(Calendar.MINUTE,startMinute)
            tvTaskStartTime.text = SimpleDateFormat("HH:mm").format(calendar.time)
                if(startHour ==23){
                    tvTaskStopTime.text = "00:$startMinute"
                }else{ tvTaskStopTime.text = "${startHour+1}:$startMinute" }

                startHoursaved = startHour
                startMinutesaved = startMinute
                if(startHoursaved == 23){endHoursaved = 0}else{endHoursaved = startHour + 1}
                endMinutesaved = startMinute
            }

            TimePickerDialog(this,timeSetListner, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show()

        }

        tvTaskStopTime.setOnClickListener{
            val timeSetListner =TimePickerDialog.OnTimeSetListener { timePicker, endHour, endMinute ->
                calendar.set(Calendar.HOUR_OF_DAY,endHour)
                calendar.set(Calendar.MINUTE,endMinute)
                tvTaskStopTime.text = SimpleDateFormat("HH:mm").format(calendar.time)

                endHoursaved = endHour
                endMinutesaved = endMinute

                val timediff = (endHoursaved*60 + endMinutesaved) - (startHoursaved*60 + startHoursaved)
                if(timediff<=0){
                    Toast.makeText(this, "Set time duration correctly", Toast.LENGTH_SHORT).show()
                    tvTaskStopTime.text = "${startHoursaved + 1}:$startMinutesaved"
                }

            }

            TimePickerDialog(this,timeSetListner, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show()
        }

        tvTaskStartDate.setOnClickListener{
            val dateSetListner = DatePickerDialog.OnDateSetListener { datePicker, dateYear,dateMonth,dateDay ->
                calendar.set(Calendar.YEAR,dateYear)
                calendar.set(Calendar.MONTH,dateMonth)
                calendar.set(Calendar.DAY_OF_MONTH,dateDay)
                
                if(curretYear <= dateYear && curretMonth <= dateMonth && curretDay <= dateDay){
                    
                    tvTaskStartDate.setText("${monthName[dateMonth]} ${dateDay}th, $dateYear")

                    daysaved = dateDay.toString()
                    curretYear = dateYear
                    curretMonth = dateMonth
                    weekDayNamesaved = dayName[dayWeekName(dateDay,dateMonth,dateYear)]
            }
                else{
                    Toast.makeText(this, "Check the date entered", Toast.LENGTH_SHORT).show()
                }
                    
                

            }

            DatePickerDialog(this,dateSetListner,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show()

        }

        btnSave.setOnClickListener {
            val titleText: String = tvTitleTextInput.text.toString()
            val descText: String = tvDescTextInput.text.toString()
            //TO MAKE THE DATE ID THING
            if(startHoursaved <9){
                var x =startHoursaved.toString()
                textsimpleDateID = curretYear.toString() + curretMonth.toString() + daysaved +"0"+x
                Toast.makeText(this, textsimpleDateID, Toast.LENGTH_SHORT).show()
            }else{
                textsimpleDateID = curretYear.toString() + curretMonth.toString() + daysaved +startHoursaved.toString()}
            //Toast.makeText(this, textsimpleDateID, Toast.LENGTH_SHORT).show()
            if(titleText != ""){

                val addedData = Data(RecyclerViewAdapter.VIEW_ACTIVITY,
                    titleText,
                    false,
                    "Not Done",
                    descText,
                    durationCal(startHoursaved,startMinutesaved, endHoursaved, endMinutesaved),
                    daysaved,
                    weekDayNamesaved,
                    textsimpleDateID,
                "addNewTask")
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
    private fun durationCal(startHour:Int, startMinute:Int, endHour:Int, endMinute:Int): String{
        var timespan: String
        if(startHour == 23){
            timespan = "Duration: $startHour:$startMinute - Tomorrow"
        }
        else{
            timespan = "Duration: $startHour:$startMinute - $endHour:$endMinute"
        }
        return timespan
    }
    private fun dayWeekName(day:Int, month:Int, year:Int): Int{
        var m = month+1
        var y = year
        val t = listOf( 0, 3, 2, 5, 0, 3, 5, 1, 4, 6, 2, 4)
        if(m<3){
            y -= 1
        }

        return (( y + (y / 4) - (y / 100) + (y / 400) + t[m - 1] + day) % 7)
    }

}