package wonyoung.goosauctionandroid.test;

import wonyoung.goosauctionandroid.MainActivity;

import com.jayway.android.robotium.solo.Solo;

public class ApplicationRunner {
		
	public static final String SNIPER_ID = "sniper";
	public static final String SNIPER_PASSWORD = "sniper";
	private static final String XMPP_HOSTNAME = "localhost";
	public static final String SNIPER_XMPP_ID = "sniper";
	private AuctionSniperDriver driver;


	public ApplicationRunner(Solo solo) {
		driver = new AuctionSniperDriver(solo);
	}
	
	public void startBiddingIn(final FakeAuctionServer auction) {
		driver.joinSniper(XMPP_HOSTNAME, SNIPER_ID, SNIPER_PASSWORD, auction.getItemId());
		driver.showSniperStatus(MainActivity.STATUS_JOINING);
	}
	
	public void showsSniperHasLostAuction() {
		driver.showSniperStatus(MainActivity.STATUS_LOST);
	}
	public void stop() {
	}

	public void hasShownSniperIsBidding() {
		driver.showSniperStatus(MainActivity.STATUS_BIDDING);
	}

	public void hasShownSniperIsWinning() {
		driver.showSniperStatus(MainActivity.STATUS_WINNING);
	}

	public void showsSniperHasWonAuction() {
		driver.showSniperStatus(MainActivity.STATUS_WON);
	}
}
