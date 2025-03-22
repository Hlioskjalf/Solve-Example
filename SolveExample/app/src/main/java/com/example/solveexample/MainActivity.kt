package com.example.solveexample

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var exampleTextView: TextView
    private lateinit var answerEditText: EditText
    private lateinit var startButton: Button
    private lateinit var checkButton: Button
    private lateinit var statsTextView: TextView

    private var correctAnswers: Int = 0
    private var totalAnswers: Int = 0

    private var currentAnswer: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        exampleTextView = findViewById(R.id.exampleTextView)
        answerEditText = findViewById(R.id.answerEditText)
        startButton = findViewById(R.id.startButton)
        checkButton = findViewById(R.id.checkButton)
        statsTextView = findViewById(R.id.statsTextView)

        startButton.setOnClickListener {
            generateExample()
            answerEditText.isEnabled = true
            startButton.isEnabled = false
            checkButton.isEnabled = true
            exampleTextView.setBackgroundColor(Color.WHITE)
        }

        checkButton.setOnClickListener {
            val userAnswer = answerEditText.text.toString().toIntOrNull()
            if (userAnswer == currentAnswer) {
                exampleTextView.setBackgroundColor(Color.GREEN)
                correctAnswers++
            } else {
                exampleTextView.setBackgroundColor(Color.RED)
            }
            totalAnswers++
            updateStats()
            answerEditText.isEnabled = false
            checkButton.isEnabled = false
            startButton.isEnabled = true
        }
    }

    @SuppressLint("SetTextI18n")
    private fun generateExample() {
        val num1 = Random.nextInt(10, 100)
        val num2 = Random.nextInt(10, 100)
        val operator = listOf('+', '-', '*', '/').random()

        val example = "$num1 $operator $num2"
        currentAnswer = when (operator) {
            '+' -> num1 + num2
            '-' -> num1 - num2
            '*' -> num1 * num2
            '/' -> {
                var divisor = num2
                while (num1 % divisor != 0) {
                    divisor = Random.nextInt(10, 100)
                }
                num1 / divisor
            }
            else -> throw IllegalArgumentException("Unknown operator")
        }

        exampleTextView.text = "$example = ?"
    }

    @SuppressLint("SetTextI18n")
    private fun updateStats() {
        val percentage = if (totalAnswers > 0) (correctAnswers.toDouble() / totalAnswers * 100).toBigDecimal().setScale(2, java.math.RoundingMode.HALF_UP).toDouble() else 0.0
        statsTextView.text = "Correct: $correctAnswers, Incorrect: ${totalAnswers - correctAnswers}, Percentage: $percentage%"
    }
}
