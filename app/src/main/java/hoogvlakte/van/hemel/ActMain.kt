package hoogvlakte.van.hemel

import android.content.Intent
import android.content.res.TypedArray
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hoogvlakte.van.hemel.databinding.ActMainBinding
import hoogvlakte.van.hemel.databinding.CompCardviewReasonBinding
import kotlinx.parcelize.Parcelize

/* -------------------------------------------------------------------------------------------------
 * RecyclerView is pain:
 * 1. Define the model class (alias data class) that shall hold the values.
 *    --> In this case is DataReason below.
 * 2. Add RecyclerView to the activity XML.
 *    --> In this case look inside act_main.XML
 * 3. Create your XML "skin" layout that will visualise each data from numero 1 model class.
 *    --> In this case is comp_cardview_reason.XML
 * 4. Create a RecyclerView.Adapter and ViewHolder to render the item from numero 4 XML layout.
 *    --> ...
 * 5. Bind the adapter (4) to the data source  (1) to populate the RecyclerView (2)
 *    --> ...
 * ---------------------------------------------------------------------------------------------- */

/* -------------------------------------------------------------------------------------------------
 * Object that shall hold values from strings.xml
 * ---------------------------------------------------------------------------------------------- */
@Parcelize
data class DataReason(
    val title: String,
    val description: String,
    val image: Int
) : Parcelable

/* -------------------------------------------------------------------------------------------------
 * The adapter's role is to >>>convert an object at a position into a list row item<<< to be
 * inserted.
 *
 * However, with a RecyclerView the adapter requires the existence of a "ViewHolder" object which
 * describes and provides access to all the views within each item row. Your holder also should
 * contain and initialize a member variable for any view that will be set as you render a row.
 *
 * Every adapter has three primary methods:
 * 1. onCreateViewHolder to inflate the item layout and create the holder;
 * 2. onBindViewHolder to set the view attributes based on the data;
 * 3. and getItemCount to determine the number of items. We need to implement all three to finish
 *    the adapter.
 * ---------------------------------------------------------------------------------------------- */
class DataAdapter : RecyclerView.Adapter<DataAdapter.DataViewHolder> {
    private var listOfDataReason: ArrayList<DataReason>
    private var listener: ActMain

    constructor(listOfDataReason: ArrayList<DataReason>, listener: ActMain) : super() {
        this@DataAdapter.listOfDataReason = listOfDataReason
        this@DataAdapter.listener = listener
    }

    inner class DataViewHolder : RecyclerView.ViewHolder {
        val compCardviewReasonBinding: CompCardviewReasonBinding

        constructor(compCardviewReasonBinding: CompCardviewReasonBinding) : super(compCardviewReasonBinding.root) {
            this@DataViewHolder.compCardviewReasonBinding = compCardviewReasonBinding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataAdapter.DataViewHolder {
        val context = parent.context
        val compCardviewReasonBinding = CompCardviewReasonBinding.inflate(LayoutInflater.from(context), parent, false)
        return this@DataAdapter.DataViewHolder(compCardviewReasonBinding)
    }

    override fun onBindViewHolder(holder: DataAdapter.DataViewHolder, position: Int) {
        val (title, description, image) = this@DataAdapter.listOfDataReason[position]
        holder.compCardviewReasonBinding.compCardviewTitle.text = title
        holder.compCardviewReasonBinding.compCardviewDescription.text = description
        holder.compCardviewReasonBinding.compCardviewImage.setImageResource(image)

        holder.compCardviewReasonBinding.root.setOnClickListener({
            val intent = Intent(holder.compCardviewReasonBinding.root.context, ActDetail::class.java)
            intent.putExtra("dataReasonPosition", this@DataAdapter.listOfDataReason[holder.adapterPosition])
            holder.compCardviewReasonBinding.root.context.startActivity(intent)
        })
    }

    override fun getItemCount(): Int {
        return this@DataAdapter.listOfDataReason.size
    }
}

/* -------------------------------------------------------------------------------------------------
 * Here we go again
 * ---------------------------------------------------------------------------------------------- */
class ActMain : AppCompatActivity {
    private lateinit var actMainBinding: ActMainBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var dataAdapter: DataAdapter
    private val listOfDataReason: ArrayList<DataReason> = ArrayList()

    constructor() : super() {}

    override
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        actMainBinding = ActMainBinding.inflate(this@ActMain.layoutInflater)

        enableEdgeToEdge()
        this@ActMain.setContentView(actMainBinding.root)
        ViewCompat.setOnApplyWindowInsetsListener(actMainBinding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        this@ActMain.recyclerView = actMainBinding.actMainRecyclerview

        this@ActMain.initialiseRecyclerView()
        this@ActMain.setSupportActionBar(actMainBinding.actMainActionbar)
        this@ActMain.supportActionBar?.title = resources.getText(R.string.act_main_actionbar)
    }

    override
    fun onCreateOptionsMenu(menu: Menu?): Boolean {
        this@ActMain.menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override
    fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_main_option_list -> {
                this@ActMain.recyclerView.layoutManager = LinearLayoutManager(this@ActMain)
            }
            R.id.menu_main_option_grid -> {
                this@ActMain.recyclerView.layoutManager = GridLayoutManager(this@ActMain, 2)
            }
            R.id.menu_main_option_about -> {
                val intent = Intent(this@ActMain, ActAbout::class.java)
                this@ActMain.startActivity(intent)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private
    fun initialiseRecyclerView() {
        this@ActMain.recyclerView.setHasFixedSize(true)

        val titles = resources.getStringArray(R.array.titles)
        val descriptions = resources.getStringArray(R.array.descriptions)
        val images = resources.obtainTypedArray(R.array.images)
        val reasons = ArrayList<DataReason>()

        for (i in titles.indices) {
            val reason = DataReason(titles[i], descriptions[i], images.getResourceId(i, -1))
            reasons.add(reason)
        }

        this@ActMain.listOfDataReason.addAll(reasons)

        this@ActMain.recyclerView.layoutManager = LinearLayoutManager(this@ActMain)
        this@ActMain.dataAdapter = DataAdapter(this@ActMain.listOfDataReason, this@ActMain)
        this@ActMain.recyclerView.adapter = this@ActMain.dataAdapter
    }
}