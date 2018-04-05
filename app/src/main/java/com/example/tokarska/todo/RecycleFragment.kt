package com.example.tokarska.todo

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.preference.PreferenceManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import kotlinx.android.synthetic.main.fragment_recycle.*


class RecycleFragment : Fragment(), SharedPreferences.OnSharedPreferenceChangeListener {

    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
        sortTasks()
    }

    private var taskList = ArrayList<Task>()
    private var alertDialog: AlertDialog.Builder? = null
    private var v: View? = null
    private var view2: View? = null
    private var contxt: Context? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view:View = inflater!!.inflate(R.layout.fragment_recycle, container, false)
        view2 = view
        var DB = DatabaseHandler(contxt!!)
        taskList = DB.fetchTasks("%")
        var task_list = view.findViewById<RecyclerView>(R.id.task_list)
        task_list.layoutManager = LinearLayoutManager(contxt!!)
        task_list.hasFixedSize()
        task_list.adapter = TaskAdapter(taskList, DB)
        initDialog()
        initFAButton()
        initSwipe()
        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        contxt = context
    }

    private fun initFAButton() {
        var fab = view2!!.findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener{
            removeView(v!!)
            alertDialog!!.setTitle("Add task")
            alertDialog!!.show()
        }
    }

    private fun initDialog() {
        alertDialog = AlertDialog.Builder(contxt!!)
        v = layoutInflater.inflate(R.layout.add_dialog, null)
        alertDialog!!.setView(v)
        var edit = v!!.findViewById<EditText>(R.id.new_task) as EditText
        alertDialog!!.setPositiveButton("Save") { dialog, _ ->
            val adapter = task_list!!.adapter as TaskAdapter
            adapter.addTask(edit.text.toString())
            sortTasks()
            edit.setText("")
            dialog.dismiss()
        }
    }

    private fun removeView (view: View) {
        if (view.parent != null) {
            (view.parent as ViewGroup).removeView(view)
        }
    }

    private fun initSwipe() {
        var task_list = view2!!.findViewById<RecyclerView>(R.id.task_list)
        val swipeHandler = object : SwipeToDeleteCallback(contxt!!) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = task_list!!.adapter as TaskAdapter
                var item = adapter.removeAt(viewHolder.adapterPosition)
                Snackbar.make(view2!!, "Task deleted", Snackbar.LENGTH_LONG)
                        .setAction("UNDO", {_ -> adapter.addTask(item.content!!)
                        sortTasks()})
                        .show()
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(task_list)
    }

    private fun sortTasks() {
        var sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity)
        var sorted: Boolean = false
        if (sharedPreferences.getBoolean("pref-sort", true))
            sorted = true
        if (sorted) {
            val adapter = task_list!!.adapter as TaskAdapter
            adapter.sortList()
        }
    }

}// Required empty public constructor
