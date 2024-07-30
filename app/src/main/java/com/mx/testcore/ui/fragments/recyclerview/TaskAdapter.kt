package com.mx.testcore.ui.fragments.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mx.testcore.data.models.Product
import com.mx.testcore.databinding.ItemTaskBinding

class TaskAdapter(
    private val onTaskSelected: (Product) -> Unit
):
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

        private var tasks: List<Product> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder = TaskViewHolder(
        ItemTaskBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(tasks[position])
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    inner class TaskViewHolder(private var binding: ItemTaskBinding) :
        RecyclerView.ViewHolder( binding.root ) {
            fun bind( item: Product ) = with( binding ) {
                taskTitle.text = item.title
                taskDescription.text = item.description
                taskPrices.text = item.price.toString()
                root.setOnClickListener { onTaskSelected(item) }
            }
        }

    fun setProducts(newProducts: List<Product>) {
        tasks = newProducts
        notifyDataSetChanged()
    }
}