package trainer.errorfunctions

import neuralnetwork.Tensor


class MSE : ErrorFunction() {
    override fun invoke(actualOutput: Tensor, desiredOutput: Tensor): Double {
        return 0.5 * desiredOutput.subtract(actualOutput).square().sum()
    }

    override fun derivative(actualOutput: Tensor, desiredOutput: Tensor): Tensor {
        return actualOutput.subtract(desiredOutput)
    }
}
