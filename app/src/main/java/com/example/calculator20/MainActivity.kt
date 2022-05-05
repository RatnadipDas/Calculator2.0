package com.example.calculator20

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.lang.ArithmeticException
import java.lang.NumberFormatException

class MainActivity : AppCompatActivity() {

    private var tvIO: TextView? = null
    private var lastDigit = false
    private var haveADot = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvIO = findViewById(R.id.tvIO)
    }

    /*
        If a Digit Button is clicked append it to the IO Text.
    */
    fun onDigit(view: View) {
        tvIO?.append((view as Button).text)
        lastDigit = true
    }

    /*
        If a Operator Button is clicked then append it to the IO Text
        if there are no other Operator in the expression, other than
        '-' sign in the beginning.
     */
    fun onOperator(view: View) {
        tvIO?.text?.let{
            if(lastDigit && !operatorAdded(it.toString())) {
                tvIO?.append((view as Button).text.toString())
                lastDigit = false
                haveADot = false
            } else if(it.isEmpty() && ((view as Button).text.toString() == "-")) {
                tvIO?.append(view.text.toString())
                lastDigit = false
                haveADot = false
            }
        }
    }

    /*
        If '.' Button is clicked then fist check weather a Digit is entered before it,
        if a Digit is there then allow user to enter '.'. Also if already a '.' is present after a
        digit then don't allow user to enter another '.'.
     */
    fun onDot(view: View) {
        if(lastDigit && !haveADot) {
            tvIO?.append((view as Button).text.toString())
            haveADot = true
        }
    }

    /*
        If 'AC' Button is clicked then clear the tvIO.text
     */
    fun onAC(view: View) {
        tvIO?.text = ""
        lastDigit  = false
        haveADot = false
    }

    /*
        If 'DEL' Button is clicked then Delete the trailing character from tvIO.text.
     */
    fun onDEL(view: View) {
        var value = tvIO?.text.toString()
        if(value.isNotEmpty()) {
            if (value.last() == '.') {
                haveADot = false
            } else if (value.last().isDigit() && (value.length == 1)) {
                lastDigit = false
            }

            value = value.dropLast(1) // Drop the last character.
            tvIO?.text = value
        } else {
            Toast.makeText(this, "Nothing to Delete!", Toast.LENGTH_SHORT).show()
        }
    }

    /*
        If '=' Button is pressed then evaluate the mathematical expression present in
        tvIO.text.
     */
    fun onEqual(view: View) {
        var value = tvIO?.text.toString()
        var prefix = "" // Will store the '-' prefix if it is there.
        try {
            /*
                If the tvIO.text has a '-' prefix, then remove and store if
                for further use.
             */
            if (value.startsWith('-')) {
                value = value.substring(1)
                prefix = "-"
            }

            if (value.contains("-")) {
                /*
                    If Arithmetic Operation which is to be performed is Subtraction then,
                    split the expression right at the '-' and get the first number and the second number.
                    Then just Subtract those two numbers.
                 */
                val splitValue = value.split('-')
                var first = splitValue[0] // First number
                val second = splitValue[1] // Second number

                if (prefix.isNotEmpty()) {
                    /*
                        In case there was a '-' in the prefix, then concatenate back to the first number.
                     */
                    first = prefix + first
                }

                val result = (first.toDouble() - second.toDouble()).toString()
                tvIO?.text = result
            } else if (value.contains("+")) {
                /*
                    If Arithmetic Operation which is to be performed is Addition then,
                    split the expression right at the '+' and get the first number and the second number.
                    Then just Add those two numbers.
                 */
                val splitValue = value.split('+')
                var first = splitValue[0]
                val second = splitValue[1]

                if (prefix.isNotEmpty()) {
                    /*
                        In case there was a '-' in the prefix, then concatenate back to the first number.
                     */
                    first = prefix + first
                }

                val result = (first.toDouble() + second.toDouble()).toString()
                tvIO?.text = result
            } else if (value.contains("/")) {
                /*
                    If Arithmetic Operation which is to be performed is Division then,
                    split the expression right at the '/' and get the first number and the second number.
                    Then just Divide those two numbers.
                 */
                val splitValue = value.split('/')
                var first = splitValue[0]
                val second = splitValue[1]

                if (prefix.isNotEmpty()) {
                    /*
                        In case there was a '-' in the prefix, then concatenate back to the first number.
                     */
                    first = prefix + first
                }

                val result = (first.toDouble() / second.toDouble()).toString()
                tvIO?.text = result
            } else if (value.contains("*")) {
                /*
                    If Arithmetic Operation which is to be performed is Multiplication then,
                    split the expression right at the '/' and get the first number and the second number.
                    Then just Multiply those two numbers.
                 */
                val splitValue = value.split('*')
                var first = splitValue[0]
                val second = splitValue[1]

                if (prefix.isNotEmpty()) {
                    /*
                        In case there was a '-' in the prefix, then concatenate back to the first number.
                     */
                    first = prefix + first
                }

                val result = (first.toDouble() * second.toDouble()).toString()
                tvIO?.text = result
            }
        } catch(e: ArithmeticException) {
            e.printStackTrace()
            Toast.makeText(this, "Error Occurred!", Toast.LENGTH_SHORT).show()
        } catch (e: NumberFormatException) {
            e.printStackTrace()
            Toast.makeText(this, "Error Occurred!", Toast.LENGTH_SHORT).show()
        }
    }

    /*
        This function is used by onOperator function.
        If any arithmetic operator is added other then '-' at the start, then return 'true',
        else return 'false'.
     */
    private fun operatorAdded(value: String): Boolean {
        if(value.startsWith('-')) {
            /*
                If value: String starts with '-' then first, remove the starting
                '-' and then check for arithmetic operators.
             */
            val value = value.substring(1) // remove starting '-'
            return value.contains('-')
                    || value.contains('+')
                    || value.contains('/')
                    || value.contains('*')
        } else {
            /*
                If value: String doesn't start with '-', then directly check for arithmetic operators.
             */
            return value.contains('-')
                    || value.contains('+')
                    || value.contains('/')
                    || value.contains('*')
        }
    }
}