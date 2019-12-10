data class ChoirMember(val id : Int, var name: String){
    fun info() = println("id: $id  name: $name")

}

class ChoirContent() : Webcontent {
    val memberList: MutableList<ChoirMember> = mutableListOf<ChoirMember>(
        ChoirMember(1, "name"),
        ChoirMember(2, "name2"),
        ChoirMember(3, "name3")
    )

    fun deleteMember(id: Int){
        var index : Int = 1
        for (member : ChoirMember in memberList){
            if (member.id == id){
                println("member name: ${member.name} id: $id")
                memberList.remove(member)
                break
            }
            index++
        }
        saveContent(memberList)
    }

    fun postMember(name: String){
        var id : Int = 1
        val sortedBy = memberList.sortedBy { it.id }
        for (member : ChoirMember in sortedBy){
            println("member id: ${member.id}")
            if (member.id != id){
                break
            }
            id++
        }
        val member : ChoirMember = ChoirMember(id, name)
        memberList.add(member)
        println("memberList size: ${memberList.size} name $name")
        saveContent(memberList)
    }

    fun putMember(id : Int, name : String) {
        val member1 : ChoirMember = ChoirMember(id, name)
        for (member : ChoirMember in memberList){
            if (member.id == id){
                member.name = "$name"
                break
            }
        }
        saveContent(memberList)
    }

    fun getMember(): List<ChoirMember> {
        return memberList.toList()
    }

    fun getMember(index: Int): ChoirMember? {
        for (member : ChoirMember in memberList){
            if (member.id == index){
                return member
            }
        }
        return null
    }



}