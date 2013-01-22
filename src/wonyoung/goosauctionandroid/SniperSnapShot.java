package wonyoung.goosauctionandroid;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class SniperSnapShot {
	public final String itemId;
	public final int lastPrice;
	public final int lastBid;
	public final SniperState state;

	public SniperSnapShot(String itemId, int lastPrice, int lastBid,
			SniperState state) {
		this.itemId = itemId;
		this.lastPrice = lastPrice;
		this.lastBid = lastBid;
		this.state = state;
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	public static SniperSnapShot joining(String itemId) {
		return new SniperSnapShot(itemId, 0, 0, SniperState.JOINING);
	}

	public SniperSnapShot bidding(int price, int bid) {
		return new SniperSnapShot(itemId, price, bid, SniperState.BIDDING);
	}

	public SniperSnapShot winning(int price) {
		return new SniperSnapShot(itemId, price, price, SniperState.WINNING);
	}

	public SniperSnapShot closed() {
		return new SniperSnapShot(itemId, lastPrice, lastBid, state.whenAuctionClosed());
	}
}
