package wonyoung.goosauctionandroid.test;

import wonyoung.goosauctionandroid.MainActivity;

import com.jayway.android.robotium.solo.Solo;

public class ApplicationRunner {
		
	public static final String SNIPER_ID = "sniper";
	public static final String SNIPER_PASSWORD = "sniper";
	private static final String XMPP_HOSTNAME = "localhost";
	public static final String SNIPER_XMPP_ID = "sniper";
	private AuctionSniperDriver driver;
	private String itemId;


	public ApplicationRunner(Solo solo) {
		driver = new AuctionSniperDriver(solo, 1000);
	}
	
	public void startBiddingIn(final FakeAuctionServer auction) {
		itemId = auction.getItemId();
		driver.joinSniper(XMPP_HOSTNAME, SNIPER_ID, SNIPER_PASSWORD, auction.getItemId());
		driver.showSniperStatus(MainActivity.STATUS_JOINING);
	}
	
	public void showsSniperHasLostAuction() {
		driver.showSniperStatus(MainActivity.STATUS_LOST);
	}
	public void stop() {
	}

	public void hasShownSniperIsBidding(int lastPrice, int lastBid) {
		driver.showSniperStatus(itemId, lastPrice, lastBid,
				MainActivity.STATUS_BIDDING);
	}

	public void hasShownSniperIsWinning(int winningBid) {
		driver.showSniperStatus(itemId, winningBid, winningBid,
				MainActivity.STATUS_WINNING);
	}

	public void showsSniperHasWonAuction(int lastPrice) {
		driver.showSniperStatus(itemId, lastPrice, lastPrice,
				MainActivity.STATUS_WON);
	}
}
