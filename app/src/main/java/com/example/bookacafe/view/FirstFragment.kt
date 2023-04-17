import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.bookacafe.R
import com.example.bookacafe.controller.ActiveUser
import com.example.bookacafe.controller.CartControllers
import com.example.bookacafe.controller.HomePageControllers
import com.example.bookacafe.view.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class FirstFragment : Fragment(R.layout.fragment_first) {

    lateinit var viewPager: ViewPager
    lateinit var viewPagerAdapter: ViewPagerAdapter
    lateinit var imageList: List<Int>
    lateinit var timer: Timer
    lateinit var timeLeftTextView: TextView
    lateinit var nameTextView: TextView
    var currentPosition = 0
    val control: CartControllers = CartControllers()
    val controlHome: HomePageControllers = HomePageControllers()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity).supportActionBar?.hide()
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val myClickableText = view.findViewById<TextView>(R.id.seeDetails)
        if (ActiveUser.getActiveTransaction()== null) {
            Log.d("TAG", "gak ad")
            myClickableText.visibility = View.INVISIBLE
        }
        val clickableImage: ImageButton = view.findViewById(R.id.imageProfile)

        setCheckedIn(controlHome.getTime(), view)
        setTotal(control.getTotalForCart(), view)
        setTimeDuration(view)

        clickableImage.setOnClickListener {
            val intent = Intent(context, MenuProfile::class.java)
            startActivity(intent)
        }
        myClickableText.setOnClickListener {
            val intent = Intent(context, BillActivity::class.java)
            intent.putExtra("transaction_id", ActiveUser.getActiveTransaction()?.transactionId)
            startActivity(intent)
        }

        viewPager = view.findViewById(R.id.idViewPager)

        imageList = ArrayList<Int>()
        imageList = imageList + R.drawable.burgir_banner
        imageList = imageList + R.drawable.strobucat_banner
        imageList = imageList + R.drawable.sashimsimi_banner

        viewPagerAdapter = ViewPagerAdapter(requireContext(), imageList)

        viewPager.adapter = viewPagerAdapter

        timer = Timer()

        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                if (currentPosition == imageList.size) {
                    currentPosition = 0
                }
                viewPager.post {
                    viewPager.setCurrentItem(currentPosition++, true)
                }
            }
        }, 2000, 5000)

        nameTextView = view.findViewById(R.id.homeUserName)
        var acct: GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(requireActivity())

        // Set data ; ambil dari data yang login sekarang
        if (acct != null) {
            var activeName: String = acct.displayName.toString()
            var displayName = "Welcome \n ${activeName}"
            nameTextView.setText(displayName)
        } else {
            setName(controlHome.getName(), view)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        timer.cancel()
    }

    fun setTotal(total: Int, view: View) {
        var cartTotal: TextView = view.findViewById(R.id.cartShortCut)
        if (total != 0) {
            cartTotal.text = "Rp$total,-"
        } else {
            cartTotal.text = "No order yet"
        }

    }

    fun setName(name: String, view: View) {
        var homePageUserName: TextView = view.findViewById(R.id.homeUserName)
        homePageUserName.text = "Welcome\n ${name}"
    }

    fun setCheckedIn(time: String, view: View) {
        var timeCheckedIn: TextView = view.findViewById(R.id.textCheckedIn)

        if (time != "") {
            timeCheckedIn.text = "Checked In ${time}"
        } else {
            timeCheckedIn.text = "No tables yet"
        }
    }

    fun setTimeDuration(view: View) {
        timeLeftTextView = view.findViewById(R.id.textTimeLeft)
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val timeCheckIn = controlHome.getTime()

        if(timeCheckIn == ""){
            timeLeftTextView.text = "Been here for N/A"

        } else {
            // get localtime
            val calendar = Calendar.getInstance()
            val localTime = calendar.time

            val localTimeAsString = dateFormat.format(localTime)
            val date = dateFormat.parse(localTimeAsString)
            val dateCheckIn = dateFormat.parse(timeCheckIn)

            // mengubah selisih waktu menjadi jam dan menit
            val diffTime = date.time - dateCheckIn.time
            val hours = TimeUnit.MILLISECONDS.toHours(diffTime)
            val minutes = TimeUnit.MILLISECONDS.toMinutes(diffTime) % 60


            // format hasil menjadi "hh:mm"
            val result = String.format("%02d:%02d", hours, minutes)

            timeLeftTextView.text = "Been here for " + result
        }





    }

}
