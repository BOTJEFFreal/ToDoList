package com.example.diffviewrv

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diffviewrv.Data.Data

const val DATA = "Data"

class MainActivity : AppCompatActivity() {

    companion object{
        private const val REQUEST_CODE = 233
    }

    private lateinit var recyclerView: RecyclerView
    var dataList = ArrayList<Data>()
    lateinit var adapter: RecyclerViewAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.rvMain)

        dataList.add(Data(RecyclerViewAdapter.VIEW_ADD_BUTTON, "Na",true,"Na","na","na","na","na","na","na"))

        adapter = RecyclerViewAdapter(this, dataList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        //To click the add activity button view
        adapter.setOnItemClickListener(object: RecyclerViewAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                val dataListPosition = dataList[position]
                if (dataListPosition.viewType == 2){
                Intent(this@MainActivity, AddNewTask::class.java).also{
                    startActivityForResult(it, REQUEST_CODE) }
                }
                else{
                    val editIntent =Intent(this@MainActivity, EditTask::class.java)
                    editIntent.putExtra("heading",dataListPosition.textTitle)
                    editIntent.putExtra("desc",dataListPosition.textDesc)
                    editIntent.putExtra("duration",dataListPosition.textDuration)
                    editIntent.putExtra("done",dataListPosition.textDone)
                    editIntent.putExtra("mainDate",dataListPosition.textMainDate)
                    editIntent.putExtra("MainDatetoggle",dataListPosition.dayDatevisibilityRev)
                    editIntent.putExtra("weekDayName",dataListPosition.textWeekDayName)
                    editIntent.putExtra("simpleDateID",dataListPosition.simpleDateID)
                    editIntent.putExtra("dayDatevisibilityRev",dataListPosition.dayDatevisibilityRev)
                    startActivityForResult(editIntent, REQUEST_CODE)
                }
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, info: Intent?) {
        var except = 0

        if(requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK){
            val data = info?.getSerializableExtra(DATA) as Data
            val similarTextTitle: String = data.textTitle
            val similarMainDate: String = data.textMainDate
            val similarDateID: String = data.simpleDateID
            val similarDataTransfer: String = data.dataTransfer


            if(similarDataTransfer == "addNewTask"){
                for(i in 0..dataList.size-1) {
                    if (similarMainDate == dataList[i].textMainDate){
                        data.dayDatevisibilityRev = true
                    }}

                for(i in 0..dataList.size-2){
                    if(similarTextTitle == dataList[i].textTitle){
                        dataList[i] = data
                        except+=1
                        break
                    }}}
            if(similarDataTransfer == "editTask"){
                for(i in 0..dataList.size-2){
                    if(similarDateID == dataList[i].simpleDateID){
                        dataList[i] = data
                        except+=1
                        break
                    }}
            }

            if(except == 0){
            dataList.add(dataList.size-1,data)}

            dataList.sortBy {
                it.simpleDateID
            }
            Log.e("finAL",dataList.toString())
            adapter.notifyDataSetChanged()
        }
        super.onActivityResult(requestCode, resultCode, info)
    }

}