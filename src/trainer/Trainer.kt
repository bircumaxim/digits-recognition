package trainer

import data.Data
import data.TrainData
import neuralnetwork.NeuralNetwork
import trainer.trainigalgorithms.TrainingAlgorithm
import utils.LineChart

class Trainer(
        private val neuralNetwork: NeuralNetwork,
        private val data: Data,
        private val trainingAlgorithm: TrainingAlgorithm,
        private val batchSize: Int,
        private val loops: Int,
        private val drawErrorGraph: Boolean,
        private val drawAccuracyGraph: Boolean) {

    private val lineChart = LineChart()
    private var isTraining: Boolean = false
    private lateinit var trainData: TrainData

    var onErrorUpdated: ((Double, Double) -> Unit)? = null

    private fun epoch() {
        neuralNetwork.feedForward(trainData.input)
        trainingAlgorithm.execute(trainData.input, trainData.output)
    }

    fun startTraining() {
        isTraining = true
        var itterations = 0
        Thread({
            while (isTraining) {
                itterations++
                runLoop()
                val error = getError()
                val accuracy = getAccuracy()
                onErrorUpdated?.invoke(error, accuracy)
                drawChart(error, accuracy, itterations)
            }
        }).start()
    }

    private fun runLoop() {
        trainData = data.trainData.extractBatch(batchSize)
        for (i in 0 until loops) {
            epoch()
        }
    }

    private fun drawChart(error: Double, accuracy: Double, iterations: Int) {
        if (drawErrorGraph) {
            lineChart.addNewValue(error, "error", iterations)
        }

        if (drawAccuracyGraph) {
            lineChart.addNewValue(accuracy, "accuracy", iterations)
        }
    }

    private fun getError(): Double {
        neuralNetwork.feedForward(data.trainData.input)
        return trainingAlgorithm.getError(neuralNetwork.output, data.trainData.output)
    }

    private fun getAccuracy(): Double {
        neuralNetwork.feedForward(data.testData.input)
        return trainingAlgorithm.getError(neuralNetwork.output, data.testData.output)
    }

    fun stopTraining() {
        isTraining = false
    }

    companion object {
        fun builder(neuralNetwork: NeuralNetwork, data: Data) = TrainerBuilder(neuralNetwork, data)
    }
}
