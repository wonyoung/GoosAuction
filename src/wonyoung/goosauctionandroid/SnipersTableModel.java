package wonyoung.goosauctionandroid;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class SnipersTableModel extends ArrayAdapter<String> {

	private static final String[] STATUS_TEXT = { 
		MainActivity.STATUS_JOINING,
		MainActivity.STATUS_BIDDING,
		MainActivity.STATUS_WINNING,
		MainActivity.STATUS_LOST,
		MainActivity.STATUS_WON
	};
	private int resourceId;
	private final SniperSnapShot STARTING_UP = new SniperSnapShot("", 0, 0, SniperState.JOINNING);
	private ArrayList<String> texts;
	private SniperSnapShot sniperState = STARTING_UP;
	private String statusText = MainActivity.STATUS_JOINING;
	Context context;
	public SnipersTableModel(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
		this.resourceId = textViewResourceId;
		this.context = context;
		texts = new ArrayList<String>();
		texts.add(statusText);
		addAll(texts);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		
		if (view == null) {
			LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = li.inflate(resourceId, null);
		}
		TextView itemIdTextView = (TextView) view.findViewById(R.id.item_id);
		itemIdTextView.setText(sniperState.itemId);
		
		Log.d("AAAA", "[[["+sniperState.itemId+"]]]");
		TextView lastPriceTextView = (TextView) view.findViewById(R.id.last_price);
		lastPriceTextView.setText(Integer.toString(sniperState.lastPrice));
		
		TextView lastBidTextView = (TextView) view.findViewById(R.id.last_bid);
		lastBidTextView.setText(Integer.toString(sniperState.lastBid));		
		
		TextView sniperStatusTextView = (TextView) view.findViewById(R.id.sniper_status);
		sniperStatusTextView.setText(statusText);
		
		return view;
	}
	public void setStatusText(String status) {
		statusText = status;
	}

	public void sniperStatusChanged(SniperSnapShot newSniperState) {
		sniperState = newSniperState;
		statusText = STATUS_TEXT[newSniperState.state.ordinal()];	
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		switch(Column.at(columnIndex)) {
		case ITEM_IDENTIFIER:
			return sniperState.itemId;
		case LAST_PRICE:
			return sniperState.lastPrice;
		case LAST_BID:
			return sniperState.lastBid;
		case SNIPER_STATE:
			return statusText;
		default:
			throw new IllegalArgumentException("No column at " + columnIndex);
		}
	}

}
