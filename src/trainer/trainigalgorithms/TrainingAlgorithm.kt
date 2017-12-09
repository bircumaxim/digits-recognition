package trainer.trainigalgorithms

import neuralnetwork.NeuralNetwork
import neuralnetwork.Tensor
import trainer.errorfunctions.ErrorFunction

abstract class TrainingAlgorithm(protected val neuralNetwork: NeuralNetwork, var errorFunction: ErrorFunction, var learningRate: Double) {
    abstract fun execute(inputData: Tensor, desiredOutput: Tensor)
    abstract fun getError(actualOutput: Tensor, desiredOutput: Tensor): Double
}
