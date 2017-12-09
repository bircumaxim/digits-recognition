package data

import neuralnetwork.Tensor
import java.util.*

class TrainData(val input: Tensor, val output: Tensor) {
    private val random = Random()

    fun extractBatch(batchSize: Int): TrainData {
        val batchInput = Array<DoubleArray>(batchSize, { kotlin.DoubleArray(input.columnsCount) })
        val batchOutput = Array<DoubleArray>(batchSize, { kotlin.DoubleArray(output.columnsCount) })

        for (i in 0 until batchSize) {
            val index = random.nextInt(input.rowsCount - 1)
            batchInput[i] = input.data[index]
            batchOutput[i] = output.data[index]
        }

        return TrainData(Tensor(batchInput), Tensor(batchOutput))
    }
}
