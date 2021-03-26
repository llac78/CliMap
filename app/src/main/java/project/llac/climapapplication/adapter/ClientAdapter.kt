package project.llac.climapapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_client.view.*
import project.llac.climapapplication.R
import project.llac.climapapplication.model.Client

class ClientAdapter(
    private val context: Context,
    private val list: List<Client>,
    private val onClick: ((Int) -> Unit)
) : RecyclerView.Adapter<ClientViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_client, parent, false)
        return ClientViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ClientViewHolder, position: Int) {
        val client = list[position]
        with(holder.itemView){
            txtName.text = client.name
            txtPhone.text = client.phone
            itemView.setOnClickListener { onClick(client.id) }
        }
    }
}

class ClientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)