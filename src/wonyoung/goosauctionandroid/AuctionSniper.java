package wonyoung.goosauctionandroid;

public class AuctionSniper implements AuctionEventListener {

	private SniperListener listener;
	private Auction auction;
	private boolean isWinning = false;
	private SniperSnapShot snapshot;

	public AuctionSniper(SniperListener sniperListener) {
		this.listener = sniperListener;
	}

	public AuctionSniper(Auction auction, SniperListener sniperListener) {
		this.auction = auction;
		this.listener = sniperListener;
	}

	public AuctionSniper(String itemId, Auction auction, SniperListener sniperListener) {
		this.auction = auction;
		this.listener = sniperListener;
		this.snapshot = SniperSnapShot.joining(itemId);
	}


	@Override
	public void auctionClosed() {
		if (isWinning ) {
			listener.sniperWon();
		} else {
			listener.sniperLost();
		}
	}

	@Override
	public void currentPrice(int price, int increment, PriceSource priceSource) {
		isWinning = priceSource == PriceSource.FromSniper;
		if (isWinning) {
			snapshot = snapshot.winning(price);
		} else {
			int bid = price + increment;
			auction.bid(bid);
			snapshot = snapshot.bidding(price, bid);
		}
		listener.sniperStateChanged(snapshot);
	}
}
