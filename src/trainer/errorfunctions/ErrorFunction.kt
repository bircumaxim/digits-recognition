package trainer.errorfunctions

import neuralnetwork.Tensor

abstract class ErrorFunction : (Tensor, Tensor) -> Double {
    abstract fun derivative(actualOutput: Tensor, desiredOutput: Tensor): Tensor
}
