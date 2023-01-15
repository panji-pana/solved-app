package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.get
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var undoStack = Stack<Int>()
        var redoStack = Stack<String>()
        redoOrUndoChanged(undoStack,redoStack)

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

                redoStack = Stack<String>()
                undoStack.push(button.text.length)

                redoOrUndoChanged(undoStack,redoStack)
            }
        }


        findViewById<ImageButton>(R.id.buttonUndo).setOnClickListener {
            val undoReturn = undo(undoStack, output)
            undoStack = undoReturn.first
            redoStack.push(undoReturn.second)
            output = undoReturn.third
            findViewById<TextView>(R.id.output).text = output
            redoOrUndoChanged(undoStack,redoStack)
        }

        findViewById<ImageButton>(R.id.buttonRedo).setOnClickListener {
            val redoReturn = redo(redoStack, output)
            redoStack = redoReturn.first
            output = redoReturn.second
            undoStack.push(redoReturn.third)
            findViewById<TextView>(R.id.output).text = output
            redoOrUndoChanged(undoStack,redoStack)
        }

        findViewById<ImageButton>(R.id.buttonHelp).setOnClickListener{
            val intent = Intent(this@MainActivity,HelperActivity::class.java)
            startActivity(intent)
        }

        findViewById<Button>(R.id.buttonSubmit).setOnClickListener{
            val calc = Calculator()
            val shunt = SYA()
            val x = shunt.sya(output)
            val y = calc.calculate(x)
            val input: Float = calc.calculate(shunt.sya(output))
            println("SYA: ${shunt.sya(output)}")
            println("Calculation: ${calc.calculate(shunt.sya(output))}")
            val intent = Intent(this@MainActivity,NonAlgebraOutActivity::class.java)
            intent.putExtra("input",output)
            intent.putExtra("output",input.toString())
            startActivity(intent)
            output = ""
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

    private fun undo(undoStack: Stack<Int>, text: String): Triple<Stack<Int>,String,String>{
        val n = undoStack.pop()
        val lastNChars = text.substring(startIndex = text.length - n)
        val newText = text.substring(0, text.length-n)

        return Triple(undoStack, lastNChars, newText)
    }

    private fun redo(redoStack: Stack<String>, text: String): Triple<Stack<String>,String,Int>{
        val change = redoStack.pop()
        val newText = text + change

        return Triple(redoStack,newText,change.length)
    }

    private fun redoOrUndoChanged(undoStack: Stack<Int>, redoStack: Stack<String>){
        val undo = findViewById<ImageButton>(R.id.buttonUndo)
        val redo = findViewById<ImageButton>(R.id.buttonRedo)

        if(undoStack.isEmpty()){
            undo.alpha = 0.25F
            undo.isEnabled = false
        }else{
            undo.alpha = 1F
            undo.isEnabled = true
        }

        if(redoStack.isEmpty()){
            redo.alpha = 0.25F
            redo.isEnabled = false
        }else{
            redo.alpha = 1F
            redo.isEnabled = true
        }
    }

}