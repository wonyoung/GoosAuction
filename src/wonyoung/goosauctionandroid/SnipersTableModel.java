package wonyoung.goosauctionandroid;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SnipersTableModel extends ArrayAdapter<String> {

	private static final String[] STATUS_TEXT = { 
		"Joining",
		"Bidding",
		"Winning",
		"Lost",
		"Won"
	};
	private int resourceId;
	private final SniperSnapShot STARTING_UP = new SniperSnapShot("", 0, 0, SniperState.JOINNING);
	private ArrayList<String> texts;
	private SniperSnapShot snapshot = STARTING_UP;
	Context context;
	public SnipersTableModel(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
		this.resourceId = textViewResourceId;
		this.context = context;
		texts = new ArrayList<String>();
		texts.add(textFor(snapshot.state));
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
		itemIdTextView.setText(snapshot.itemId);
		
		Log.d("AAAA", "[[["+snapshot.itemId+"]]]");
		TextView lastPriceTextView = (TextView) view.findViewById(R.id.last_price);
		lastPriceTextView.setText(Integer.toString(snapshot.lastPrice));
		
		TextView lastBidTextView = (TextView) view.findViewById(R.id.last_bid);
		lastBidTextView.setText(Integer.toString(snapshot.lastBid));		
		
		TextView sniperStatusTextView = (TextView) view.findViewById(R.id.sniper_status);
		sniperStatusTextView.setText(textFor(snapshot.state));
		
		return view;
	}
	public void sniperStatusChanged(SniperSnapShot newSniperState) {
		snapshot = newSniperState;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		return Column.at(columnIndex).valueIn(snapshot);
	}

	public static String textFor(SniperState state) {
		return STATUS_TEXT[state.ordinal()];
	}

}
