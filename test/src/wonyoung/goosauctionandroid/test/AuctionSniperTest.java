package wonyoung.goosauctionandroid.test;

import junit.framework.TestCase;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.States;
import org.jmock.integration.junit3.JUnit3Mockery;

import wonyoung.goosauctionandroid.Auction;
import wonyoung.goosauctionandroid.AuctionEventListener.PriceSource;
import wonyoung.goosauctionandroid.AuctionSniper;
import wonyoung.goosauctionandroid.SniperListener;
import wonyoung.goosauctionandroid.SniperSnapShot;
import wonyoung.goosauctionandroid.SniperState;

public class AuctionSniperTest extends TestCase {
	protected static final String ITEM_ID = "";
	private final Mockery context = new JUnit3Mockery();
	private final SniperListener sniperListener = 
				context.mock(SniperListener.class);
	private final Auction auction = context.mock(Auction.class);
	private final AuctionSniper sniper = new AuctionSniper(ITEM_ID, auction, sniperListener);
	private final States sniperState = context.states("sniper");
	
	public void testReportsIsWinningWhenCurrentPriceComesFromSniper() {
		context.checking(new Expectations() {{
			atLeast(1).of(sniperListener).sniperWinning();
		}});
		
		sniper.currentPrice(123, 45, PriceSource.FromSniper);
	}
	public void testReportsLostWhenAuctionClosesImmediately() {
		context.checking(new Expectations() {{
			one(sniperListener).sniperLost();
		}});
		
		sniper.auctionClosed();
	}
	public void testReportsLostWhenAuctionClosesWhenBidding() {
		final int price = 123;
		final int increment = 45;
		final int bid = price + increment;
		context.checking(new Expectations() {{
			ignoring(auction);
			allowing(sniperListener).sniperStateChanged(
					new SniperSnapShot(ITEM_ID, price, bid, SniperState.BIDDING));
				then(sniperState.is("bidding"));
			atLeast(1).of(sniperListener).sniperLost();
				when(sniperState.is("bidding"));
		}});
		
		sniper.currentPrice(price, increment, PriceSource.FromOtherBidder);
		sniper.auctionClosed();
	}
	
	public void testReportsWonIfAuctionClosesWhenWinning() {
		context.checking(new Expectations() {{
			ignoring(auction);
			allowing(sniperListener).sniperWinning();
				then(sniperState.is("winning"));
			atLeast(1).of(sniperListener).sniperWon();
				when(sniperState.is("winning"));
		}});
		
		sniper.currentPrice(123, 45, PriceSource.FromSniper);
		sniper.auctionClosed();
	}
	public void testBidsHigherAndReportsBiddingWhenNewPriceArrives() {
		final int price = 1001;
		final int increment = 25;
		final int bid = price + increment;
		context.checking(new Expectations() {{
			one(auction).bid(price + increment);
			atLeast(1).of(sniperListener).sniperStateChanged(
					new SniperSnapShot(ITEM_ID, price, bid, SniperState.BIDDING));
			
		}});
		
		sniper.currentPrice(price, increment, PriceSource.FromOtherBidder);
	}
	
	
	public void tearDown() {
		context.assertIsSatisfied();
	}
}

