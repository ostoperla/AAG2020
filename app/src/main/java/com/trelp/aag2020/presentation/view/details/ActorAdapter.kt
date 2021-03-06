package com.trelp.aag2020.presentation.view.details

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.trelp.aag2020.domain.entity.Actor
import com.trelp.aag2020.databinding.ItemActorBinding
import com.trelp.aag2020.presentation.view.common.utils.inflater
import com.trelp.aag2020.presentation.view.common.utils.loadImage

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
        data.clear()
        data.plusAssign(newData)
        notifyDataSetChanged()
    }

    class ActorHolder(
        private val binding: ItemActorBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(actor: Actor) {
            with(binding) {
                imageActorPhoto.loadImage(actor.profilePath)
                textActorName.text = actor.name
            }
        }
    }
}