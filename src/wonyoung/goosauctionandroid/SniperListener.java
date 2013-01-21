package wonyoung.goosauctionandroid;

public interface SniperListener {

	void sniperLost();

	void sniperBidding();

	void sniperWinning();

	void sniperWon();

	void sniperStateChanged(SniperSnapShot sniperSnapShot);

}
