package com.extremex.tablemanager.adapter


import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.imageview.ShapeableImageView
import androidx.recyclerview.widget.RecyclerView
import com.extremex.tablemanager.R
import com.extremex.tablemanager.lib.CustomDialog
import com.extremex.tablemanager.lib.CustomDialogConfirmListener
import com.extremex.tablemanager.lib.ManageTeachersModel
import com.google.android.material.textview.MaterialTextView


class ManageAllTeachersAdapter(private val context: Context, private val teacherList: MutableList<ManageTeachersModel>) :
    RecyclerView.Adapter<ManageAllTeachersAdapter.ViewHolder>() {
        interface TeacherListener{
            fun onTeacherRemoved()
        }

    private var listener: TeacherListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_manage_teacher_cell, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val teacher = teacherList[position]
        holder.nameTextView.text = teacher.name
        holder.idNumberTextView.text = teacher.idNumber
        holder.subjectTextView.text = teacher.assignedSubject
        holder.removeTeacherButton.setOnClickListener { removeTeacher(position, teacherList) }
    }

    override fun getItemCount(): Int {
        return teacherList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: MaterialTextView = itemView.findViewById(R.id.TeacherName)
        val idNumberTextView: MaterialTextView = itemView.findViewById(R.id.TeacherID)
        val subjectTextView: MaterialTextView = itemView.findViewById(R.id.TeacherSubject)
        val removeTeacherButton: ShapeableImageView = itemView.findViewById(R.id.TeacherRemove)

    }

    private fun removeTeacher(position: Int, teacherList: MutableList<ManageTeachersModel>): Int {
        val makeDialog = CustomDialog( context,null, object : CustomDialogConfirmListener {
            override fun onConfirm() {
                Log.v("Deleting: ", teacherList[position].name)
                remove(position)
            }
        })

        makeDialog.createBasicTwoStateCustomDialog(
            "Cancel",
            "Confirm",
            "Are you sure, you want to remove ${teacherList[position].name} from this institute.",
            true
        )

        return position
    }
    private fun remove(position: Int){
        if (position >= 0) {
            Log.v("Deleted: ", "$position" + teacherList[position].name)
            teacherList.removeAt(position)
            listener?.onTeacherRemoved()
            notifyItemRemoved(position)
        }
    }

    fun setListener(listener: TeacherListener){
        this.listener = listener
    }
}
