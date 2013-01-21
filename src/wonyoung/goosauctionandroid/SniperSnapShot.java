package wonyoung.goosauctionandroid;

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
		final int prime = 31;
		int result = 1;
		result = prime * result + ((itemId == null) ? 0 : itemId.hashCode());
		result = prime * result + lastBid;
		result = prime * result + lastPrice;
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SniperSnapShot other = (SniperSnapShot) obj;
		if (itemId == null) {
			if (other.itemId != null)
				return false;
		} else if (!itemId.equals(other.itemId))
			return false;
		if (lastBid != other.lastBid)
			return false;
		if (lastPrice != other.lastPrice)
			return false;
		if (state != other.state)
			return false;
		return true;
	}

	public static SniperSnapShot joining(String itemId) {
		return new SniperSnapShot(itemId, 0, 0, SniperState.JOINNING);
	}

	public SniperSnapShot bidding(int price, int bid) {
		return new SniperSnapShot(itemId, price, bid, SniperState.BIDDING);
	}

	public SniperSnapShot winning(int price) {
		// TODO Auto-generated method stub
		return new SniperSnapShot(itemId, price, price, SniperState.WINNING);
	}
	
	
	// TODO: change equals() and hashcode() method with Apache common.lang
}
