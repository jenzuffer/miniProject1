import com.google.gson.Gson
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.net.ServerSocket
import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.full.memberFunctions

enum class Method (){GET, PUT, POST, DELETE, NONHTTP}

fun handle(request: Request, response: Reponse, content: Any){
    val str: String = request.resource
    val method : Method = request.method
    println("str: $str method: $method")

    //dont use getcontent function at all, use reflection here instead
    //val callFunction: Any? = callFunction(content, Method.GET, "/member/1")
    val callFunction: Any? = callFunction(content, method, "$str")
    if (callFunction != null){
        println("callFunction: $callFunction")
        val parts : List<String> = callFunction.toString().split("(")
        val functioname : String = parts[0]
        //response.append(functioname)
        if (functioname.contains("ChoirMember")){
            val choirmembers : MutableList<ChoirMember> = mutableListOf()
            for (part: String in parts) {
                if (part == parts[0]){
                    continue
                }
                val s = part.split("id=")[1]
                val toInts : Int = s.split(",")[0].toInt()
                val s1 = part.split("name=")[1].split(")")[0]
                val member : ChoirMember = ChoirMember(toInts, s1)
                choirmembers.add(member)
                //println("member: $member")
            }
            val jsonString: String = Gson().toJson(choirmembers)
            //println("jsonString: $jsonString"
            response.append("$jsonString")
        }else if (functioname.contains("DummyMember")){
            val choirmembers : MutableList<DummyMember> = mutableListOf()
            for (part: String in parts) {
                if (part == parts[0]) {
                    continue
                }
                val s = part.split("id=")[1]
                val toInts : Int = s.split(",")[0].toInt()
                val s1 = part.split("name=")[1].split(",")[0]
                val s2 : List<Int> = part.split("listOfInts=")[1].split("],")[0].map { it.toInt() }
                val s3 : Float = part.split("randomFloat=")[1].split(")")[0].toFloat()
                val member : DummyMember = DummyMember(toInts, s1, s2 as MutableList<Int>, s3)
                choirmembers.add(member)
            }
            val jsonString: String = Gson().toJson(choirmembers)
            //println("jsonString: $jsonString"
            response.append("$jsonString")
        }
        response.send()
    }
}

class Webserver (val content: Any, val port : Int = 8080){
    var running = true
    fun start(){
        val serverSocket = ServerSocket(port)
        while (running){
            val socket = serverSocket.accept()
            //co-routines instead of threads
            runBlocking {
                launch { handle(Request(socket.getInputStream()), Reponse(socket.getOutputStream()), content) }
            }
            socket.close()
        }
    }
    fun stop(){

    }
}

fun listFunctions(content: Any) {
    val contentType = content::class
    println("content: ${contentType.simpleName}")
    contentType.memberFunctions.forEach {
        println(it)
    }
}

fun callFunction(content: Any, method: Method, resource: String): Any? {
    val parts = resource.split("/").filter { !it.isEmpty() }
    println("parts $parts")
    if (parts.isEmpty()) return null
    val methodName = method.toString().toLowerCase() + (parts[0].capitalize())
    println("methodName $methodName")
    val type = content::class
    val function = type.declaredFunctions
        .filter { it.name == methodName }
        .filter {
            //println("it param size: ${it.parameters.size} parts size: ${parts.size}")
            //println("it.parameters: " + it.parameters)
            //println("it.parameters.size: ${it.parameters.size}, parts.size: ${parts.size}")
            it.parameters.size == parts.size
        }
        .firstOrNull()
    if (function == null) return null
    if (function.parameters.size > 1) {
        val p = function.parameters[1]
        //  println("p.type.classifier ${p.type.classifier}")
        println("parts[1]: ${parts[1]} p: ${p.type.classifier}")
        if (methodName.contains("get")) {
            when (p.type.classifier) {
                Int::class -> {
                    val v1 = parts[1].toInt()
                    return function.call(content, v1)
                }
                else -> {
                    val p1 = Gson().fromJson(parts[1], p.type.javaClass)
                    return function.call(content, p1)
                }
            /*
            ChoirMember::class -> {
                val s = parts[1].split("id=")[1]
                val toInts : Int = s.split(",")[0].toInt()
                val s1 = parts[1].split("name=")[1].split(")")[0]
                val v1 : ChoirMember = ChoirMember(toInts, s1)
                return function.call(content, v1)
            } */
                //else -> return null
            }
        } else if (methodName.contains("put")) {
            val p1 = parts[1].toInt()
            val p2 = parts[2]
            return function.call(content, p1, p2)
        }
        else {
            return null
        }
    } else return function.call(content)
}

fun main() {
    val content = ChoirContent()
    val content2 = DummyContent()
    val server = Webserver(content, 8080)
    //val server = Webserver(content2, 8080)
    println(callFunction(content, Method.PUT, "/member/4/randomname"))
    server.start()
}