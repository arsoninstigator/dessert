package com.example.caloric

import android.os.Bundle
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var butter = 100
    private var sugar = 100
    private var flour = 200

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val butterText = findViewById<TextView>(R.id.butterText)
        val sugarText = findViewById<TextView>(R.id.sugarText)
        val flourText = findViewById<TextView>(R.id.flourText)
        val caloriesText = findViewById<TextView>(R.id.caloriesText)

        val butterSeekBar = findViewById<SeekBar>(R.id.butterSeekBar)
        val sugarSeekBar = findViewById<SeekBar>(R.id.sugarSeekBar)
        val flourSeekBar = findViewById<SeekBar>(R.id.flourSeekBar)

        butterSeekBar.progress = butter
        sugarSeekBar.progress = sugar
        flourSeekBar.progress = flour

        updateCalories(caloriesText)

        butterSeekBar.setOnSeekBarChangeListener(createSeekBarListener { value ->
            butter = value
            butterText.text = "Butter: $value g"
            updateCalories(caloriesText)
        })

        sugarSeekBar.setOnSeekBarChangeListener(createSeekBarListener { value ->
            sugar = value
            sugarText.text = "Sugar: $value g"
            updateCalories(caloriesText)
        })

        flourSeekBar.setOnSeekBarChangeListener(createSeekBarListener { value ->
            flour = value
            flourText.text = "Flour: $value g"
            updateCalories(caloriesText)
        })
    }

    private fun updateCalories(caloriesText: TextView) {
        val totalCalories = (butter * 7.17) + (sugar * 3.87) + (flour * 3.64)
        caloriesText.text = "Total Calories: ${totalCalories.toInt()} kcal"
    }

    private fun createSeekBarListener(onProgressChanged: (Int) -> Unit): SeekBar.OnSeekBarChangeListener {
        return object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                onProgressChanged(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        }
    }
}
