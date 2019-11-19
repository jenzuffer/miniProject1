import com.google.gson.Gson
import java.io.File
import java.io.Writer

interface Webcontent {
    fun saveContent(members: MutableCollection<ChoirMember>) {
        val jsonString: String = Gson().toJson(members)
        File("outputFile.txt").writeText(jsonString)
    }
}
