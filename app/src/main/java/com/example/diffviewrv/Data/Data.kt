package com.example.diffviewrv.Data

import java.io.Serializable

data class Data(var viewType: Int,
                var textTitle: String,
                var dayDatevisibilityRev: Boolean,
                var textDone: String = "Done",
                var textDesc: String,
                var textDuration: String,
                var textMainDate: String,
                var textWeekDayName: String,
                var simpleDateID: String,
                var dataTransfer: String
                ) : Serializable{









                }
