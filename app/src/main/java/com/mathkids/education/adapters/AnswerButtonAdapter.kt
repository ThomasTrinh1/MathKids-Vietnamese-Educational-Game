package com.mathkids.education.adapters

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mathkids.education.R
import com.mathkids.education.databinding.ItemAnswerButtonBinding

/**
 * Adapter for answer buttons in game activities
 */
class AnswerButtonAdapter(
    private val onAnswerClick: (Int) -> Unit
) : RecyclerView.Adapter<AnswerButtonAdapter.AnswerButtonViewHolder>() {

    private var answers: List<Int> = emptyList()
    private var selectedAnswer: Int? = null
    private var isAnswerSelected = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswerButtonViewHolder {
        val binding = ItemAnswerButtonBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AnswerButtonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AnswerButtonViewHolder, position: Int) {
        holder.bind(answers[position])
    }

    override fun getItemCount(): Int = answers.size

    fun updateAnswers(newAnswers: List<Int>) {
        answers = newAnswers
        selectedAnswer = null
        isAnswerSelected = false
        notifyDataSetChanged()
    }

    fun setCorrectAnswer(correctAnswer: Int) {
        // This will be called after user selects an answer
        // to show which was correct
        notifyDataSetChanged()
    }

    fun resetSelection() {
        selectedAnswer = null
        isAnswerSelected = false
        notifyDataSetChanged()
    }

    inner class AnswerButtonViewHolder(
        private val binding: ItemAnswerButtonBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(answer: Int) {
            val context = binding.root.context
            
            // Set answer text
            binding.btnAnswer.text = answer.toString()
            
            // Reset button state
            binding.btnAnswer.isEnabled = !isAnswerSelected
            
            // Set button appearance based on selection state
            when {
                selectedAnswer == answer -> {
                    // This answer was selected
                    binding.btnAnswer.backgroundTintList = 
                        ContextCompat.getColorStateList(context, R.color.primary)
                    binding.btnAnswer.setTextColor(
                        ContextCompat.getColor(context, R.color.white)
                    )
                }
                isAnswerSelected -> {
                    // Another answer was selected, disable this one
                    binding.btnAnswer.backgroundTintList = 
                        ContextCompat.getColorStateList(context, R.color.button_disabled)
                    binding.btnAnswer.setTextColor(
                        ContextCompat.getColor(context, R.color.text_tertiary)
                    )
                }
                else -> {
                    // Default state
                    binding.btnAnswer.backgroundTintList = 
                        ContextCompat.getColorStateList(context, R.color.surface)
                    binding.btnAnswer.setTextColor(
                        ContextCompat.getColor(context, R.color.text_primary)
                    )
                }
            }
            
            // Set click listener
            binding.btnAnswer.setOnClickListener {
                if (!isAnswerSelected) {
                    selectedAnswer = answer
                    isAnswerSelected = true
                    
                    // Animate button press
                    animateButtonPress()
                    
                    // Notify adapter to update other buttons
                    notifyDataSetChanged()
                    
                    // Call the click handler
                    onAnswerClick(answer)
                }
            }
        }
        
        private fun animateButtonPress() {
            val scaleDown = AnimatorSet().apply {
                playTogether(
                    ObjectAnimator.ofFloat(binding.btnAnswer, "scaleX", 1f, 0.95f),
                    ObjectAnimator.ofFloat(binding.btnAnswer, "scaleY", 1f, 0.95f)
                )
                duration = 100
            }
            
            val scaleUp = AnimatorSet().apply {
                playTogether(
                    ObjectAnimator.ofFloat(binding.btnAnswer, "scaleX", 0.95f, 1f),
                    ObjectAnimator.ofFloat(binding.btnAnswer, "scaleY", 0.95f, 1f)
                )
                duration = 100
            }
            
            scaleDown.start()
            scaleDown.addListener(object : android.animation.AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: android.animation.Animator) {
                    scaleUp.start()
                }
            })
        }
    }
}
