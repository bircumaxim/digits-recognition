package mnist

import java.io.FileNotFoundException
import java.io.IOException


/**
 *
 * MNIST database label file.
 *
 */
class MnistLabelFile
/**
 * Creates new MNIST database label file ready for reading.
 *
 * @param name
 * the system-dependent filename
 * @param mode
 * the access mode
 * @throws IOException
 * @throws FileNotFoundException
 */
@Throws(IOException::class)
constructor(name: String, mode: String) : MnistDbFile(name, mode) {

    /**
     * Reads the integer at the current position.
     *
     * @return integer representing the label
     * @throws IOException
     */
    @Throws(IOException::class)
    fun readLabel(): Int {
        return readUnsignedByte()
    }

    /** Read the specified number of labels from the current position */
    @Throws(IOException::class)
    fun readLabels(num: Int): IntArray {
        val out = IntArray(num)
        for (i in 0 until num) out[i] = readLabel()
        return out
    }

    override val magicNumber: Int
        get() = 2049
}