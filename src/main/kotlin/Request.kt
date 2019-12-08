import java.io.InputStream

class Request(val input: InputStream){
    val resource: String
    val method: Method
    init {
        val reading = input.bufferedReader().readLine()
        //println("reading: $reading")
        var parts = reading.split(" ")
        resource = parts[1]
        method = Method.valueOf(parts[0])
    }
    fun inputReading() {
        println(input.bufferedReader().readLine())
    }

}

