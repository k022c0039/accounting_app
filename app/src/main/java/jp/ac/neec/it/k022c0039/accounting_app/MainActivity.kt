package jp.ac.neec.it.k022c0039.accounting_app

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        }
    fun accountingButtonClick(view: View){
        val intent = Intent(this@MainActivity,AccountingActivity::class.java)
        startActivity(intent)
    }
    fun DailytotalButtonclick(view: View){
        val intent = Intent(this@MainActivity,DailytotalActivity::class.java)
        startActivity(intent)
    }
}