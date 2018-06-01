package comcesar1287.github.bolocopadomundo2018.firestore.firestore.services

import com.google.firebase.firestore.DocumentReference
import comcesar1287.github.bolocopadomundo2018.models.Bet
import comcesar1287.github.bolocopadomundo2018.models.Group
import comcesar1287.github.bolocopadomundo2018.models.Playoff
import comcesar1287.github.bolocopadomundo2018.models.User

class GeneralService {

    fun findEmployeeByUID(user: User, callbackService: CallbackService<User>){
        val userService = UserService()
        userService.findByUid(user.id, object : CallbackService<User>{
            override fun onComplete(item: User?) {
                callbackService.onComplete(item)
            }
        })
    }

    fun addUser(uid: String, name:String, email:String): DocumentReference{
        //Services
        val userService = UserService()
        val betService = BetService()
        val groupService = GroupService()
        val playoffService = PlayoffService()

        /* BEGIN */
        val playoffsReferences = mutableListOf<DocumentReference>()

        //Oitavas de final
        val roundOf16 = Playoff()
        roundOf16.id = playoffService.id
        roundOf16.phase = 1
        playoffsReferences.add(playoffService.collectionReference.document(roundOf16.id!!))
        playoffService.add(roundOf16)

        //Quartas de final
        val roundOf8 = Playoff()
        roundOf8.id = playoffService.id
        roundOf8.phase = 2
        playoffsReferences.add(playoffService.collectionReference.document(roundOf8.id!!))
        playoffService.add(roundOf8)

        //Semi final
        val roundOf4 = Playoff()
        roundOf4.id = playoffService.id
        roundOf4.phase = 3
        playoffsReferences.add(playoffService.collectionReference.document(roundOf4.id!!))
        playoffService.add(roundOf4)

        //Final
        val roundOf2 = Playoff()
        roundOf2.id = playoffService.id
        roundOf2.phase = 4
        playoffsReferences.add(playoffService.collectionReference.document(roundOf2.id!!))
        playoffService.add(roundOf2)
        /* END */

        /* BEGIN */
        val groupsReferences = mutableListOf<DocumentReference>()

        //Group A
        val groupA = Group()
        groupA.id = groupService.id
        groupA.number = 1
        groupsReferences.add(groupService.collectionReference.document(groupA.id!!))
        groupService.add(groupA)

        //Group B
        val groupB = Group()
        groupB.id = groupService.id
        groupB.number = 2
        groupsReferences.add(groupService.collectionReference.document(groupB.id!!))
        groupService.add(groupB)

        //Group C
        val groupC = Group()
        groupC.id = groupService.id
        groupC.number = 3
        groupsReferences.add(groupService.collectionReference.document(groupC.id!!))
        groupService.add(groupC)

        //Group D
        val groupD = Group()
        groupD.id = groupService.id
        groupD.number = 4
        groupsReferences.add(groupService.collectionReference.document(groupD.id!!))
        groupService.add(groupD)

        //Group E
        val groupE = Group()
        groupE.id = groupService.id
        groupE.number = 5
        groupsReferences.add(groupService.collectionReference.document(groupE.id!!))
        groupService.add(groupE)

        //Group F
        val groupF = Group()
        groupF.id = groupService.id
        groupF.number = 6
        groupsReferences.add(groupService.collectionReference.document(groupF.id!!))
        groupService.add(groupF)

        //Group G
        val groupG = Group()
        groupG.id = groupService.id
        groupG.number = 7
        groupsReferences.add(groupService.collectionReference.document(groupG.id!!))
        groupService.add(groupG)

        //Group H
        val groupH = Group()
        groupH.id = groupService.id
        groupH.number = 8
        groupsReferences.add(groupService.collectionReference.document(groupH.id!!))
        groupService.add(groupH)
        /* END */


        //bet
        val bet = Bet()
        bet.id = betService.id
        bet.user = userService.collectionReference.document(uid)
        bet.groups = groupsReferences
        bet.playoffs = playoffsReferences

        // User
        val user = User()
        user.id = uid
        user.name = name
        user.email = email
        val betDocumentReference = betService.collectionReference.document(bet.id!!)
        user.bets = betDocumentReference

        userService.add(user)
        betService.add(bet)

        return betDocumentReference
    }
}