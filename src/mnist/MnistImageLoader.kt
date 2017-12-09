package mnist

import javax.imageio.ImageIO
import java.awt.*
import java.awt.image.BufferedImage
import java.io.File

object MnistImageLoader {

    @Throws(Exception::class)
    fun loadImage(path: String): BufferedImage {
        return resize(ImageIO.read(File(path)), 28, 28)
    }

    fun resize(img: BufferedImage, newW: Int, newH: Int): BufferedImage {
        val tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH)
        val dimg = BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB)

        val g2d = dimg.createGraphics()
        g2d.drawImage(tmp, 0, 0, null)
        g2d.dispose()

        return dimg
    }

    fun bufferedImageToArray(img: BufferedImage): Array<IntArray> {
        val arr = Array(img.width) { IntArray(img.height) }

        for (i in 0..img.width - 1)
            for (j in 0..img.height - 1)
                arr[i][j] = img.getRGB(i, j)

        return arr
    }

    fun bufferedImageRedToArray(img: BufferedImage): Array<IntArray> {
        val arr = Array(img.width) { IntArray(img.height) }

        for (i in 0..img.width - 1)
            for (j in 0..img.height - 1)
                arr[i][j] = Color(img.getRGB(i, j)).red

        return arr
    }

    fun intArrayToDoubleArray(i: Array<IntArray>): DoubleArray {
        val ar = DoubleArray(i.size * i[0].size)
        for (j in i.indices) {
            for (n in 0..i[0].size - 1) {
                ar[j * i.size + n] = i[n][j].toDouble() / 256.toDouble()
            }
        }
        return ar
    }

    fun invert(ar: DoubleArray): DoubleArray {
        for (j in ar.indices) {
            ar[j] = 0.9999 - ar[j]
        }
        return ar
    }

}
