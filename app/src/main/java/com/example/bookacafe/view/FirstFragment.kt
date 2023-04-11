import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.example.bookacafe.R
import com.example.bookacafe.view.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import java.util.*
import kotlin.collections.ArrayList

class FirstFragment:Fragment(R.layout.fragment_first) {

    lateinit var viewPager: ViewPager
    lateinit var viewPagerAdapter: ViewPagerAdapter
    lateinit var imageList: List<Int>
    lateinit var timer: Timer
    lateinit var nameTextView: TextView
    var currentPosition = 0

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
        val clickableImage: ImageButton = view.findViewById(R.id.imageProfile)
        clickableImage.setOnClickListener{
            val intent = Intent(context, MenuProfile::class.java)
            startActivity(intent)
        }
        myClickableText.setOnClickListener {
            val intent = Intent(context, BillActivity::class.java)
            intent.putExtra("transaction_id", "TR20230211-001")
            startActivity(intent)
        }
//        myClickableText.setOnClickListener {
//
//            val mCategoryFragment = SecondFragment()
//            val mFragmentManager = parentFragmentManager as FragmentManager
//            mFragmentManager
//                .beginTransaction()
//                .replace(
//                    R.id.fragment_container,
//                    mCategoryFragment,
//                    SecondFragment::class.java.simpleName
//                )
//                .addToBackStack(null)
//                .commit()
//        }

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
        if (acct != null){
            var activeName: String = acct.displayName.toString()
            var displayName = "Welcome \n ${activeName}"
            nameTextView.setText(displayName)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        timer.cancel()
    }

}


