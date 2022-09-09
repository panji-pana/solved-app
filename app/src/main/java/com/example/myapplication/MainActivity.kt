package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.get

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var buttons: Array<Button> = arrayOf()

        val a = findViewById<ConstraintLayout>(R.id.main)

        for(i in 2..6){
            val b = a[i] as TableLayout
            for(j in 0..1){
                val c = (b[j] as TableRow)[0] as LinearLayout
                for(k in 0 until c.childCount){
                    buttons += c[k] as Button
                }
            }
        }

        val spinner = findViewById<Spinner>(R.id.spinner)
        var m: Int
        var n: Int
        var sValue = 'e'
        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                // Sets everything so it is unable to be used
                buttons.forEach{it.isEnabled=false}
                findViewById<TableLayout>(R.id.tableDigits).alpha= 0F
                findViewById<TableLayout>(R.id.tableOperations).alpha= 0F
                findViewById<TableLayout>(R.id.tableFunctions1).alpha= 0F
                findViewById<TableLayout>(R.id.tableFunctions2).alpha= 0F
                findViewById<TableLayout>(R.id.tableConstants).alpha= 0F

                when(p0?.getItemAtPosition(p2).toString()){
                    "Digits"->{
                        m=0
                        n=9
                        sValue='d'
                        findViewById<TableLayout>(R.id.tableDigits).alpha= 1F
                        findViewById<TableLayout>(R.id.tableDigits).bringToFront()
                    }
                    "Operations"->{
                        m=10
                        n=16
                        sValue='o'
                        findViewById<TableLayout>(R.id.tableOperations).alpha= 1F
                        findViewById<TableLayout>(R.id.tableOperations).bringToFront()
                    }
                    "Functions 1"->{
                        m=17
                        n=26
                        sValue='1'
                        findViewById<TableLayout>(R.id.tableFunctions1).alpha= 1F
                        findViewById<TableLayout>(R.id.tableFunctions1).bringToFront()
                    }
                    "Functions 2"->{
                        m=27
                        n=36
                        sValue='2'
                        findViewById<TableLayout>(R.id.tableFunctions2).alpha= 1F
                        findViewById<TableLayout>(R.id.tableFunctions2).bringToFront()
                    }
                    "Constants"->{
                        m=37
                        n=46
                        sValue='c'
                        findViewById<TableLayout>(R.id.tableConstants).alpha= 1F
                        findViewById<TableLayout>(R.id.tableConstants).bringToFront()
                    }
                    else->{
                        m=0
                        n=buttons.size-1
                        sValue='e'
                    }

                }

                for(i in m..n){
                    println(buttons[i])
                    buttons[i].isEnabled = true
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}

        }

        var output = ""
        buttons.forEach {button->
            button.setOnClickListener {
                output += buttonClicked(b = button, s = sValue)
                findViewById<TextView>(R.id.output).text = output
            }
        }
    }

    private fun buttonClicked(b: Button, s: Char): String {
        return when (s) {
            'd' -> { // digits
                b.text as String
            }
            '1', '2' -> { // functions
                " ${b.text} ( "
            }
            'o', 'c' -> { // operations and constants
                " ${b.text} "
            }
            else -> {
                ""
            }
        }
    }
    
    private fun guideClicked(){

    }


}