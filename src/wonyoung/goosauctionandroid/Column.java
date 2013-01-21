package wonyoung.goosauctionandroid;

public enum Column {
	ITEM_IDENTIFIER {
		@Override
		public Object valueIn(SniperSnapShot snapshot) {
			return snapshot.itemId;
		}
	},
	LAST_PRICE {
		@Override
		public Object valueIn(SniperSnapShot snapshot) {
			return snapshot.lastPrice;
		}
	},
	LAST_BID {
		@Override
		public Object valueIn(SniperSnapShot snapshot) {
			return snapshot.lastBid;
		}
	},
	SNIPER_STATE {
		@Override
		public Object valueIn(SniperSnapShot snapshot) {
			return SnipersTableModel.textFor(snapshot.state);
		}
	};
	
	abstract public Object valueIn(SniperSnapShot snapshot);
	public static Column at(int offset) { return values()[offset]; }
}
