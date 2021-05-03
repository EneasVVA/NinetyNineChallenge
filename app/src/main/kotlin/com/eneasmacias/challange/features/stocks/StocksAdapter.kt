
package com.eneasmacias.challange.features.stocks

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.eneasmacias.challange.R
import com.eneasmacias.challange.core.extension.inflate
import kotlinx.android.synthetic.main.row_stock.view.*
import javax.inject.Inject
import kotlin.properties.Delegates

class StocksAdapter
@Inject constructor() : RecyclerView.Adapter<StocksAdapter.ViewHolder>() {

    internal var collection: List<StockView> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    internal var itemClickListener: (StockView) -> Unit = { _ -> }
    internal var deleteFavoriteClickListener: (StockView) -> Unit = { _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(R.layout.row_stock))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) =
        viewHolder.bind(collection[position], itemClickListener, deleteFavoriteClickListener)

    override fun getItemCount() = collection.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(stockView: StockView, clickListener: (StockView) -> Unit, deleteFavoriteClickListener: (StockView) -> Unit) {
            itemView.tvStockId.text = stockView.id
            itemView.ibDeleteFavorite.setOnClickListener {
                deleteFavoriteClickListener(stockView)
            }
            itemView.setOnClickListener {
                clickListener(stockView)
            }
        }
    }
}
