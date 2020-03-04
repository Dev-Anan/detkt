package jp.vertice.devkt

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.MenuItemCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import com.google.android.material.navigation.NavigationView
import jp.vertice.devkt.databinding.ActivityMainBinding
import jp.vertice.devkt.databinding.NavHeaderMainBinding
import jp.vertice.devkt.manager.http.Helper
import jp.vertice.devkt.viewmodel.ContactsViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var menuItem: MenuItem? = null
    private var countNotification: Int? = 0
    var badgeNotification: TextView? = null
    private val navController by lazy { findNavController(R.id.nav_host_fragment) } //1
    private val appBarConfiguration by lazy {
        AppBarConfiguration(
            setOf(
                R.id.mainFragment
            ), drawerLayout
        )
    } //2

    private var contactsViewModel: ContactsViewModel? = null
    private lateinit var headerBinding: NavHeaderMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupDataBinding()
        setSupportActionBar(toolbar)
        setupViews()
        setupNavigation()
        setupViewModel()
    }

    private fun setupDataBinding() {
        val activityMainBinding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        headerBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.nav_header_main,
            activityMainBinding.navView,
            false
        )
        activityMainBinding.navView.addHeaderView(headerBinding.root)
    }

    private fun setupViewModel() {
        try {
            val viewModelProvider = ViewModelProvider(
                navController.getViewModelStoreOwner(R.id.main_nav),
                ViewModelProvider.AndroidViewModelFactory(application)
            )
            contactsViewModel = viewModelProvider.get(ContactsViewModel::class.java)
//            headerBinding.viewModel = contactsViewModel
//            lettersViewModel?.loadProfile()
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }

    }


    private fun setupViews() {
        navView.setNavigationItemSelectedListener(this)

        fab.setOnClickListener {
            navController.navigate(R.id.checkFragment)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) ||  super.onSupportNavigateUp()
    }

    private fun setupNavigation(){
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        toolbar.setLogo(R.drawable.logo)
        NavigationUI.setupWithNavController(toolbar,navController,appBarConfiguration)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id in arrayOf(
                    R.id.contactFragment
                )
            ) {
                fab.show()
            } else {
                fab.hide()
            }
            if (destination.id == R.id.checkFragment) {
//                toolbar.visibility = View.GONE
            } else {
                toolbar.visibility = View.VISIBLE
            }
        }
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)

        menuItem = menu.findItem(R.id.action_notification)
        MenuItemCompat.setActionView(menuItem!!, R.layout.notification_badge)
        val relativeLayout = MenuItemCompat.getActionView(menuItem!!) as RelativeLayout

        badgeNotification = relativeLayout.findViewById(R.id.badgeNotification)
        val btnNotification = relativeLayout.findViewById<ImageView>(R.id.btnNotification)

        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        menu.findItem(R.id.action_add).isVisible = false
        menu.findItem(R.id.action_edit).isVisible = false
        menu.findItem(R.id.action_del).isVisible = false
        menu.findItem(R.id.action_save).isVisible = false
        menu.findItem(R.id.action_back).isVisible = false
        menu.findItem(R.id.action_car).isVisible = false
        menu.findItem(R.id.action_personal).isVisible = false
        menu.findItem(R.id.action_settings).isVisible = false
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> if (Helper.isNetworkConnected(applicationContext, this, false)) {
                navController.popBackStack(R.id.mainFragment, false)
            }
            R.id.nav_check -> if (Helper.isNetworkConnected(applicationContext, this, false)) {
                if (!item.isChecked)
                    navController.navigate(Uri.parse("nav://check"))

            }
            R.id.nav_personal -> {
                if (!item.isChecked)
                    navController.navigate(R.id.personalFragment)
            }
            R.id.nav_shop -> {
                if (Helper.isNetworkConnected(applicationContext, this, false)) {
                    if (!item.isChecked) {
                        val browserIntent =
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(getString(R.string.setting_url_shop))
                            )
                        startActivity(browserIntent)
                    }
                }
            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }
}
