package com.mathkids.education.activities

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.mathkids.education.MathKidsApplication
import com.mathkids.education.R
import com.mathkids.education.adapters.AnswerButtonAdapter
import com.mathkids.education.databinding.ActivityCountingBinding
import com.mathkids.education.models.CountingExercise
import com.mathkids.education.viewmodels.CountingViewModel
import kotlinx.coroutines.*

/**
 * Counting Activity - Learn to count objects
 * Interactive counting game with visual objects and audio feedback
 */
class CountingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCountingBinding
    private lateinit var viewModel: CountingViewModel
    private lateinit var app: MathKidsApplication
    private lateinit var answerAdapter: AnswerButtonAdapter
    
    private var currentExercise: CountingExercise? = null
    private val objectViews = mutableListOf<ImageView>()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        binding = ActivityCountingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        app = MathKidsApplication.getInstance(this)
        viewModel = ViewModelProvider(this)[CountingViewModel::class.java]
        
        setupUI()
        setupObservers()
        loadNextExercise()
    }

    private fun setupUI() {
        // Setup toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
        
        // Setup answer buttons grid
        answerAdapter = AnswerButtonAdapter { answer ->
            onAnswerSelected(answer)
        }
        
        binding.rvAnswers.apply {
            layoutManager = GridLayoutManager(this@CountingActivity, 2)
            adapter = answerAdapter
        }
        
        // Setup control buttons
        binding.btnNext.setOnClickListener {
            app.audioManager.playClickSound()
            loadNextExercise()
        }
        
        binding.btnHint.setOnClickListener {
            app.audioManager.playClickSound()
            showHint()
        }
        
        binding.btnRepeat.setOnClickListener {
            app.audioManager.playClickSound()
            repeatInstruction()
        }
        
        // Setup progress
        updateProgress()
    }

    private fun setupObservers() {
        viewModel.currentExercise.observe(this) { exercise ->
            currentExercise = exercise
            displayExercise(exercise)
        }
        
        viewModel.score.observe(this) { score ->
            binding.tvScore.text = getString(R.string.score_format, score)
        }
        
        viewModel.level.observe(this) { level ->
            binding.tvLevel.text = getString(R.string.level_format, level)
        }
        
        viewModel.isCorrect.observe(this) { isCorrect ->
            if (isCorrect != null) {
                handleAnswer(isCorrect)
            }
        }
    }

    private fun loadNextExercise() {
        binding.progressBar.visibility = View.VISIBLE
        binding.btnNext.isEnabled = false
        
        coroutineScope.launch {
            delay(500) // Small delay for better UX
            viewModel.generateNewExercise()
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun displayExercise(exercise: CountingExercise) {
        // Clear previous objects
        clearObjects()
        
        // Display instruction
        binding.tvInstruction.text = exercise.instruction
        app.audioManager.speakText(exercise.instruction)
        
        // Generate and display objects
        generateObjects(exercise.objectCount, exercise.objectType)
        
        // Setup answer buttons
        answerAdapter.updateAnswers(exercise.answers)
        
        // Reset UI state
        binding.btnNext.isEnabled = false
        binding.btnNext.visibility = View.GONE
        binding.rvAnswers.visibility = View.VISIBLE
    }

    private fun generateObjects(count: Int, objectType: String) {
        val objectContainer = binding.objectContainer
        objectContainer.removeAllViews()
        objectViews.clear()
        
        val objectDrawable = getObjectDrawable(objectType)
        val containerWidth = resources.displayMetrics.widthPixels - 64 // Account for margins
        val containerHeight = 400 // Fixed height for object area
        
        coroutineScope.launch {
            for (i in 0 until count) {
                delay(200) // Stagger object appearance
                
                val imageView = ImageView(this@CountingActivity).apply {
                    setImageResource(objectDrawable)
                    layoutParams = android.widget.FrameLayout.LayoutParams(
                        80, 80 // Object size in dp
                    )
                    
                    // Random position within container
                    val x = (Math.random() * (containerWidth - 80)).toFloat()
                    val y = (Math.random() * (containerHeight - 80)).toFloat()
                    translationX = x
                    translationY = y
                    
                    // Random rotation for natural look
                    rotation = (Math.random() * 30 - 15).toFloat()
                    
                    // Start invisible for animation
                    alpha = 0f
                    scaleX = 0f
                    scaleY = 0f
                }
                
                objectContainer.addView(imageView)
                objectViews.add(imageView)
                
                // Animate object appearance
                animateObjectAppearance(imageView)
            }
        }
    }

    private fun animateObjectAppearance(imageView: ImageView) {
        val fadeIn = ObjectAnimator.ofFloat(imageView, "alpha", 0f, 1f)
        val scaleXIn = ObjectAnimator.ofFloat(imageView, "scaleX", 0f, 1f)
        val scaleYIn = ObjectAnimator.ofFloat(imageView, "scaleY", 0f, 1f)
        
        AnimatorSet().apply {
            playTogether(fadeIn, scaleXIn, scaleYIn)
            duration = 300
            start()
        }
        
        // Add bounce effect
        imageView.animate()
            .scaleX(1.1f)
            .scaleY(1.1f)
            .setDuration(150)
            .withEndAction {
                imageView.animate()
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(150)
                    .start()
            }
            .start()
    }

    private fun getObjectDrawable(objectType: String): Int {
        return when (objectType) {
            "apple" -> R.drawable.ic_apple
            "star" -> R.drawable.ic_star
            "heart" -> R.drawable.ic_heart
            "circle" -> R.drawable.ic_circle
            "triangle" -> R.drawable.ic_triangle
            "square" -> R.drawable.ic_square
            "flower" -> R.drawable.ic_flower
            "balloon" -> R.drawable.ic_balloon
            else -> R.drawable.ic_circle
        }
    }

    private fun onAnswerSelected(answer: Int) {
        app.audioManager.playClickSound()
        viewModel.checkAnswer(answer)
    }

    private fun handleAnswer(isCorrect: Boolean) {
        if (isCorrect) {
            // Correct answer
            app.audioManager.playSuccessSound()
            app.audioManager.speakPositiveFeedback()
            
            // Highlight correct objects
            highlightObjects(true)
            
            // Show next button
            binding.btnNext.visibility = View.VISIBLE
            binding.btnNext.isEnabled = true
            binding.rvAnswers.visibility = View.GONE
            
            // Celebrate animation
            celebrateCorrectAnswer()
            
        } else {
            // Wrong answer
            app.audioManager.playErrorSound()
            app.audioManager.speakEncouragement()
            
            // Shake animation for feedback
            shakeObjects()
        }
    }

    private fun highlightObjects(correct: Boolean) {
        objectViews.forEach { imageView ->
            if (correct) {
                // Green highlight for correct
                imageView.animate()
                    .scaleX(1.2f)
                    .scaleY(1.2f)
                    .setDuration(200)
                    .withEndAction {
                        imageView.animate()
                            .scaleX(1f)
                            .scaleY(1f)
                            .setDuration(200)
                            .start()
                    }
                    .start()
            }
        }
    }

    private fun shakeObjects() {
        objectViews.forEach { imageView ->
            val shake = ObjectAnimator.ofFloat(imageView, "translationX", 
                imageView.translationX, 
                imageView.translationX + 10, 
                imageView.translationX - 10, 
                imageView.translationX + 10, 
                imageView.translationX)
            shake.duration = 500
            shake.start()
        }
    }

    private fun celebrateCorrectAnswer() {
        // Confetti animation
        binding.lottieSuccess.apply {
            visibility = View.VISIBLE
            playAnimation()
        }
        
        // Hide confetti after animation
        coroutineScope.launch {
            delay(2000)
            binding.lottieSuccess.visibility = View.GONE
        }
    }

    private fun clearObjects() {
        binding.objectContainer.removeAllViews()
        objectViews.clear()
    }

    private fun showHint() {
        currentExercise?.let { exercise ->
            // Highlight objects one by one
            var count = 0
            objectViews.forEach { imageView ->
                coroutineScope.launch {
                    delay(count * 300L)
                    
                    // Highlight object
                    imageView.animate()
                        .scaleX(1.3f)
                        .scaleY(1.3f)
                        .setDuration(200)
                        .withEndAction {
                            imageView.animate()
                                .scaleX(1f)
                                .scaleY(1f)
                                .setDuration(200)
                                .start()
                        }
                        .start()
                    
                    // Speak number
                    app.audioManager.speakNumber(count + 1)
                }
                count++
            }
            
            // Speak final answer
            coroutineScope.launch {
                delay(objectViews.size * 300L + 500)
                app.audioManager.speakText("Tổng cộng có ${exercise.objectCount} ${exercise.objectType}")
            }
        }
    }

    private fun repeatInstruction() {
        currentExercise?.let { exercise ->
            app.audioManager.speakText(exercise.instruction)
        }
    }

    private fun updateProgress() {
        val progress = viewModel.getProgress()
        binding.progressIndicator.progress = progress
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }
}
