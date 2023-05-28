package com.example.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import kotlin.text.StringBuilder
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener{
    var sbCurrentInputOperand = StringBuilder()
    private var lastOperand  = 0

    private var lastOperator = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvOne.setOnClickListener(this)
        tvTwo.setOnClickListener(this)
        tvPlus.setOnClickListener {
            clickOperator(it)
        }
        tvEqual.setOnClickListener {
            clickOperator(it)
        }
    }

    override fun onClick(v: View?) {
        if (v != null) {
            clickNumber(v)
        }
    }

    fun clickNumber(view:View){
        //cast view to TextView
        val tv = view as TextView
        sbCurrentInputOperand.append(tv.text)
        if(lastOperator == "") {
            lastOperand = sbCurrentInputOperand.toString().toInt()
        }

        displayExpression()
        //计算结果
        //calculate()
    }

    // displaying the expression string
    private fun displayExpression(){
        val str = StringBuilder()
        str.append(lastOperand)
        if(lastOperator == "+") {
            str.append(" ${lastOperator} ")
            if(!sbCurrentInputOperand.isEmpty())
                str.append(sbCurrentInputOperand)
        }
        tvProcess.text = str.toString()
    }

    fun clickOperator(view: View){
        val tv = view as TextView

        if(sbCurrentInputOperand.isEmpty()) {
            lastOperator = tv.text.toString()
            return
        }

        var operand2 = sbCurrentInputOperand.toString().toInt()
        var result = 0
        if(lastOperator == "+")
        {
            result = lastOperand + operand2
            tvResult.text = result.toString()
            lastOperand = result
        }
        lastOperator = tv.text.toString()
        sbCurrentInputOperand.clear()
        displayExpression()
    }

 }