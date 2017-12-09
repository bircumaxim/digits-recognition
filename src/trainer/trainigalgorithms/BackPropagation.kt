package trainer.trainigalgorithms


import neuralnetwork.NeuralNetwork
import neuralnetwork.Tensor
import neuralnetwork.activation.ActivationPrime
import trainer.errorfunctions.ErrorFunction

class BackPropagation(
        neuralNetwork: NeuralNetwork,
        errorFunction: ErrorFunction,
        private val activationPrimeFunction: ActivationPrime,
        learningRate: Double,
        var regularizationCoefficient: Double = 0.0001) : TrainingAlgorithm(neuralNetwork, errorFunction, learningRate) {

    val gradients: Array<Tensor> = neuralNetwork.weights.copyOf()

    override fun getError(actualOutput: Tensor, desiredOutput: Tensor): Double {
        return errorFunction(actualOutput, desiredOutput) + regularizationCoefficient / neuralNetwork.weights[0].rowsCount * neuralNetwork.weights.sumByDouble { it.square().sum() }
    }

    override fun execute(inputData: Tensor, desiredOutput: Tensor) {
        backPropagate(inputData, desiredOutput)
        adjustWeights()
    }

    private fun adjustWeights() {
        for (i in 0 until neuralNetwork.weights.size) {
            neuralNetwork.weights[i] = neuralNetwork.weights[i].subtract(gradients[i].multiply(learningRate))
        }
    }

    private fun backPropagate(inputData: Tensor, desiredOutput: Tensor) {
        var delta: Tensor = errorFunction.derivative(neuralNetwork.output, desiredOutput)
                .multiplyElementWise(neuralNetwork.preActivations.last().applyToEachElement(activationPrimeFunction))

        for (i in neuralNetwork.weights.size - 1 downTo 1) {
            gradients[i] = delta.preMultiply(neuralNetwork.activations[i - 1].transpose()).add(neuralNetwork.weights[i].multiply(regularizationCoefficient))
            delta = delta.multiply(neuralNetwork.weights[i].transpose()).multiplyElementWise(neuralNetwork.preActivations[i - 1].applyToEachElement(activationPrimeFunction))
        }

        gradients[0] = delta.preMultiply(inputData.transpose())
    }
}
