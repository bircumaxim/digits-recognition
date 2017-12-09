package mnist


import mnist.MnistDbFile
import java.io.FileNotFoundException
import java.io.IOException

/**
 *
 * MNIST database image file. Contains additional header information for the
 * number of rows and columns per each entry.
 *
 */
class MnistImageFile
/**
 * Creates new MNIST database image file ready for reading.
 *
 * @param name
 * the system-dependent filename
 * @param mode
 * the access mode
 * @throws IOException
 * @throws FileNotFoundException
 */
@Throws(FileNotFoundException::class, IOException::class)
constructor(name: String, mode: String) : MnistDbFile(name, mode) {
    /**
     * Number of rows per image.
     *
     * @return int
     */
    val rows: Int = readInt()
    /**
     * Number of columns per image.
     *
     * @return int
     */
    val cols: Int = readInt()

    init {

        // read header information
    }

    /**
     * Reads the image at the current position.
     *
     * @return matrix representing the image
     * @throws IOException
     */
    @Throws(IOException::class)
    fun readImage(): Array<IntArray> {
        val dat = Array(rows) { IntArray(cols) }
        for (i in 0..cols - 1) {
            for (j in 0..rows - 1) {
                dat[i][j] = readUnsignedByte()
            }
        }
        return dat
    }

    /**
     * Move the cursor to the next image.
     *
     * @throws IOException
     */
    @Throws(IOException::class)
    fun nextImage() {
        super.next()
    }

    /**
     * Move the cursor to the previous image.
     *
     * @throws IOException
     */
    @Throws(IOException::class)
    fun prevImage() {
        super.prev()
    }

    override val magicNumber: Int
        get() = 2051

    override val entryLength: Int
        get() = cols * rows

    override // to more integers - rows and columns
    val headerSize: Int
        get() = super.headerSize + 8
}