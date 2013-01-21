package wonyoung.goosauctionandroid.test;

import wonyoung.goosauctionandroid.MainActivity;
import wonyoung.goosauctionandroid.R;
import android.app.Activity;

import com.jayway.android.robotium.solo.Solo;
import com.objogate.wl.android.driver.AndroidDriver;
import com.objogate.wl.android.driver.ListViewDriver;

public class AuctionSniperDriver extends AndroidDriver<Activity>{
	Solo solo;

	public AuctionSniperDriver(Solo solo, int timeoutMillis) {
		super(solo, timeoutMillis);
		this.solo = solo;
	}

	public void showSniperStatus(String status) {
		new ListViewDriver(this, R.id.snipersListView).hasItem(containsAllStrings(status));
	}

	public void joinSniper(String hostname, String username,
			String password, String itemId) {
		MainActivity activity = (MainActivity) solo.getCurrentActivity();
		activity.join(hostname, username, password, itemId);
	}

	public void showSniperStatus(String itemId, int lastPrice, int lastBid,
			String status) {
		ListViewDriver listView = new ListViewDriver(this, R.id.snipersListView);

// TODO: com.objogate.wl.android.matcher.IterableComponentsMatcher.matching
//		listView.hasItem(matching(containsAllStrings(itemId),
//						containsAllStrings(String.valueOf(lastPrice)),
//						containsAllStrings(String.valueOf(lastBid)),
//						containsAllStrings(status)));

		listView.hasItem(containsAllStrings(itemId));
		listView.hasItem(containsAllStrings(String.valueOf(lastPrice)));
		listView.hasItem(containsAllStrings(String.valueOf(lastBid)));
		listView.hasItem(containsAllStrings(status));
	}
}
