package com.mathkids.education.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.mathkids.education.MathKidsApplication
import com.mathkids.education.R
import com.mathkids.education.adapters.GameModuleAdapter
import com.mathkids.education.databinding.ActivityMainBinding
import com.mathkids.education.models.GameModule
import com.mathkids.education.viewmodels.MainViewModel
import com.mathkids.education.utils.SoundEffects

/**
 * Main Activity - Home screen with game modules
 * Displays available learning modules and navigation options
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var app: MathKidsApplication
    private lateinit var gameModuleAdapter: GameModuleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        app = MathKidsApplication.getInstance(this)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        
        setupUI()
        setupGameModules()
        setupObservers()
        
        // Play welcome sound
        app.audioManager.speakWelcome()
    }

    private fun setupUI() {
        // Setup toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        
        // Setup user greeting
        updateUserGreeting()
        
        // Setup navigation buttons
        binding.btnProgress.setOnClickListener {
            app.audioManager.playClickSound()
            startActivity(Intent(this, ProgressActivity::class.java))
        }
        
        binding.btnAchievements.setOnClickListener {
            app.audioManager.playClickSound()
            startActivity(Intent(this, AchievementsActivity::class.java))
        }
        
        binding.btnSettings.setOnClickListener {
            app.audioManager.playClickSound()
            startActivity(Intent(this, SettingsActivity::class.java))
        }
        
        // Setup floating action button for help
        binding.fabHelp.setOnClickListener {
            app.audioManager.playClickSound()
            showHelpDialog()
        }
    }

    private fun setupGameModules() {
        val gameModules = listOf(
            GameModule(
                id = "counting",
                title = getString(R.string.module_counting),
                description = getString(R.string.module_counting_desc),
                icon = R.drawable.ic_counting,
                color = R.color.module_counting,
                difficulty = 1,
                isUnlocked = true
            ),
            GameModule(
                id = "addition",
                title = getString(R.string.module_addition),
                description = getString(R.string.module_addition_desc),
                icon = R.drawable.ic_addition,
                color = R.color.module_addition,
                difficulty = 2,
                isUnlocked = viewModel.isModuleUnlocked("addition")
            ),
            GameModule(
                id = "subtraction",
                title = getString(R.string.module_subtraction),
                description = getString(R.string.module_subtraction_desc),
                icon = R.drawable.ic_subtraction,
                color = R.color.module_subtraction,
                difficulty = 3,
                isUnlocked = viewModel.isModuleUnlocked("subtraction")
            ),
            GameModule(
                id = "number_recognition",
                title = getString(R.string.module_number_recognition),
                description = getString(R.string.module_number_recognition_desc),
                icon = R.drawable.ic_numbers,
                color = R.color.module_numbers,
                difficulty = 1,
                isUnlocked = true
            )
        )
        
        gameModuleAdapter = GameModuleAdapter(gameModules) { module ->
            onGameModuleClicked(module)
        }
        
        binding.rvGameModules.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            adapter = gameModuleAdapter
            setHasFixedSize(true)
        }
    }

    private fun setupObservers() {
        // Observe progress updates
        viewModel.totalProgress.observe(this) { progress ->
            binding.progressBar.progress = progress
            binding.tvProgressText.text = getString(R.string.progress_format, progress)
        }
        
        // Observe daily streak
        viewModel.dailyStreak.observe(this) { streak ->
            binding.tvStreakCount.text = streak.toString()
            if (streak > 0) {
                binding.ivStreakIcon.visibility = View.VISIBLE
            }
        }
        
        // Observe stars earned
        viewModel.totalStars.observe(this) { stars ->
            binding.tvStarsCount.text = stars.toString()
        }
    }

    private fun updateUserGreeting() {
        val hour = java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY)
        val greeting = when (hour) {
            in 6..11 -> getString(R.string.good_morning)
            in 12..17 -> getString(R.string.good_afternoon)
            else -> getString(R.string.good_evening)
        }
        binding.tvGreeting.text = greeting
    }

    private fun onGameModuleClicked(module: GameModule) {
        app.audioManager.playClickSound()
        
        if (!module.isUnlocked) {
            app.audioManager.speakText(getString(R.string.module_locked))
            showModuleLockedDialog(module)
            return
        }
        
        val intent = when (module.id) {
            "counting" -> Intent(this, CountingActivity::class.java)
            "addition" -> Intent(this, AdditionActivity::class.java)
            "subtraction" -> Intent(this, SubtractionActivity::class.java)
            "number_recognition" -> Intent(this, NumberRecognitionActivity::class.java)
            else -> return
        }
        
        intent.putExtra("module_id", module.id)
        intent.putExtra("difficulty", module.difficulty)
        startActivity(intent)
        
        // Custom transition animation
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
    }

    private fun showHelpDialog() {
        // Show help dialog with instructions
        app.audioManager.speakText(getString(R.string.help_instructions))
    }

    private fun showModuleLockedDialog(module: GameModule) {
        // Show dialog explaining how to unlock the module
        val message = getString(R.string.unlock_module_message, module.title)
        app.audioManager.speakText(message)
    }

    override fun onResume() {
        super.onResume()
        // Refresh progress when returning from other activities
        viewModel.refreshProgress()
        gameModuleAdapter.updateUnlockStatus()
    }

    override fun onBackPressed() {
        // Show exit confirmation dialog
        app.audioManager.speakText(getString(R.string.exit_confirmation))
        super.onBackPressed()
    }
}
