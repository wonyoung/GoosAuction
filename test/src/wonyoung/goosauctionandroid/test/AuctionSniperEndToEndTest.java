package wonyoung.goosauctionandroid.test;

import wonyoung.goosauctionandroid.MainActivity;
import android.test.ActivityInstrumentationTestCase2;

import com.jayway.android.robotium.solo.Solo;

public class AuctionSniperEndToEndTest extends ActivityInstrumentationTestCase2<MainActivity> {
	
	private Solo solo;
	private final FakeAuctionServer auction = new FakeAuctionServer("item-54321");
	private ApplicationRunner application;
	
	public AuctionSniperEndToEndTest(Class<MainActivity> activityClass) {
		super(activityClass);
	}
	
	public AuctionSniperEndToEndTest() {
		this(MainActivity.class);
	}
	
	public void setUp() throws Exception {
		solo = new Solo(getInstrumentation(), getActivity());
		application = new ApplicationRunner(solo);
	}
	
	public void testSniperJoinsAuctionUntilAuctionClosed() throws Exception {
		auction.startSellingItem();
		application.startBiddingIn(auction);
		auction.hasReceivedJoinRequestFromSniper();
		auction.announceClosed();
		application.showsSniperHasLostAuction();
	}
	
	public void testSniperMakesAHigherBidButLoses() throws Exception {
		auction.startSellingItem();
		
		application.startBiddingIn(auction);
		auction.hasReceivedJoinRequestFrom(ApplicationRunner.SNIPER_XMPP_ID);
		
		auction.reportPrice(1000, 98, "other bidder");
		application.hasShownSniperIsBidding();
		
		auction.hasReceivedBid(1098, ApplicationRunner.SNIPER_XMPP_ID);
		
		auction.announceClosed();
		application.showsSniperHasLostAuction();
	}
	
	public void testSniperWinsAnAuctionByBiddingHigher() throws Exception {
		auction.startSellingItem();
		
		application.startBiddingIn(auction);
		auction.hasReceivedJoinRequestFrom(ApplicationRunner.SNIPER_XMPP_ID);
		
		auction.reportPrice(1000, 98, "other bidder");
		application.hasShownSniperIsBidding(1000, 1098);
		
		auction.hasReceivedBid(1098, ApplicationRunner.SNIPER_XMPP_ID);
		
		auction.reportPrice(1098, 97, ApplicationRunner.SNIPER_XMPP_ID);
		application.hasShownSniperIsWinning(1098);
		
		auction.announceClosed();
		application.showsSniperHasWonAuction(1098);
	}
	
	public void tearDown() {
		solo.finishOpenedActivities();
		auction.stop();
	}
}
