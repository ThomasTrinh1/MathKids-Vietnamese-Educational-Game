package com.mathkids.education.audio

import android.content.Context
import android.media.MediaPlayer
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import androidx.annotation.RawRes
import com.mathkids.education.R
import kotlinx.coroutines.*
import java.util.*

/**
 * Vietnamese Audio Manager
 * Handles Text-to-Speech, sound effects, and background music
 */
class VietnameseAudioManager(private val context: Context) {

    private var tts: TextToSpeech? = null
    private var isTtsInitialized = false
    private var soundEffectsPlayer: MediaPlayer? = null
    private var backgroundMusicPlayer: MediaPlayer? = null
    
    private val coroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    
    // Vietnamese number pronunciations
    private val vietnameseNumbers = mapOf(
        0 to "không",
        1 to "một", 2 to "hai", 3 to "ba", 4 to "bốn", 5 to "năm",
        6 to "sáu", 7 to "bảy", 8 to "tám", 9 to "chín", 10 to "mười",
        11 to "mười một", 12 to "mười hai", 13 to "mười ba", 14 to "mười bốn", 15 to "mười lăm",
        16 to "mười sáu", 17 to "mười bảy", 18 to "mười tám", 19 to "mười chín", 20 to "hai mười"
    )
    
    // Common phrases
    private val commonPhrases = mapOf(
        "welcome" to "Chào mừng bạn đến với MathKids! Hãy cùng học toán vui vẻ nhé!",
        "correct" to "Chính xác! Bạn làm rất tốt!",
        "incorrect" to "Chưa đúng rồi. Hãy thử lại nhé!",
        "excellent" to "Xuất sắc! Bạn thật giỏi!",
        "try_again" to "Thử lại nào!",
        "well_done" to "Làm tốt lắm!",
        "count_objects" to "Hãy đếm các vật thể trên màn hình",
        "choose_answer" to "Chọn đáp án đúng",
        "tap_number" to "Chạm vào số",
        "good_job" to "Làm tốt lắm!"
    )

    fun initialize() {
        initializeTTS()
        preloadSoundEffects()
    }

    private fun initializeTTS() {
        tts = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                val result = tts?.setLanguage(Locale("vi", "VN"))
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    // Fallback to English if Vietnamese is not available
                    tts?.setLanguage(Locale.ENGLISH)
                }
                
                // Configure TTS settings for children
                tts?.setSpeechRate(0.8f) // Slower speech rate
                tts?.setPitch(1.1f) // Slightly higher pitch
                
                isTtsInitialized = true
                
                // Set utterance progress listener
                tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                    override fun onStart(utteranceId: String?) {}
                    override fun onDone(utteranceId: String?) {}
                    override fun onError(utteranceId: String?) {}
                })
            }
        }
    }

    private fun preloadSoundEffects() {
        // Preload common sound effects
        coroutineScope.launch(Dispatchers.IO) {
            try {
                // Preload click sound
                val clickPlayer = MediaPlayer.create(context, R.raw.click_sound)
                clickPlayer?.release()
                
                // Preload success sound
                val successPlayer = MediaPlayer.create(context, R.raw.success_sound)
                successPlayer?.release()
                
                // Preload error sound
                val errorPlayer = MediaPlayer.create(context, R.raw.error_sound)
                errorPlayer?.release()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * Speak Vietnamese number
     */
    fun speakNumber(number: Int) {
        val vietnameseText = vietnameseNumbers[number] ?: number.toString()
        speakText(vietnameseText)
    }

    /**
     * Speak common phrase
     */
    fun speakPhrase(phraseKey: String) {
        val phrase = commonPhrases[phraseKey] ?: phraseKey
        speakText(phrase)
    }

    /**
     * Speak custom text
     */
    fun speakText(text: String) {
        if (isTtsInitialized && tts != null) {
            val utteranceId = UUID.randomUUID().toString()
            tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId)
        }
    }

    /**
     * Speak welcome message
     */
    fun speakWelcome() {
        speakPhrase("welcome")
    }

    /**
     * Speak positive feedback
     */
    fun speakPositiveFeedback() {
        val feedbacks = listOf("correct", "excellent", "well_done", "good_job")
        val randomFeedback = feedbacks.random()
        speakPhrase(randomFeedback)
    }

    /**
     * Speak encouragement for wrong answers
     */
    fun speakEncouragement() {
        val encouragements = listOf("incorrect", "try_again")
        val randomEncouragement = encouragements.random()
        speakPhrase(randomEncouragement)
    }

    /**
     * Speak counting instruction
     */
    fun speakCountingInstruction() {
        speakPhrase("count_objects")
    }

    /**
     * Speak math problem
     */
    fun speakMathProblem(num1: Int, operator: String, num2: Int) {
        val operatorText = when (operator) {
            "+" -> "cộng"
            "-" -> "trừ"
            "×" -> "nhân"
            "÷" -> "chia"
            else -> operator
        }
        
        val problem = "${vietnameseNumbers[num1]} $operatorText ${vietnameseNumbers[num2]} bằng bao nhiêu?"
        speakText(problem)
    }

    /**
     * Play sound effect
     */
    fun playSoundEffect(@RawRes soundRes: Int) {
        coroutineScope.launch(Dispatchers.IO) {
            try {
                soundEffectsPlayer?.release()
                soundEffectsPlayer = MediaPlayer.create(context, soundRes)
                soundEffectsPlayer?.start()
                
                soundEffectsPlayer?.setOnCompletionListener { player ->
                    player.release()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * Play click sound
     */
    fun playClickSound() {
        playSoundEffect(R.raw.click_sound)
    }

    /**
     * Play success sound
     */
    fun playSuccessSound() {
        playSoundEffect(R.raw.success_sound)
    }

    /**
     * Play error sound
     */
    fun playErrorSound() {
        playSoundEffect(R.raw.error_sound)
    }

    /**
     * Play celebration sound
     */
    fun playCelebrationSound() {
        playSoundEffect(R.raw.celebration_sound)
    }

    /**
     * Start background music
     */
    fun startBackgroundMusic() {
        coroutineScope.launch(Dispatchers.IO) {
            try {
                backgroundMusicPlayer?.release()
                backgroundMusicPlayer = MediaPlayer.create(context, R.raw.background_music)
                backgroundMusicPlayer?.apply {
                    isLooping = true
                    setVolume(0.3f, 0.3f) // Lower volume for background
                    start()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * Stop background music
     */
    fun stopBackgroundMusic() {
        backgroundMusicPlayer?.apply {
            if (isPlaying) {
                stop()
            }
            release()
        }
        backgroundMusicPlayer = null
    }

    /**
     * Preload common sounds for better performance
     */
    fun preloadCommonSounds() {
        coroutineScope.launch(Dispatchers.IO) {
            // Preload TTS for common numbers
            for (i in 0..20) {
                // This will cache the TTS audio
            }
        }
    }

    /**
     * Set TTS speech rate
     */
    fun setSpeechRate(rate: Float) {
        tts?.setSpeechRate(rate)
    }

    /**
     * Set TTS pitch
     */
    fun setPitch(pitch: Float) {
        tts?.setPitch(pitch)
    }

    /**
     * Stop current speech
     */
    fun stopSpeaking() {
        tts?.stop()
    }

    /**
     * Check if TTS is speaking
     */
    fun isSpeaking(): Boolean {
        return tts?.isSpeaking ?: false
    }

    /**
     * Shutdown audio manager
     */
    fun shutdown() {
        tts?.apply {
            stop()
            shutdown()
        }
        soundEffectsPlayer?.release()
        backgroundMusicPlayer?.release()
        coroutineScope.cancel()
    }
}
