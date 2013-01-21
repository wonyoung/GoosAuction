package wonyoung.goosauctionandroid;

public class AuctionSniper implements AuctionEventListener {

	private SniperListener listener;
	private Auction auction;
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
		snapshot = snapshot.closed();
		notifyChange();
	}

	private void notifyChange() {
		listener.sniperStateChanged(snapshot);
	}

	@Override
	public void currentPrice(int price, int increment, PriceSource priceSource) {
		switch(priceSource) {
		case FromSniper:
			snapshot = snapshot.winning(price);
			break;
		case FromOtherBidder:
			int bid = price + increment;
			auction.bid(bid);
			snapshot = snapshot.bidding(price, bid);
			break;
		}
		notifyChange();
	}
}
