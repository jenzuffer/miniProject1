import kotlin.reflect.KFunction
import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.full.memberFunctions

data class ChoirMember(val id : Int, val name: String){
    fun info() = println("id: $id  name: $name")
}

class ChoirContent() : Webcontent {
    val memberList: MutableMap<Int, ChoirMember> = mutableMapOf<Int, ChoirMember>(
        1 to ChoirMember(1, "name"),
        2 to ChoirMember(2, "name2"),
        3 to ChoirMember(3, "name3 ")
    )

    fun putMember(member: ChoirMember) {
        memberList[memberList.size] = member
    }

    fun getMember(): List<ChoirMember> {
        return memberList.values.toList()
    }

    fun getMember(index: Int): ChoirMember? {
        return  memberList[index]
    }

    override fun saveContent() {

    }


}