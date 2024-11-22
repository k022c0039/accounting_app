package jp.ac.neec.it.k022c0039.accounting_app

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DailytotalActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dailytotal2)

        // SharedPreferences を取得
        val sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE)
        val gson = Gson()

        // 保存されたデータリストを取得
        val savedDataJson = sharedPreferences.getString("dataList", "[]") ?: "[]"
        val type = object : TypeToken<MutableList<SavedData>>() {}.type
        val savedDataList: MutableList<SavedData> = gson.fromJson(savedDataJson, type)

        val textView1 = findViewById<TextView>(R.id.tvTotal)
        val textView2 = findViewById<TextView>(R.id.tvStaff)
        val textView3 = findViewById<TextView>(R.id.tvExpense)
        val textView4 = findViewById<TextView>(R.id.tvCalculation)

        val totalSum = savedDataList.sumOf { it.price }

        // 会計データの表示
        if (savedDataList.isEmpty()) {
            textView1.text = "会計データなし"
        } else {
            val totalAmount = (totalSum * 0.9).toInt()
            val taxAmount = totalSum - totalAmount
            val formattedData = savedDataList.joinToString("\n") {
                "${it.name} : ¥${"%,.0f".format(it.price.toDouble())}"
            }
            textView1.text = "$formattedData\n\n小計： ¥${"%,.0f".format(totalAmount.toDouble())}\n税金： ¥${"%,.0f".format(taxAmount.toDouble())}\n合計金額： ¥${"%,.0f".format(totalSum.toDouble())}".trimIndent()
        }

        // 人件費データの表示
        val employeeDataList = intent.getParcelableArrayListExtra<EmployeeData>("employeeData") ?: arrayListOf()
        val totalCost = intent.getDoubleExtra("totalCost", 0.0)

        if (employeeDataList.isEmpty()) {
            textView2.text = "人件費なし"
        } else {
            val individualCosts = employeeDataList.joinToString("\n") {
                "${it.name} : ¥${"%,.0f".format(it.cost.toDouble())}"
            }
            textView2.text = "$individualCosts\n合計人件費： ¥${"%,.0f".format(totalCost.toDouble())}"
        }

        // 経費データの表示
        val expenseList = intent.getParcelableArrayListExtra<ExpenseData>("expenseList") ?: arrayListOf()
        val totalExpense = expenseList.sumOf { it.price }

        if (expenseList.isEmpty()) {
            textView3.text = "経費なし"
        } else {
            val formattedExpenses = expenseList.joinToString("\n") {
                "${it.storeName}: ¥${"%,.0f".format(it.price.toDouble())}"
            }
            textView3.text = "$formattedExpenses\n合計経費： ¥${"%,.0f".format(totalExpense.toDouble())}"
        }

        // 現金残高の計算
        val calculation = totalSum - (totalCost + totalExpense)
        textView4.text = "現金残高： ¥${"%,.0f".format(calculation.toDouble())}"
    }

    fun FinButtonClick(view: View) {
        val intent = Intent(this@DailytotalActivity2, MainActivity::class.java)
        val sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.remove("dataList")
        editor.apply()
        startActivity(intent)
    }

    fun BackButtonClick(view: View) {
        finish()
    }
}