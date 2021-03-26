package project.llac.climapapplication

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_client.*
import project.llac.climapapplication.model.*

class ClientActivity : BaseActivity() {

    private var id: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client)

        setupToolbar(toolBar, "Adicionar novo cliente", true)
        setupClient()
        btnSave.setOnClickListener { onClickSalvarContato() }
    }

    private fun setupClient(){
        id = intent.getIntExtra("index",-1)
        if (id == -1){
            btnRemove.visibility = View.GONE
            return
        }
        progress.visibility = View.VISIBLE
        Thread(Runnable {
            Thread.sleep(1500)
            var list = CliMapApplication.instance.helperDB?.search("$id",true) ?: return@Runnable
            var client = list.getOrNull(0) ?: return@Runnable
            runOnUiThread {
                edtName.setText(client.name)
                edtPhone.setText(client.phone)
                progress.visibility = View.GONE
            }
        }).start()
    }

    private fun onClickSalvarContato(){
        val name = edtName.text.toString()
        val phone = edtPhone.text.toString()
        val client = Client(id, name, phone)
        progress.visibility = View.VISIBLE
        Thread(Runnable {
            Thread.sleep(1500)
            if(id == -1) {
                CliMapApplication.instance.helperDB?.save(client)
            }else{
                CliMapApplication.instance.helperDB?.update(client)
            }
            runOnUiThread {
                progress.visibility = View.GONE
                finish()
            }
        }).start()
    }

    fun onClickExcluirContato(view: View) {
        if(id > -1){
            progress.visibility = View.VISIBLE
            Thread(Runnable {
                Thread.sleep(1500)
                CliMapApplication.instance.helperDB?.remove(id)
                runOnUiThread {
                    progress.visibility = View.GONE
                    finish()
                }
            }).start()
        }
    }


}