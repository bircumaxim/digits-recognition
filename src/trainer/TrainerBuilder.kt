package trainer

import data.Data
import neuralnetwork.NeuralNetwork
import neuralnetwork.activation.SigmoidPrime
import trainer.errorfunctions.ErrorFunction
import trainer.errorfunctions.MSE
import trainer.trainigalgorithms.BackPropagation

class TrainerBuilder(private val neuralNetwork: NeuralNetwork, private val data: Data) {

    private var shuffleData: Boolean = false
    private var loops = 100
    private var batchSize = 100
    private var regularizationCoefficient = 0.0001
    private var drawErrorGraph = false
    private var drawAccuracyGraph = false
    private var learningRate = 0.1
    private var errorFunction = MSE()
    private var trainingAlgorithm: BackPropagation = BackPropagation(neuralNetwork, errorFunction, SigmoidPrime(), learningRate)

    fun withTrainingAlgorithm(trainingAlgorithm: BackPropagation): TrainerBuilder {
        this.trainingAlgorithm = trainingAlgorithm
        return this
    }

    fun withErrorFunction(errorFunction: ErrorFunction): TrainerBuilder {
        this.trainingAlgorithm.errorFunction = errorFunction
        return this
    }

    fun withLearningRate(learningRate: Double): TrainerBuilder {
        this.learningRate = learningRate
        return this
    }

    fun drawErrorGraph(): TrainerBuilder {
        drawErrorGraph = true
        return this
    }

    fun drawAccuracyGraph(): TrainerBuilder {
        drawAccuracyGraph = true
        return this
    }

    fun batchSize(batchSize: Int): TrainerBuilder {
        this.batchSize = batchSize
        return this
    }

    fun loops(loops: Int): TrainerBuilder {
        this.loops = loops
        return this
    }

    fun build(): Trainer {
        trainingAlgorithm.regularizationCoefficient = regularizationCoefficient
        trainingAlgorithm.errorFunction = errorFunction
        trainingAlgorithm.learningRate = learningRate
        return Trainer(
                neuralNetwork,
                data,
                trainingAlgorithm,
                batchSize,
                loops,
                drawErrorGraph,
                drawAccuracyGraph
        )
    }

    fun withRegularizationCoefficient(regularizationCoefficient: Double): TrainerBuilder {
        this.regularizationCoefficient = regularizationCoefficient
        return this
    }
}
