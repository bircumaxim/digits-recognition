import data.TestData
import neuralnetwork.NeuralNetwork
import neuralnetwork.Tensor
import java.awt.*
import javax.swing.JPanel

class TestPanel(private val data: TestData, private val neuralNetwork: NeuralNetwork) : JPanel()  {

    private var currentSampleIndex: Int = 0

    private var imageBuffer = IntArray(784)
    private var labelBuffer = -1

    fun nextImage() {
        if (currentSampleIndex == data.input.rowsCount - 1) {
            currentSampleIndex = 0
        }
        val sample = data.input.data[currentSampleIndex]
        neuralNetwork.feedForward(Tensor(arrayOf(sample)))
        for (i in 0 until sample.size) {
            imageBuffer[i] = (sample[i] * 100).toInt()
        }
        labelBuffer = indexOfMax(neuralNetwork.output.data[0].map { (it * 10).toInt() })!!
        currentSampleIndex++
    }

    public override fun paintComponent(g: Graphics) {
        val g2d = g as Graphics2D
        val w = this.width
        val h = this.height
        g.clearRect(0, 0, w, h)
        for (i in 0..783) {
            val x = i % 28 * w / 28
            val y = i / 28 * h / 28
            g2d.color = Color(imageBuffer[i])
            g2d.fillRect(x, y, w / 28 + 1, h / 28 + 1)
        }
        val comp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .3f)
        g2d.composite = comp
        g2d.paint = Color.red
        g2d.font = Font("Times Roman", Font.PLAIN, h / 3)
        g2d.drawString("" + labelBuffer, h / 14, w / 4)
    }

    fun indexOfMax(a: List<Int>): Int? {
        if (a.isEmpty())
            return null

        var max: Int = Integer.MIN_VALUE
        var maxPosition: Int = 0

        for (i in a.indices) {

            if (a[i] >= max) {
                max = a[i]
                maxPosition = i
            }

        }

        return maxPosition
    }
}