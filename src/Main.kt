import data.Data
import data.TestData
import data.TrainData
import mnist.Mnist
import neuralnetwork.NeuralNetwork
import neuralnetwork.Tensor
import trainer.Trainer
import java.awt.BorderLayout
import java.util.*
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.border.EmptyBorder

object Main {
    private lateinit var data: Data
    private lateinit var neuralNetwork: NeuralNetwork
    private var bestResult: Double? = 1700.0

    @JvmStatic
    fun main(args: Array<String>) {
        execute()
    }

    private val trainSamples = 20000
    private val testSamples = 4000

    private fun execute() {

        data = getDataSet()

//        neuralNetwork = NeuralNetwork.load("snapshots/neural_net_backup")

        neuralNetwork = NeuralNetwork.builder()
                .withLayers(data.testData.input.columnsCount, 160, 160, 160, data.testData.output.columnsCount)
                .withBias()
                .build()

        val trainer = Trainer.builder(neuralNetwork, data)
                .withLearningRate(0.01)
                .batchSize(100)
                .loops(32)
                .withRegularizationCoefficient(0.00000001)
                .drawAccuracyGraph()
                .drawErrorGraph()
                .build()

        trainer.startTraining()
        trainer.onErrorUpdated = { error, accuracy ->
            if (accuracy < bestResult!!) {
                bestResult = accuracy
                neuralNetwork.persist("neural_net")
            }
            println("Error: $error Accuracy: $accuracy")
        }
        val scanner = Scanner(System.`in`)
        var command = 0;
        while (command != 1) {
            command = scanner.nextInt()
        }
        trainer.stopTraining()
        startTesting()
        neuralNetwork.persist("snapshots/neural_net_backup")
    }

    private fun startTesting() {
        val frame = JFrame()
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.setBounds(100, 100, 450, 400)
        val contentPane = JPanel()
        contentPane.border = EmptyBorder(5, 5, 5, 5)
        contentPane.layout = BorderLayout(0, 0)

        val mnistTestData = Mnist.getMnistData(200, 300, "/res/test-images", "/res/test-labels")

        val p = TestPanel(TestData(Tensor(mnistTestData.images), Tensor(mnistTestData.labels)), neuralNetwork)
        p.nextImage()

        contentPane.add(p, BorderLayout.CENTER)

        frame.contentPane = contentPane
        frame.isVisible = true

        for (i in 0..100) {
            Thread.sleep(1000)
            p.nextImage()
            frame.repaint()
        }
    }

    private fun getDataSet(): Data {
        val mnistTrainData = Mnist.getMnistData(0, trainSamples, "/res/train-images", "/res/train-labels")
        val mnistTestData = Mnist.getMnistData(0, testSamples, "/res/test-images", "/res/test-labels")
        val trainData = TrainData(Tensor(mnistTrainData.images), Tensor(mnistTrainData.labels))
        val testData = TestData(Tensor(mnistTestData.images), Tensor(mnistTestData.labels))
        return Data(trainData, testData)
    }
}