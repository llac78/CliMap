package project.llac.climapapplication

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import project.llac.climapapplication.adapter.ClientAdapter
import project.llac.climapapplication.model.Client

class MainActivity : BaseActivity() {

    private var adapter: ClientAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupToolbar(toolbar, "Clientes", false)
        setupRecyclerView()
        setupOnClicks()
    }

    private fun setupOnClicks(){
        fab.setOnClickListener { addClient() }
        btnSearch.setOnClickListener { search() }
    }

    override fun onResume() {
        super.onResume()
        search()
    }

    private fun setupRecyclerView(){
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun addClient(){
        val intent = Intent(this, ClientActivity::class.java)
        startActivity(intent)
    }

    private fun onClickItemRecyclerView(index: Int) {
        val intent = Intent(this, ClientActivity::class.java)
        intent.putExtra("index", index)
        startActivity(intent)
    }

    private fun search(){
        val search = edtSearch.text.toString()
        progress.visibility = View.VISIBLE

        Thread(Runnable {
            Thread.sleep(1500)
            var filterList: List<Client> = mutableListOf()
            try {
                filterList = CliMapApplication.instance.helperDB?.search(search) ?: mutableListOf()
            }catch (ex: Exception){
                ex.printStackTrace()
            }
            runOnUiThread {
                adapter = ClientAdapter(this, filterList) {onClickItemRecyclerView(it)}
                recyclerView.adapter = adapter
                progress.visibility = View.GONE
            }
        }).start()

        /*var filterList: List<Client> = mutableListOf()
        try {
            filterList = CliMapApplication.instance.helperDB?.search(search) ?: mutableListOf()
        } catch (e: Exception){
            e.printStackTrace()
        }

        adapter = ClientAdapter(this, filterList) {
            onClickItemRecyclerView(it)
        }
        recyclerView.adapter = adapter*/
    }
}