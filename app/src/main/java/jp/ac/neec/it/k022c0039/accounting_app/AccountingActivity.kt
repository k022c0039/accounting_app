package jp.ac.neec.it.k022c0039.accounting_app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity


class AccountingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accounting)
    }

    fun nextButtonClick(view: View) {
        val etName = findViewById<EditText>(R.id.etName)
        val name = etName.text.toString()
        val etNum = findViewById<EditText>(R.id.etNum)
        val num = etNum.text.toString().toInt()

        if (name.isBlank() || etNum.text.isBlank()) {
            if (name.isBlank()) {
                etName.error = "名前を入力してください"
            }
            if (etNum.text.isBlank()) {
                etNum.error = "人数を入力してください"
            }
            return
        }

        if (num <= 0) {
            etNum.error = "人数は1以上で入力してください"
            return
        }

        val intent = Intent(this@AccountingActivity, AccountingActivity2::class.java)
        intent.putExtra("name", name) // 名前を渡す
        startActivity(intent)
    }

    fun backButtonClick(view: View) {
        finish()
    }
}