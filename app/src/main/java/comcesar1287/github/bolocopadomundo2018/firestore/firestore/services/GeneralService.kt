package comcesar1287.github.bolocopadomundo2018.firestore.firestore.services

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import comcesar1287.github.bolocopadomundo2018.firestore.firestore.services.BetService
import comcesar1287.github.bolocopadomundo2018.firestore.firestore.services.UserService
import comcesar1287.github.bolocopadomundo2018.models.Bet
import comcesar1287.github.bolocopadomundo2018.models.User

import kotlin.collections.HashMap

class GeneralService(var context: Context) {

//    private val companyService = CompanyService()
    private val hashMap = HashMap<String, Any>()

//    fun findEmployeeByUser(user: User, callbackService: CallbackService<EmployeeDTO>){
//        val userService = UserService()
//        userService.findByEmail(user.email!!, object :
//            CallbackService<List<User>>{ override fun onComplete(item: List<User>?) {
//                item?.let {
//                    if (!it.isEmpty()) {
//                        val user = it[0]
//                        val employeeDTO = EmployeeDTO()
//                        employeeDTO.user = user
//                        employeeDTO.userReference = user.id?.let { reference -> userService.collectionReference.document(reference) }
//
//                        val employeeReference = user.jobs?.get(0)
//                        employeeReference?.let {
//                            EmployeeService().findByReference(it, object : CallbackService<Employee> {
//                                override fun onComplete(item: Employee?) {
//                                    if (employeeDTO.employee == null) {
//                                        item?.let {
//                                            employeeDTO.employee = it
//                                            callbackService.onComplete(employeeDTO)
//                                        }
//                                    }
//                                }
//                            })
//                        }
//                    }
//                }
//            }
//        })
//    }
//
//    fun findEmployeeByUID(user: User, callbackService: CallbackService<User>){
//        val userService = UserService()
//        userService.findByEmail(user.email!!, object : CallbackService<List<User>>{ override fun onComplete(item: List<User>?) {
//            item?.let {
//                if (!it.isEmpty()) {
//                    val result = it[0]
//                    callbackService.onComplete(result)
//                }
//            }
//        }
//        })
//    }

    fun addUser(uid: String, name:String, email:String){
        //Services
        val userService = UserService()
        val betService = BetService()

//        //Companies and Shift
//        val company = Company()
//        company.id = companyService.id
//        val shift = Shift()
//        shift.id = shiftService.id
//        shift.shiftType = ShiftType.WEEKLY
//        val dayOffs = ArrayList<WorkDay>()
//        val workDays = ArrayList<WorkDay>()
//        val periods = ArrayList<Period>()
//        periods.add(Period("08:00", "12:00"))
//        periods.add(Period("13:00", "17:00"))
//
//        for (i in 1..7){
//
//            val workDay = WorkDay()
//            workDay.endDay = "23:59"
//            workDay.mainBreak = Period("12:00", "13:00")
//            workDay.weekDay = i
//            workDay.periods = periods
//            if ((i == 1).or(i == 7))
//                dayOffs.add(workDay)
//            else
//                workDays.add(workDay)
//        }
//
//        shift.dayOffs =dayOffs
//        shift.workDays = workDays
//
//        val companyReference = companyService
//                .collectionReference
//                .document(company.id!!)
//
//        // JobTitle
//        val jobTitle = JobTitle()
//        jobTitle.id = jobTitleService.id
//        jobTitle.name = ""
//        val jobTitleReference = jobTitleService
//                .db.document(companyReference.path + File.separator + jobTitleService.collection + File.separator +jobTitle.id)
//        //
//
//        // Employee
//        val employee = Employee()
//        employee.id = employeeService.id
//        employee.name = name
//        employee.role = Role.ANONYMOUS_USER
//        employee.company = companyReference
//        val allocation = Allocation()
//        val shiftDocumentReference = companyService
//                .collectionReference
//                .document(company.id!!)
//                .collection(shiftService.collection)
//                .document(shift.id!!)
//
//        allocation.jobTitle = jobTitleReference
//        allocation.shift = shiftDocumentReference
//        allocation.admissionDate = Calendar.getInstance().time
//        employee.allocations = listOf(allocation)
//


        //bet
        val bet = Bet()
        bet.id = betService.id
        bet.user = userService.collectionReference.document(uid)

        // User
        val user = User()
        user.id = uid
        user.name = name
        user.email = email
        val betDocumentReference = betService.collectionReference.document(bet.id!!)
        user.bets = betDocumentReference

        userService.add(user)
        //betService.add(bet)
//        companyService.add(company)
//        shiftService.add(company.id, shift)
//        employeeService.add(employee)
//        jobTitleService.add(company.id, jobTitle)
    }

//    fun addUserNoCompany(uid: String?, name:String, email:String){
//        //Services
//        val employeeService = EmployeeService()
//        val companyService = CompanyService()
//        val userService = UserService()
//        val shiftService = ShiftService(context)
//        val jobTitleService = JobTitleService(context)
//
//        //Companies and Shift
//        val company = Company()
//        company.id = companyService.id
//        val shift = Shift()
//        shift.id = shiftService.id
//        shift.shiftType = ShiftType.WEEKLY
//        val dayOffs = ArrayList<WorkDay>()
//        val workDays = ArrayList<WorkDay>()
//        val periods = ArrayList<Period>()
//        periods.add(Period("08:00", "12:00"))
//        periods.add(Period("13:00", "17:00"))
//
//        for (i in 1..7){
//
//            val workDay = WorkDay()
//            workDay.endDay = "23:59"
//            workDay.mainBreak = Period("12:00", "13:00")
//            workDay.weekDay = i
//            workDay.periods = periods
//            if ((i == 1).or(i == 7))
//                dayOffs.add(workDay)
//            else
//                workDays.add(workDay)
//        }
//
//        shift.dayOffs =dayOffs
//        shift.workDays = workDays
//
//        val companyReference = companyService
//                .collectionReference
//                .document(company.id!!)
//
//        // JobTitle
//        val jobTitle = JobTitle()
//        jobTitle.id = jobTitleService.id
//        jobTitle.name = ""
//        val jobTitleReference = jobTitleService
//                .db.document(companyReference.path + File.separator + jobTitleService.collection + File.separator +jobTitle.id)
//        //
//
//        // Employee
//        val employee = Employee()
//        employee.id = employeeService.id
//        employee.name = name
//        employee.role = Role.NO_COMPANY
//        employee.company = companyReference
//        val allocation = Allocation()
//        val shiftDocumentReference = companyService
//                .collectionReference
//                .document(company.id!!)
//                .collection(shiftService.collection)
//                .document(shift.id!!)
//
//        allocation.jobTitle = jobTitleReference
//        allocation.shift = shiftDocumentReference
//        allocation.admissionDate = Calendar.getInstance().time
//        employee.allocations = listOf(allocation)
//
//        // Users
//        val user = User()
//        user.id = uid
//        user.email = email
//        val jobDocumentReference = employeeService.collectionReference.document(employee.id!!)
//        user.jobs = listOf(jobDocumentReference)
//
//        userService.add(user)
//        companyService.add(company)
//        shiftService.add(company.id, shift)
//        employeeService.add(employee)
//        jobTitleService.add(company.id, jobTitle)
//    }
}