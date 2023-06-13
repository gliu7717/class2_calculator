package com.example.mycalculator

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mycalculator.databinding.ActivityMainBinding
import kotlin.text.StringBuilder

class MainActivity : AppCompatActivity(), View.OnClickListener{
    private lateinit var binding: ActivityMainBinding
    private lateinit var sf:SharedPreferences
    private  lateinit var editor:SharedPreferences.Editor
    private lateinit var viewModel: MainActivityViewModel


    var sbCurrentInputOperand = StringBuilder()
    private var lastOperand  = 0

    private var lastOperator = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        //setContentView(R.layout.activity_main)
        setContentView(view)
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        viewModel.result.observe(this, Observer {
            binding.tvResult.text = it.toString()
        })
        binding.tvProcess.text = viewModel.progressValue

        sf = getSharedPreferences("my_sf", MODE_PRIVATE)
        editor = sf.edit()

        binding.tvOne.setOnClickListener(this)
        binding.tvTwo.setOnClickListener(this)
        binding.tvPlus.setOnClickListener {
            clickOperator(it)
        }
        binding.tvEqual.setOnClickListener {
            clickOperator(it)
        }

    }

    override fun onPause() {
        super.onPause()
        val result = binding.tvResult.text.toString()
        editor.apply(){
       //     putString("sf_result", result)
        //    commit()
        }
    }

    override fun onResume() {
        super.onResume()
        //val result = sf.getString("sf_result",null)
        //if(result!=null)
        //    binding.tvResult.text = result
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
        viewModel.updateProgressValue(str.toString())
        binding.tvProcess.text = str.toString()

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

            //binding.tvResult.text = result.toString()
            viewModel.updateResult(result)

            lastOperand = result
        }
        lastOperator = tv.text.toString()
        sbCurrentInputOperand.clear()
        displayExpression()
    }

 }