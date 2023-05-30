package com.example.zadanko1

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class MineGridRecyclerAdapter(private var cells: ArrayList<Cell>, listener: OnCellClickListener) :
    RecyclerView.Adapter<MineGridRecyclerAdapter.MineTileViewHolder>() {
    private val listener: OnCellClickListener

    init {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MineTileViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_cell, parent, false)
        return MineTileViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MineTileViewHolder, position: Int) {
        holder.bind(cells[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int {
        return cells.size
    }

    fun setCells(cells: ArrayList<Cell>) {
        this.cells = cells
        println("notified")
        notifyDataSetChanged()
    }

    inner class MineTileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var valueTextView: TextView

        init {
            valueTextView = itemView.findViewById(R.id.item_cell_value)
        }

        fun bind(cell: Cell) {
            itemView.setBackgroundColor(Color.GRAY)
            itemView.setOnClickListener(object : View.OnClickListener {
                override fun onClick(view: View?) {
                    println("ItemView klik")
                    listener.onCellClick(cell)
                }
            })
            if (cell.isRevealed()) {
                println("was revealed")
                if (cell.getValue() == (-1)) {//BOMB
                    valueTextView.setText(R.string.bomb)
                } else if (cell.getValue() == (0)) {//BLANK
                    valueTextView.text = ""
                    itemView.setBackgroundColor(Color.WHITE)
                } else {
                    valueTextView.text = cell.getValue().toString()
                    if (cell.getValue() == 1) {
                        valueTextView.setTextColor(Color.BLUE)
                    } else if (cell.getValue() == 2) {
                        valueTextView.setTextColor(Color.GREEN)
                    } else if (cell.getValue() == 3) {
                        valueTextView.setTextColor(Color.RED)
                    }
                }
            } else if (cell.isFlagged()) {
                valueTextView.setText(R.string.flag)
            }
        }

    }
}