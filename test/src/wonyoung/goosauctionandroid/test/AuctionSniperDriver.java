package wonyoung.goosauctionandroid.test;

import static junit.framework.Assert.assertTrue;
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
}
