package com.trelp.aag2020.ui.details

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.trelp.aag2020.data.Actor
import com.trelp.aag2020.databinding.ItemActorBinding
import com.trelp.aag2020.ui.common.utils.inflater

class ActorAdapter : RecyclerView.Adapter<ActorAdapter.ActorHolder>() {

    private val data = mutableListOf<Actor>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorHolder {
        return ActorHolder(
            ItemActorBinding.inflate(parent.inflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ActorHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount() = data.size

    fun setupData(newData: List<Actor>) {
        data.plusAssign(newData)
        notifyDataSetChanged()
    }

    class ActorHolder(
        private val binding: ItemActorBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(actor: Actor) {
            with(binding) {
                imageActorPhoto.setImageResource(actor.photo)
                textActorName.text = actor.name
            }
        }
    }
}