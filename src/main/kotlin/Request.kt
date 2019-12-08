import java.io.InputStream

class Request(val input: InputStream){
    val resource: String
    val method: Method
    init {
        val reading = input.bufferedReader().readLine()
        println("reading: $reading")
        var parts = reading.split(" ")
        //println("parts[0]: ${parts[0]}")
        resource = parts[1]
        //af en eller anden grund kan PUT OG DELETE ikke læses men bliver altid sat som OPTIONS
        method = if (parts[0].equals("OPTIONS")){
            if (resource.substringAfterLast("/").toIntOrNull() == null) {
                Method.valueOf("PUT")
            } else{
                Method.valueOf("DELETE")
            }
        } else {
            Method.valueOf(parts[0])
        }
    }
    fun inputReading() {
        println(input.bufferedReader().readLine())
    }

}

