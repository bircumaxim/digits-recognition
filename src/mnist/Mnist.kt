package mnist

import java.io.File

class Mnist {
    companion object {
        fun getMnistData(start: Int, end: Int, imagesPath: String, labelsPath: String): MnistData {
            val images: Array<DoubleArray> = Array<DoubleArray>(end - start + 1, { kotlin.DoubleArray(28 * 28) })
            val labels: Array<DoubleArray> = Array<DoubleArray>(end - start + 1, { kotlin.DoubleArray(10) })

            try {

                val path = File("").absolutePath

                val imageData = MnistImageFile(path + imagesPath, "rw")
                val labelData = MnistLabelFile(path + labelsPath, "rw")

                var temp = 0

                for(i in 0..start) {


                    labelData.readLabel()
                    for (j in 0 until 28 * 28) {
                        imageData.read().toDouble() / 256.toDouble()
                    }

                    imageData.next()
                    labelData.next()
                }

                for (i in start..end) {

                    if (i % 100 == 0) {
                        println("prepared: " + i)
                    }

                    val image = DoubleArray(28 * 28)
                    val label = DoubleArray(10)

                    label[labelData.readLabel()] = 1.0
                    for (j in 0 until 28 * 28) {
                        image[j] = imageData.read().toDouble() / 256.toDouble()
                    }

                    images[temp] = image
                    labels[temp] = label

                    imageData.next()
                    labelData.next()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }



            return MnistData(images, labels)
        }
    }
}
