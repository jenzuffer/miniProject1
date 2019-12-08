data class ChoirMember(val id : Int, val name: String){
    fun info() = println("id: $id  name: $name")
}

class ChoirContent() : Webcontent {
    val memberList: MutableMap<Int, ChoirMember> = mutableMapOf<Int, ChoirMember>(
        1 to ChoirMember(1, "name"),
        2 to ChoirMember(2, "name2"),
        3 to ChoirMember(3, "name3")
    )
    /*
    fun putMember(member: ChoirMember): ChoirMember {
        memberList[memberList.size + 1] = member
        saveContent(memberList.values)
        return member
    }
    */

    fun deleteMember(id: Int){
        var iterator : Int = 0
        for (member : ChoirMember in memberList.values){
            if (member.id == id){
                memberList.remove(iterator)
                break
            }
            iterator++
        }
        saveContent(memberList.values)
    }

    fun postMember(name: String){
        val member : ChoirMember = ChoirMember(memberList.size + 1, name)
        memberList[memberList.size + 1] = member
        saveContent(memberList.values)
    }

    fun putMember(id : Int, name : String) {
        val member : ChoirMember = ChoirMember(id, name)
        memberList.remove(id)
        memberList.put(id, member)
        saveContent(memberList.values)
    }

    fun getMember(): List<ChoirMember> {
        return memberList.values.toList()
    }

    fun getMember(index: Int): ChoirMember? {
        return  memberList[index]
    }



}