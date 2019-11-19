import com.google.gson.Gson
import java.io.File
import java.io.Writer

interface Webcontent {
    fun saveContent(members: MutableCollection<ChoirMember>) {
        val jsonString: String = Gson().toJson(members)
        File("outputFile.txt").writeText(jsonString)
    }

    fun loadContent() : MutableCollection<ChoirMember>{
        val jsonString: String = File("outputFile.txt").readText(Charsets.UTF_8)
        val mutableList : MutableCollection<ChoirMember> = mutableListOf()
        val member : ChoirMember =  Gson().fromJson(jsonString, ChoirMember::class.java)
        mutableList.add(member)
        return mutableList
    }
}
