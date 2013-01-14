package wonyoung.goosauctionandroid;

public interface AuctionEventListener {

	enum PriceSource {
		FromSniper, FromOtherBidder;
	};
	
	void auctionClosed();
	void currentPrice(int price, int increment, PriceSource priceSource);

}
