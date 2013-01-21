package wonyoung.goosauctionandroid;

import java.util.ArrayList;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

	public class SniperStateDisplayer implements SniperListener {

		@Override
		public void sniperLost() {
			showStatus(STATUS_LOST);
		}

		@Override
		public void sniperBidding() {
			showStatus(STATUS_BIDDING);
		}

		@Override
		public void sniperWinning() {
			showStatus(STATUS_WINNING);
		}

		@Override
		public void sniperWon() {
			showStatus(STATUS_WON);
		}

		@Override
		public void sniperStateChanged(final SniperSnapShot sniperSnapShot) {
			sniperStatusChanged(sniperSnapShot);
		}
	}

	public static class XMPPAuction implements Auction {

		private final Chat chat;

		public XMPPAuction(Chat chat) {
			this.chat = chat;
		}

		@Override
		public void bid(int amount) {
			sendMessage(String.format(BID_COMMAND_FORMAT, amount));					
		}
		
		@Override
		public void join() {
			sendMessage(JOIN_COMMAND_FORMAT);
		}

		private void sendMessage(final String message) {
			try {
				chat.sendMessage(message);					
			} catch (XMPPException e) {
				e.printStackTrace();
			}
		}
	}

	private static final String AUCTION_RESOURCE = "Auction";
	private static final String ITEM_ID_AS_LOGIN = "auction-%s";
	private static final String AUCTION_ID_FORMAT = 
			ITEM_ID_AS_LOGIN + "@%s/" + AUCTION_RESOURCE;
	
	public static final String BID_COMMAND_FORMAT = "SOLVersion: 1.1; Command: BID; Price: %d;";
	public static final String JOIN_COMMAND_FORMAT = "SOLVersion: 1.1; Command: JOIN;";
	
	public static final String STATUS_JOINING = "Joining";
	public static final String STATUS_LOST = "Lost";
	public static final String STATUS_BIDDING = "Bidding";
	public static final String STATUS_WINNING = "Winning";
	public static final String STATUS_WON = "Won";
	private ListView snipersListView;
	
	@SuppressWarnings("unused")
	private Chat notToBeGCd;
	private SnipersTableModel snipers;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		snipers = new SnipersTableModel(this, R.layout.snipers_view);

		snipersListView = (ListView) findViewById(R.id.snipersListView);
		snipersListView.setAdapter(snipers);
	}

	public void connectToXMPPAndSendAMessage(String hostname, String username, String password,
			String itemId) throws XMPPException {
		XMPPConnection connection = connectTo(hostname, username, password);
		final Chat chat = connection.getChatManager().createChat(
				auctionId(itemId, connection), null);
		this.notToBeGCd = chat;
		
		Auction auction = new XMPPAuction(chat);
		String sniperId = connection.getUser().split("@")[0];
		chat.addMessageListener(
				new AuctionMessageTranslator(
						sniperId,
						new AuctionSniper(itemId, auction, new SniperStateDisplayer())));
		auction.join();
	}

	private static String auctionId(String itemId, XMPPConnection connection) {
		return String.format(AUCTION_ID_FORMAT, itemId,
				connection.getServiceName());
	}

	private static XMPPConnection connectTo(String hostname, String username, String password) 
			throws XMPPException {
		XMPPConnection connection = new XMPPConnection(hostname);
		connection.connect();
		connection.login(username, password, AUCTION_RESOURCE);
		
		return connection;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public void join(final String hostname, final String username, final String password,
			final String itemId) {
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					connectToXMPPAndSendAMessage(hostname, username, password, itemId);
				} catch (XMPPException e) {
					e.printStackTrace();
				}
			}
		});		
		thread.start();
		
		showStatus(STATUS_JOINING);
	}

	private void showStatus(String status) {
		snipers.setStatusText(status);
		
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				snipers.notifyDataSetChanged();
			}
		});
	}

	private void sniperStatusChanged(SniperSnapShot sniperState) {
		snipers.sniperStatusChanged(sniperState);

		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				snipers.notifyDataSetChanged();
			}
		});	
	}
}
