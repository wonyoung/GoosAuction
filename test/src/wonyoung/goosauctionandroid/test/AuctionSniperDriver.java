package wonyoung.goosauctionandroid.test;

import static junit.framework.Assert.*;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;
import wonyoung.goosauctionandroid.MainActivity;

import android.view.View;
import android.widget.ListView;

import com.jayway.android.robotium.solo.Solo;

public class AuctionSniperDriver {
	Solo solo;

	public AuctionSniperDriver(Solo solo) {
		this.solo = solo;
	}

	public void showSniperStatus(String statusJoining) {
		assertThat("ListView not found", firstListView(), is(notNullValue()));
		assertTrue("sniper status is not "+statusJoining, solo.searchText(statusJoining));
		
	}

	private ListView firstListView() {
		for(View view : solo.getViews()) {
			if (view instanceof ListView)
				return (ListView) view;
		}
		return null;
	}

	public void joinSniper(String hostname, String username,
			String password, String itemId) {
		MainActivity activity = (MainActivity) solo.getCurrentActivity();
		activity.join(hostname, username, password, itemId);
	}
}
