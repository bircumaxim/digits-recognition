package mnist


import java.io.FileNotFoundException
import java.io.IOException
import java.io.RandomAccessFile

/**
 * MNIST database file containing entries that can represent image or label
 * data. Extends the standard random access file with methods for navigating
 * over the entries. The file format is basically idx with specific header
 * information. This includes a magic number for determining the type of stored
 * entries, count of entries.
 */
abstract class MnistDbFile
/**
 * Creates new instance and reads the header information.
 *
 * @param name
 * the system-dependent filename
 * @param mode
 * the access mode
 * @throws IOException
 * @throws FileNotFoundException
 * @see RandomAccessFile
 */
@Throws(IOException::class)
constructor(name: String, mode: String) : RandomAccessFile(name, mode) {
    val count: Int


    init {
        if (magicNumber != readInt()) {
            throw RuntimeException("This MNIST DB file $name should start with the number $magicNumber.")
        }
        count = readInt()
    }

    /**
     * MNIST DB files start with unique integer number.
     *
     * @return integer number that should be found in the beginning of the file.
     */
    protected abstract val magicNumber: Int

    /**
     * The current entry index.
     *
     * @return long
     * @throws IOException
     */
    /**
     * Set the required current entry index.
     *
     * @param curr
     * the entry index
     */
    var currentIndex: Long
        @Throws(IOException::class)
        get() = (filePointer - headerSize) / entryLength + 1
        set(curr) = try {
            if (curr < 0 || curr > count) {
                throw RuntimeException(curr.toString() + " is not in the range 0 to " + count)
            }
            seek(headerSize + (curr - 1) * entryLength)
        } catch (e: IOException) {
            throw RuntimeException(e)
        }

    // two integers
    open val headerSize: Int
        get() = 8

    /**
     * Number of bytes for each entry.
     * Defaults to 1.
     *
     * @return int
     */
    open val entryLength: Int
        get() = 1

    /**
     * Move to the next entry.
     *
     * @throws IOException
     */
    @Throws(IOException::class)
    operator fun next() {
        if (currentIndex < count) {
            skipBytes(entryLength)
        }
    }

    /**
     * Move to the previous entry.
     *
     * @throws IOException
     */
    @Throws(IOException::class)
    fun prev() {
        if (currentIndex > 0) {
            seek(filePointer - entryLength)
        }
    }
}