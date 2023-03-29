import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.example.bookacafe.R
import com.example.bookacafe.view.ViewPagerAdapter
import java.util.*
import kotlin.collections.ArrayList

class FirstFragment:Fragment(R.layout.fragment_first) {

    lateinit var viewPager: ViewPager
    lateinit var viewPagerAdapter: ViewPagerAdapter
    lateinit var imageList: List<Int>
    lateinit var timer: Timer
    var currentPosition = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val myClickableText = view.findViewById<TextView>(R.id.seeDetails)
        val clickableImage: ImageButton = view.findViewById(R.id.imageProfile)
        clickableImage.setOnClickListener{
            val mCategoryFragment = ThirdFragment()
            val mFragmentManager = parentFragmentManager as FragmentManager
            mFragmentManager
                .beginTransaction()
                .replace(
                    R.id.fragment_container,
                    mCategoryFragment,
                    ThirdFragment::class.java.simpleName
                )
                .addToBackStack(null)
                .commit()
        }
        myClickableText.setOnClickListener {

//            val newFragment = SecondFragment()
//            val transaction = parentFragmentManager.beginTransaction()
//            transaction.replace(R.id.fragment_container, newFragment)
//            transaction.addToBackStack(null)
//            transaction.commit()
            val mCategoryFragment = SecondFragment()
            val mFragmentManager = parentFragmentManager as FragmentManager
            mFragmentManager
                .beginTransaction()
                .replace(
                    R.id.fragment_container,
                    mCategoryFragment,
                    SecondFragment::class.java.simpleName
                )
                .addToBackStack(null)
                .commit()
        }

        viewPager = view.findViewById(R.id.idViewPager)

        imageList = ArrayList<Int>()
        imageList = imageList + R.drawable.logo1
        imageList = imageList + R.drawable.logo2
        imageList = imageList + R.drawable.hunter

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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        timer.cancel()
    }

//    override fun onClick(v: View) {
//        if (v.id == R.id.seeDetails) {
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
//
//        }
//    }
}


