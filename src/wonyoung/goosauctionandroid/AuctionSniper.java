package wonyoung.goosauctionandroid;

public class AuctionSniper implements AuctionEventListener {

	private SniperListener listener;
	private Auction auction;
	private boolean isWinning = false;
	private String itemId;

	public AuctionSniper(SniperListener sniperListener) {
		this.listener = sniperListener;
	}

	public AuctionSniper(Auction auction, SniperListener sniperListener) {
		this.auction = auction;
		this.listener = sniperListener;
	}

	public AuctionSniper(String itemId, Auction auction, SniperListener sniperListener) {
		this.itemId = itemId;
		this.auction = auction;
		this.listener = sniperListener;
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
			listener.sniperWinning();
		} else {
			int bid = price + increment;
			auction.bid(bid);
			listener.sniperStateChanged(
					new SniperSnapShot(itemId, price, bid, SniperState.BIDDING));
		}
	}
}
