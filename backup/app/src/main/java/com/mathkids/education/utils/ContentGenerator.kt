package com.mathkids.education.utils

import com.mathkids.education.models.*
import kotlin.random.Random

/**
 * Content Generator for infinite exercise creation
 * Generates math problems, counting exercises, and number recognition tasks
 */
class ContentGenerator {

    private val random = Random.Default
    
    // Pre-generated content cache for better performance
    private val countingExerciseCache = mutableListOf<CountingExercise>()
    private val additionExerciseCache = mutableListOf<AdditionExercise>()
    private val subtractionExerciseCache = mutableListOf<SubtractionExercise>()
    private val numberRecognitionCache = mutableListOf<NumberRecognitionExercise>()

    /**
     * Generate counting exercise based on difficulty level
     */
    fun generateCountingExercise(difficulty: Int): CountingExercise {
        // Check cache first
        val cached = countingExerciseCache.find { it.difficulty == difficulty }
        if (cached != null && countingExerciseCache.size > 10) {
            countingExerciseCache.remove(cached)
            return cached
        }

        val range = when (difficulty) {
            1 -> 1..5      // Very easy: 1-5 objects
            2 -> 3..10     // Easy: 3-10 objects
            3 -> 5..15     // Medium: 5-15 objects
            4 -> 10..20    // Hard: 10-20 objects
            else -> 1..10
        }

        val objectCount = range.random()
        val objectTypes = listOf("apple", "star", "heart", "circle", "triangle", "square")
        val objectType = objectTypes.random()
        
        // Generate wrong answers
        val wrongAnswers = generateWrongAnswers(objectCount, 3)
        val allAnswers = (wrongAnswers + objectCount).shuffled()

        return CountingExercise(
            id = generateId(),
            difficulty = difficulty,
            objectCount = objectCount,
            objectType = objectType,
            answers = allAnswers,
            correctAnswer = objectCount,
            instruction = "Đếm số ${getVietnameseObjectName(objectType)} trên màn hình"
        )
    }

    /**
     * Generate addition exercise
     */
    fun generateAdditionExercise(level: Int): AdditionExercise {
        val cached = additionExerciseCache.find { it.level == level }
        if (cached != null && additionExerciseCache.size > 10) {
            additionExerciseCache.remove(cached)
            return cached
        }

        val (num1, num2) = when (level) {
            1 -> Pair(random.nextInt(1, 4), random.nextInt(1, 4))     // 1-3 + 1-3
            2 -> Pair(random.nextInt(1, 6), random.nextInt(1, 6))     // 1-5 + 1-5
            3 -> Pair(random.nextInt(3, 8), random.nextInt(2, 6))     // 3-7 + 2-5
            4 -> Pair(random.nextInt(5, 11), random.nextInt(3, 8))    // 5-10 + 3-7
            else -> Pair(random.nextInt(1, 6), random.nextInt(1, 6))
        }

        val correctAnswer = num1 + num2
        val wrongAnswers = generateWrongAnswers(correctAnswer, 3)
        val allAnswers = (wrongAnswers + correctAnswer).shuffled()

        return AdditionExercise(
            id = generateId(),
            level = level,
            number1 = num1,
            number2 = num2,
            operator = "+",
            correctAnswer = correctAnswer,
            answers = allAnswers,
            instruction = "Tính $num1 + $num2 = ?",
            visualAid = generateVisualAid(num1, num2, "+")
        )
    }

    /**
     * Generate subtraction exercise
     */
    fun generateSubtractionExercise(level: Int): SubtractionExercise {
        val cached = subtractionExerciseCache.find { it.level == level }
        if (cached != null && subtractionExerciseCache.size > 10) {
            subtractionExerciseCache.remove(cached)
            return cached
        }

        val (minuend, subtrahend) = when (level) {
            1 -> {
                val min = random.nextInt(2, 6)  // 2-5
                val sub = random.nextInt(1, min) // Always less than minuend
                Pair(min, sub)
            }
            2 -> {
                val min = random.nextInt(5, 11) // 5-10
                val sub = random.nextInt(1, min)
                Pair(min, sub)
            }
            3 -> {
                val min = random.nextInt(8, 16) // 8-15
                val sub = random.nextInt(2, min)
                Pair(min, sub)
            }
            4 -> {
                val min = random.nextInt(10, 21) // 10-20
                val sub = random.nextInt(3, min)
                Pair(min, sub)
            }
            else -> {
                val min = random.nextInt(3, 8)
                val sub = random.nextInt(1, min)
                Pair(min, sub)
            }
        }

        val correctAnswer = minuend - subtrahend
        val wrongAnswers = generateWrongAnswers(correctAnswer, 3)
        val allAnswers = (wrongAnswers + correctAnswer).shuffled()

        return SubtractionExercise(
            id = generateId(),
            level = level,
            minuend = minuend,
            subtrahend = subtrahend,
            operator = "-",
            correctAnswer = correctAnswer,
            answers = allAnswers,
            instruction = "Tính $minuend - $subtrahend = ?",
            visualAid = generateVisualAid(minuend, subtrahend, "-")
        )
    }

    /**
     * Generate number recognition exercise
     */
    fun generateNumberRecognitionExercise(range: Int): NumberRecognitionExercise {
        val cached = numberRecognitionCache.find { it.range == range }
        if (cached != null && numberRecognitionCache.size > 10) {
            numberRecognitionCache.remove(cached)
            return cached
        }

        val targetNumber = random.nextInt(0, range + 1)
        val exerciseTypes = listOf("identify", "sequence", "compare", "match")
        val exerciseType = exerciseTypes.random()

        return when (exerciseType) {
            "identify" -> generateIdentifyNumberExercise(targetNumber, range)
            "sequence" -> generateSequenceExercise(targetNumber, range)
            "compare" -> generateCompareExercise(targetNumber, range)
            "match" -> generateMatchExercise(targetNumber, range)
            else -> generateIdentifyNumberExercise(targetNumber, range)
        }
    }

    private fun generateIdentifyNumberExercise(targetNumber: Int, range: Int): NumberRecognitionExercise {
        val wrongAnswers = generateWrongAnswers(targetNumber, 3, 0, range)
        val allAnswers = (wrongAnswers + targetNumber).shuffled()

        return NumberRecognitionExercise(
            id = generateId(),
            range = range,
            targetNumber = targetNumber,
            exerciseType = "identify",
            answers = allAnswers,
            correctAnswer = targetNumber,
            instruction = "Chọn số $targetNumber",
            visualRepresentation = generateNumberVisual(targetNumber)
        )
    }

    private fun generateSequenceExercise(targetNumber: Int, range: Int): NumberRecognitionExercise {
        val sequence = mutableListOf<Int>()
        val start = maxOf(0, targetNumber - 2)
        
        for (i in start until start + 4) {
            if (i <= range) {
                sequence.add(i)
            }
        }
        
        // Remove one number to create the gap
        val missingIndex = random.nextInt(1, sequence.size - 1)
        val missingNumber = sequence[missingIndex]
        sequence[missingIndex] = -1 // Placeholder for missing number

        val wrongAnswers = generateWrongAnswers(missingNumber, 3, 0, range)
        val allAnswers = (wrongAnswers + missingNumber).shuffled()

        return NumberRecognitionExercise(
            id = generateId(),
            range = range,
            targetNumber = missingNumber,
            exerciseType = "sequence",
            answers = allAnswers,
            correctAnswer = missingNumber,
            instruction = "Số nào bị thiếu trong dãy?",
            sequence = sequence
        )
    }

    private fun generateCompareExercise(targetNumber: Int, range: Int): NumberRecognitionExercise {
        val compareNumber = random.nextInt(0, range + 1)
        val isGreater = targetNumber > compareNumber
        
        val instruction = if (isGreater) {
            "Số nào lớn hơn $compareNumber?"
        } else {
            "Số nào nhỏ hơn $compareNumber?"
        }

        val wrongAnswers = if (isGreater) {
            generateWrongAnswers(targetNumber, 3, 0, compareNumber)
        } else {
            generateWrongAnswers(targetNumber, 3, compareNumber, range)
        }
        
        val allAnswers = (wrongAnswers + targetNumber).shuffled()

        return NumberRecognitionExercise(
            id = generateId(),
            range = range,
            targetNumber = targetNumber,
            exerciseType = "compare",
            answers = allAnswers,
            correctAnswer = targetNumber,
            instruction = instruction,
            compareNumber = compareNumber
        )
    }

    private fun generateMatchExercise(targetNumber: Int, range: Int): NumberRecognitionExercise {
        val wrongAnswers = generateWrongAnswers(targetNumber, 3, 0, range)
        val allAnswers = (wrongAnswers + targetNumber).shuffled()

        return NumberRecognitionExercise(
            id = generateId(),
            range = range,
            targetNumber = targetNumber,
            exerciseType = "match",
            answers = allAnswers,
            correctAnswer = targetNumber,
            instruction = "Chọn số tương ứng với hình ảnh",
            visualRepresentation = generateNumberVisual(targetNumber)
        )
    }

    private fun generateWrongAnswers(correctAnswer: Int, count: Int, min: Int = 0, max: Int = 20): List<Int> {
        val wrongAnswers = mutableSetOf<Int>()
        
        while (wrongAnswers.size < count) {
            val wrong = random.nextInt(min, max + 1)
            if (wrong != correctAnswer) {
                wrongAnswers.add(wrong)
            }
        }
        
        return wrongAnswers.toList()
    }

    private fun generateVisualAid(num1: Int, num2: Int, operator: String): VisualAid {
        return VisualAid(
            type = "objects",
            objects1 = generateObjectList(num1),
            objects2 = generateObjectList(num2),
            operator = operator
        )
    }

    private fun generateObjectList(count: Int): List<String> {
        val objects = listOf("apple", "star", "heart", "circle")
        val selectedObject = objects.random()
        return List(count) { selectedObject }
    }

    private fun generateNumberVisual(number: Int): String {
        // Generate visual representation (dots, objects, etc.)
        return "dots_$number"
    }

    private fun getVietnameseObjectName(objectType: String): String {
        return when (objectType) {
            "apple" -> "quả táo"
            "star" -> "ngôi sao"
            "heart" -> "trái tim"
            "circle" -> "hình tròn"
            "triangle" -> "hình tam giác"
            "square" -> "hình vuông"
            else -> objectType
        }
    }

    private fun generateId(): String {
        return "ex_${System.currentTimeMillis()}_${random.nextInt(1000, 9999)}"
    }

    /**
     * Pre-generate content for better performance
     */
    fun preGenerateContent() {
        // Pre-generate some exercises for each difficulty level
        repeat(5) {
            countingExerciseCache.add(generateCountingExercise(1))
            countingExerciseCache.add(generateCountingExercise(2))
            additionExerciseCache.add(generateAdditionExercise(1))
            additionExerciseCache.add(generateAdditionExercise(2))
            subtractionExerciseCache.add(generateSubtractionExercise(1))
            subtractionExerciseCache.add(generateSubtractionExercise(2))
            numberRecognitionCache.add(generateNumberRecognitionExercise(10))
        }
    }

    /**
     * Clear cache to free memory
     */
    fun clearCache() {
        countingExerciseCache.clear()
        additionExerciseCache.clear()
        subtractionExerciseCache.clear()
        numberRecognitionCache.clear()
    }
}
