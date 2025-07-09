package com.mathkids.education.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mathkids.education.R
import com.mathkids.education.databinding.ItemGameModuleBinding
import com.mathkids.education.models.GameModule

/**
 * Adapter for displaying game modules in a grid layout
 */
class GameModuleAdapter(
    private var modules: List<GameModule>,
    private val onModuleClick: (GameModule) -> Unit
) : RecyclerView.Adapter<GameModuleAdapter.GameModuleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameModuleViewHolder {
        val binding = ItemGameModuleBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return GameModuleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GameModuleViewHolder, position: Int) {
        holder.bind(modules[position])
    }

    override fun getItemCount(): Int = modules.size

    fun updateModules(newModules: List<GameModule>) {
        modules = newModules
        notifyDataSetChanged()
    }

    fun updateUnlockStatus() {
        notifyDataSetChanged()
    }

    inner class GameModuleViewHolder(
        private val binding: ItemGameModuleBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(module: GameModule) {
            val context = binding.root.context
            
            // Set module info
            binding.tvModuleTitle.text = module.title
            binding.tvModuleDescription.text = module.description
            binding.ivModuleIcon.setImageResource(module.icon)
            
            // Set module color
            val moduleColor = ContextCompat.getColor(context, module.color)
            binding.iconBackground.backgroundTintList = 
                ContextCompat.getColorStateList(context, module.color)
            
            // Set difficulty
            val difficultyText = when (module.difficulty) {
                1 -> context.getString(R.string.difficulty_very_easy)
                2 -> context.getString(R.string.difficulty_easy)
                3 -> context.getString(R.string.difficulty_medium)
                4 -> context.getString(R.string.difficulty_hard)
                else -> context.getString(R.string.difficulty_easy)
            }
            binding.tvDifficulty.text = difficultyText
            
            // Set completion status
            binding.tvCompletion.text = "0/${module.totalLevels}"
            
            // Set progress
            binding.progressModule.progress = 0 // TODO: Get actual progress
            
            // Handle locked/unlocked state
            if (module.isUnlocked) {
                // Unlocked module
                binding.ivLockIcon.visibility = android.view.View.GONE
                binding.btnPlay.isEnabled = true
                binding.btnPlay.text = context.getString(R.string.start)
                binding.btnPlay.backgroundTintList = 
                    ContextCompat.getColorStateList(context, module.color)
                
                // Set card alpha to normal
                binding.root.alpha = 1.0f
                
            } else {
                // Locked module
                binding.ivLockIcon.visibility = android.view.View.VISIBLE
                binding.btnPlay.isEnabled = false
                binding.btnPlay.text = context.getString(R.string.locked)
                binding.btnPlay.backgroundTintList = 
                    ContextCompat.getColorStateList(context, R.color.button_disabled)
                
                // Set card alpha to indicate disabled state
                binding.root.alpha = 0.6f
            }
            
            // Set stars (TODO: Get actual stars from progress)
            updateStars(0) // Default to 0 stars
            
            // Set click listener
            binding.root.setOnClickListener {
                onModuleClick(module)
            }
            
            binding.btnPlay.setOnClickListener {
                if (module.isUnlocked) {
                    onModuleClick(module)
                }
            }
        }
        
        private fun updateStars(starCount: Int) {
            val stars = listOf(
                binding.ivStar1,
                binding.ivStar2,
                binding.ivStar3
            )
            
            stars.forEachIndexed { index, star ->
                if (index < starCount) {
                    star.setColorFilter(
                        ContextCompat.getColor(binding.root.context, R.color.star_filled)
                    )
                } else {
                    star.setColorFilter(
                        ContextCompat.getColor(binding.root.context, R.color.star_empty)
                    )
                }
            }
        }
    }
}
