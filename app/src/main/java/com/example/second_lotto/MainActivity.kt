package com.example.second_lotto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {

    private val addButton:Button by lazy { findViewById(R.id.addButton)}
    private val clearButton:Button by lazy { findViewById(R.id.ClearButton)}
    private val autoButton:Button by lazy { findViewById(R.id.autoButton)}
    private val numberPicker : NumberPicker by lazy {findViewById(R.id.NumberPicker)}

    private var didRun = false
    private var pickNumberSet = mutableSetOf<Int>()
    private val numberTextView : List<TextView> by lazy {
        listOf<TextView>(
            findViewById(R.id.lotto_number1),
            findViewById(R.id.lotto_number2),
            findViewById(R.id.lotto_number3),
            findViewById(R.id.lotto_number4),
            findViewById(R.id.lotto_number5),
            findViewById(R.id.lotto_number6),
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numberPicker.minValue = 1
        numberPicker.maxValue = 45

        initRunButton()
        initAddButton()
        initClearButton()
    }

    private fun initRunButton(){
        autoButton.setOnClickListener(){
            val list= getRandomNumber()
            list.forEachIndexed{index,number ->
                val textView = numberTextView[index]
                textView.isVisible = true
                textView.text = number.toString()

                numberBackground(number,textView)
            }
        }
    }

    private fun initAddButton(){
        addButton.setOnClickListener(){
            if(didRun){
                Toast.makeText(this,"초기화 후 다시 시도해주세요",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(pickNumberSet.size >=5){
                Toast.makeText(this,"번호는 5개 까지 선택가능합니다",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(pickNumberSet.contains(numberPicker.value)){
                Toast.makeText(this,"이미 선택된 번호입니다",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val textView = numberTextView[pickNumberSet.size]
            textView.isVisible = true
            textView.text = numberPicker.value.toString()

            numberBackground(numberPicker.value,textView)

            pickNumberSet.add(numberPicker.value)

        }
    }

    private fun initClearButton(){
        clearButton.setOnClickListener(){
            didRun = false
            pickNumberSet.clear()
            numberTextView.forEach(){
                it.isVisible = false
            }}
    }

    private fun getRandomNumber() : List<Int> {
        val numberList = mutableListOf<Int>().apply{
            for(i in 1..45){
                if(pickNumberSet.contains(i)){
                    continue
                }
                this.add(i)
            }
        }
        numberList.shuffle()

        val newList =pickNumberSet.toList() + numberList.subList(0,6-pickNumberSet.size)
        didRun = true
        return newList.sorted()
    }

    private fun numberBackground(number : Int, textView : TextView){
        when(number){
            in 1..10 -> textView.background = ContextCompat.getDrawable(this,R.drawable.color_yellow)
            in 11..20 -> textView.background = ContextCompat.getDrawable(this,R.drawable.color_blue)
            in 21..30 -> textView.background = ContextCompat.getDrawable(this,R.drawable.color_red)
            in 31..40 -> textView.background = ContextCompat.getDrawable(this,R.drawable.color_gray)
            else -> textView.background = ContextCompat.getDrawable(this,R.drawable.color_green)
        }
    }

}