import java.io.OutputStream
import java.io.PrintWriter
import java.lang.StringBuilder

class Reponse(private val output: OutputStream) {

    val body = StringBuilder()
    fun append(text: String) {
        body.append(text)
    }
    fun send(){
        val head = """
        HTTP/1.1 200 OK
        Content-Type: text/html; charset=UTF-8
        Access-Control-Allow-Origin:*
        Content-Length: ${body.length}
        Connection: close
        
        """.trimIndent()
        println("body: $body")
        //PrintWriter(output, true).println("$body")
        val writer = output.bufferedWriter()
        writer.append(head)
        writer.newLine()
        writer.append("$body")
        writer.close()
    }
}