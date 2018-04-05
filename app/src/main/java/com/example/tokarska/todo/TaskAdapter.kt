package com.example.tokarska.todo

import android.content.ContentValues
import android.support.v7.preference.PreferenceManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.list_item.view.*

class TaskAdapter (var taskList: MutableList<Task>, val handler: DatabaseHandler) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        (holder as TaskViewHolder).bind(taskList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item, parent, false)
        return TaskViewHolder(view)
    }

    override fun getItemCount() = taskList.size

    public fun removeAt(position: Int) : Task {
        var item = taskList.removeAt(position)
        handler.removeTask(item.id)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, taskList.size)
        return item
    }

    fun addTask(content: String) {
        var max: Int = 0
        for (task in taskList) {
            if (task.id > max)
                max = task.id
        }
        var values = ContentValues()
        values.put("content", content)
        handler.addTask(values)
        taskList.add(Task(content, max))
        notifyItemInserted(taskList.size)
    }

    fun sortList() {
        taskList = taskList.sortedWith(compareBy({it.content})).toMutableList()
        notifyDataSetChanged()
    }

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(task: Task) {
            itemView.item_content.text = task.content
        }
    }
}