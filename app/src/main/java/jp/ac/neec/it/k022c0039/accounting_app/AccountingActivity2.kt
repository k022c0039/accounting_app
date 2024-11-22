package jp.ac.neec.it.k022c0039.accounting_app

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AccountingActivity2 : AppCompatActivity() {

    private var name: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accounting2)

        // 前のアクティビティから名前を取得
        name = intent?.getStringExtra("name") ?: "未入力"

        // Spinnerのデータを初期化
        initializeSpinners()
    }

    private fun initializeSpinners() {
        // Spinnerに設定するデータ
        val array = (0..30).map { it.toString() } // 範囲指定でデータを作成
        val spinnerIds = listOf(
            R.id.spSet, R.id.spCharge, R.id.spShochu, R.id.spHig, R.id.spKicchomu,
            R.id.spMac, R.id.spHibiki, R.id.spCan, R.id.spShot, R.id.spSoft,
            R.id.spYellow, R.id.spPink, R.id.spFlower
        )

        // 非同期でSpinnerのアダプタを設定
        lifecycleScope.launch {
            // アダプタ作成を非同期で行う
            val adapter = withContext(Dispatchers.Default) {
                ArrayAdapter(this@AccountingActivity2, android.R.layout.simple_spinner_dropdown_item, array)
            }

            // UIスレッドでSpinnerにアダプタを設定
            withContext(Dispatchers.Main) {
                spinnerIds.forEach { id ->
                    val spinner = findViewById<Spinner>(id)
                    spinner.adapter = adapter
                }
            }
        }
    }

    fun confButtonClick(view: View) {
        // 次のアクティビティへIntentを準備
        val intent = Intent(this@AccountingActivity2, SettlementActivity::class.java)

        // Spinner IDと項目名のマッピング
        val spinnerInfo = mapOf(
            R.id.spSet to getString(R.string.tv_set),
            R.id.spCharge to getString(R.string.tv_charge),
            R.id.spShochu to getString(R.string.tv_shochu),
            R.id.spHig to getString(R.string.tv_hig),
            R.id.spKicchomu to getString(R.string.tv_kicchomu),
            R.id.spMac to getString(R.string.tv_mac),
            R.id.spHibiki to getString(R.string.tv_hibiki),
            R.id.spCan to getString(R.string.tv_can),
            R.id.spShot to getString(R.string.tv_shot),
            R.id.spSoft to getString(R.string.tv_soft),
            R.id.spYellow to getString(R.string.tv_yellow),
            R.id.spPink to getString(R.string.tv_pink),
            R.id.spFlower to getString(R.string.tv_flower)
        )

        // 単価のマッピング
        val priceMap = mapOf(
            R.id.spSet to 3000,
            R.id.spCharge to 1000,
            R.id.spShochu to 5000,
            R.id.spHig to 6000,
            R.id.spKicchomu to 8000,
            R.id.spMac to 18000,
            R.id.spHibiki to 35000,
            R.id.spCan to 1000,
            R.id.spShot to 1000,
            R.id.spSoft to 500,
            R.id.spYellow to 20000,
            R.id.spPink to 35000,
            R.id.spFlower to 60000
        )

        val selectedItems = mutableListOf<String>()
        var totalAmount = 0
        var taxAmount = 0.0 // 消費税額の計算

        // Spinnerの値を取得して計算
        spinnerInfo.forEach { (id, itemName) ->
            val spinner = findViewById<Spinner>(id)
            val selectedValue = spinner.selectedItem.toString().toIntOrNull() ?: 0

            if (selectedValue > 0) {
                val price = priceMap[id] ?: 0
                val itemTotal = selectedValue * price
                val itemTax = itemTotal * 0.1

                totalAmount += itemTotal
                taxAmount += itemTax
                selectedItems.add("$itemName : ×$selectedValue -------- $itemTotal")
            }
        }

        // 合計金額を次のアクティビティに渡す
        intent.putStringArrayListExtra("selectedItems", ArrayList(selectedItems))
        intent.putExtra("totalAmount", totalAmount)
        intent.putExtra("taxAmount", taxAmount)
        intent.putExtra("grandTotal", totalAmount + taxAmount)

        // name を渡す
        intent.putExtra("name", name)

        startActivity(intent)
    }

    fun backButtonClick(view: View) {
        finish()
    }
}