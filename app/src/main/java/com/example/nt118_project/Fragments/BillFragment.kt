package com.example.nt118_project.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nt118_project.Adapter.BusTicketAdapter
import com.example.nt118_project.Adapter.BusTicketInvoiceAdapter
import com.example.nt118_project.Model.BusTicket
import com.example.nt118_project.Model.BusTicketInvoice
import com.example.nt118_project.R
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks.await
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.reflect.Constructor
import java.util.concurrent.locks.ReentrantLock
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BillFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BillFragment : Fragment(){
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var job: Job = Job()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    class bus_invoice_ {
        var bus_Id_1: String = ""
        var bus_Id_2: String = ""
        var id: String = ""
        var invoice_Id: String = ""
        constructor(){}
        constructor(bus_Id_1:String, bus_Id_2: String, id:String, invoice_Id:String)
        {
            this.bus_Id_1 = bus_Id_1
            this.bus_Id_2 = bus_Id_2
            this.id = id
            this.invoice_Id = invoice_Id
        }
    }
    class invoice_ {
        var id: String = ""
        var tag: String = ""
        var user_Id: String = ""
        var num_Ticket: String = ""
        var total: String = ""
        constructor(){}
        constructor(id:String, tag:String, num_Ticket:String, user_Id:String, total:String)
        {
            this.id = id
            this.tag = tag
            this.num_Ticket = num_Ticket
            this.user_Id = user_Id
            this.total = total
        }
    }
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        return rootView
        return inflater.inflate(R.layout.fragment_bill, container, false)
    }
    private lateinit var RadioGroup: RadioGroup
    //private lateinit var RecyclerViewTicket: RecyclerView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        val databaseReference = Firebase.firestore
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        val user_id = currentUser!!.uid
        RadioGroup = view.findViewById(R.id.radio_group)

        RadioGroup.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.radio_button1 -> {
                    var RecyclerViewTicket: RecyclerView
                    RecyclerViewTicket = view.findViewById(R.id.RecyclerViewTicket)
                    val invoice_model_List = ArrayList<invoice_>()
                    var busTicketInvoiceAdapter: BusTicketInvoiceAdapter
                    var dataList:ArrayList<BusTicketInvoice> = ArrayList<BusTicketInvoice>()
                    //dataList.add(BusTicketInvoice("HN1000202419256","","1 ghế ngồi","200000.0"))
                    Thread(Runnable(){
                        var test = databaseReference.collection("Invoice").whereEqualTo("user_Id", user_id.toString()).whereEqualTo("tag", "Bus")
                        var testSnapshot = await(test.get())
                        for (doc in testSnapshot.documents)
                        {
                            val invoice_model = doc.toObject(invoice_::class.java)
                            invoice_model_List.add(invoice_model!!)
                        }
                        for (inv in invoice_model_List)
                        {
                            databaseReference.collection("Bus_invoice").whereEqualTo("invoice_Id", inv.id).get()
                                .addOnSuccessListener { documents ->
                                    var temp = ArrayList<String>()
                                    for (document in documents)
                                    {
                                        val bus_invoice_model = document.toObject(bus_invoice_::class.java)
                                        temp.add(bus_invoice_model.bus_Id_1)
                                        temp.add(bus_invoice_model.bus_Id_2)
                                        break
                                    }
                                    val busticketinvoice = BusTicketInvoice(temp[0],temp[1],inv.num_Ticket,inv.total)
                                    dataList.add(busticketinvoice)
                                    busTicketInvoiceAdapter = BusTicketInvoiceAdapter(dataList)
                                    RecyclerViewTicket.adapter = busTicketInvoiceAdapter
                                    RecyclerViewTicket.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
                                }
                        }
                    }).start()
//                    busTicketInvoiceAdapter = BusTicketInvoiceAdapter(dataList)
//                    RecyclerViewTicket.adapter = busTicketInvoiceAdapter
//                    RecyclerViewTicket.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
                    Log.d("DataList", "dataList.size.toString()4")
                }
                R.id.radio_button2 -> {
                    var RecyclerViewTicket: RecyclerView
                    RecyclerViewTicket = view.findViewById(R.id.RecyclerViewTicket)
                    val invoice_model_List = ArrayList<invoice_>()
                    var busTicketInvoiceAdapter: BusTicketInvoiceAdapter
                    var dataList:ArrayList<BusTicketInvoice> = ArrayList<BusTicketInvoice>()
                    dataList.add(BusTicketInvoice("HN080020241313419","","1 ghế ngồi","200000.0"))
                    busTicketInvoiceAdapter = BusTicketInvoiceAdapter(dataList)
                    RecyclerViewTicket.adapter = busTicketInvoiceAdapter
                    RecyclerViewTicket.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
//                    Thread(Runnable(){
//                        var test = databaseReference.collection("Invoice").whereEqualTo("user_Id", user_id.toString()).whereEqualTo("tag", "Bus")
//                        var testSnapshot = await(test.get())
//                        for (doc in testSnapshot.documents)
//                        {
//                            val invoice_model = doc.toObject(invoice_::class.java)
//                            invoice_model_List.add(invoice_model!!)
//                        }
//                        for (inv in invoice_model_List)
//                        {
//                            databaseReference.collection("Bus_invoice").whereEqualTo("invoice_Id", inv.id).get()
//                                .addOnSuccessListener { documents ->
//                                    var temp = ArrayList<String>()
//                                    for (document in documents)
//                                    {
//                                        val bus_invoice_model = document.toObject(bus_invoice_::class.java)
//                                        temp.add(bus_invoice_model.bus_Id_1)
//                                        temp.add(bus_invoice_model.bus_Id_2)
//                                        break
//                                    }
//                                    val busticketinvoice = BusTicketInvoice(temp[0],temp[1],inv.num_Ticket,inv.total)
//                                    dataList.add(busticketinvoice)
//                                    busTicketInvoiceAdapter = BusTicketInvoiceAdapter(dataList)
//                                    RecyclerViewTicket.adapter = busTicketInvoiceAdapter
//                                    RecyclerViewTicket.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
//                                }
//                        }
//                    }).start()
//                    busTicketInvoiceAdapter = BusTicketInvoiceAdapter(dataList)
//                    RecyclerViewTicket.adapter = busTicketInvoiceAdapter
//                    RecyclerViewTicket.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
                    Log.d("DataList", "dataList.size.toString()4")
                }
                R.id.radio_button3 -> {
                }
                R.id.radio_button4 -> {
                }
            }
        }
    }

//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment BillFragment.
//         */
//        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            BillFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }
}




//        var testSnapshot = await(test.get())
//            for (doc in testSnapshot.documents)
//            {
//                val invoice_model = doc.toObject(invoice_::class.java)
//                invoice_model_List.add(invoice_model!!)
//            }
//            Log.d("DataList", dataList.size.toString())

//        databaseReference.collection("Invoice").whereEqualTo("user_Id", user_id.toString()).whereEqualTo("tag", "Bus")
//            .get()
//            .addOnSuccessListener {documents ->
//                Log.d("DataList", "dataList.size.toString()1")
//                for (document in documents)
//                {
//                    val invoice_model = document.toObject(invoice_::class.java)
//                    invoice_model_List.add(invoice_model)
//                }
//            }