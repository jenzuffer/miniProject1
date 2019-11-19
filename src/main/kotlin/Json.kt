import kotlin.reflect.full.callSuspend
import kotlin.reflect.full.memberProperties

fun jsonCall(){
    val jsonExample = """
        {
         "age": 27, 
         "firstName":  "kurt" , 
         "id": 7, 
         "lastName":  "hansen" 
        }
    """.trimIndent()
}

fun toJson(what: Any) =
    what::class.memberProperties
        .map { """  "${it.name}": ${jsonValueOf(it.call(what))}""" }
        .joinToString(",\n", "{\n", "\n}\n")

fun toJson2(what: Any) : String {
    var json = ""
    val type = what::class
    for (property in type.memberProperties) {
        val name = property.name
        val value = property.call(what)
        if (json.isEmpty()) json = "{\n  \"$name\": ${jsonValueOf(value)}"
        else json += ",\n  \"$name\": ${jsonValueOf(value)}"
    }
    return "$json\n}\n"
}

fun jsonValueOf(value: Any?): String =
    when (value) {
        null -> "null"
        is Int -> value.toString()
        is Double -> value.toString()
        is String -> """"$value""""
        else -> toJson1_5(value)
    }

fun toJson1_5(what: Any) =
    what::class.memberProperties
        .map { property -> """  "${property.name}": ${jsonValueOf(property.call(what))}""" }
        .joinToString(prefix = "{\n", separator = ",\n", postfix = "\n}\n")