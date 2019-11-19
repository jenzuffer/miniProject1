import kotlin.random.Random

data class DummyMember(val id : Int, val name: String, val listOfInts : MutableList<Int>, val randomFloat : Float){
    fun info() = println("id: $id  name: $name, listOfInts: ${listOfInts.forEach{ println(it)}}, randomFloat: $randomFloat")
}

class DummyContent() : Webcontent{
    val memberList: MutableMap<Int, DummyMember> = mutableMapOf<Int, DummyMember>(
        1 to DummyMember(1, "name", (1..10).map { (0..150).random() } as MutableList<Int>, 0.30F),
        2 to DummyMember(2, "name2",  (1..10).map { (0..150).random() } as MutableList<Int>, 5.38F),
        3 to DummyMember(3, "name3 ",  (1..10).map { (0..150).random() } as MutableList<Int>, 2.90F)
    )
    fun PutDummyMember(member : DummyMember){
        memberList[memberList.size] = member
    }

    fun getDummymember(): List<DummyMember> {
        return memberList.values.toList()
    }
    fun getDummymember(index : Int): DummyMember? {
        return  memberList[index]
    }
}