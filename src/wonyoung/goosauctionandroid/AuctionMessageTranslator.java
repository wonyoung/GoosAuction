package wonyoung.goosauctionandroid;

import static wonyoung.goosauctionandroid.AuctionEventListener.PriceSource.FromOtherBidder;
import static wonyoung.goosauctionandroid.AuctionEventListener.PriceSource.FromSniper;

import java.util.HashMap;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

import wonyoung.goosauctionandroid.AuctionEventListener.PriceSource;

public class AuctionMessageTranslator implements MessageListener {

	private static final String EVENT_TYPE_PRICE = "PRICE";
	private static final String EVENT_TYPE_CLOSE = "CLOSE";

	public static class AuctionEvent {
		private final HashMap<String, String> fields = new HashMap<String, String>();
		
		public static AuctionEvent from(String messageBody) {
			AuctionEvent event = new AuctionEvent();
			for (String field : fieldsIn(messageBody)) {
				event.addField(field);
			}
			return event;
		}

		private void addField(String field) {
			String[] pair = field.split(":");
			fields.put(pair[0].trim(), pair[1].trim());
		}

		private static String[] fieldsIn(String messageBody) {
			return messageBody.split(";");
		}

		public String type() {
			return get("Event");
		}

		private String get(String string) {
			return fields.get(string);
		}

		public int increment() {
			return getInt("Increment");
		}

		public int currentPrice() {
			return getInt("CurrentPrice");
		}

		private int getInt(String string) {
			return Integer.parseInt(get(string));
		}

		public PriceSource isFrom(String sniperId) {
			return sniperId.equals(bidder()) ? FromSniper : FromOtherBidder;
		}

		private String bidder() {
			return get("Bidder");
		}
	}

	private AuctionEventListener listener;
	private final String sniperId;

	public AuctionMessageTranslator(String sniperId, AuctionEventListener listener) {
		this.sniperId = sniperId;
		this.listener = listener;
	}

	@Override
	public void processMessage(Chat chat, Message message) {
		AuctionEvent event = AuctionEvent.from(message.getBody());
		
		String eventType = event.type();
		if (EVENT_TYPE_CLOSE.equals(eventType)) {
			listener.auctionClosed();
		} else if (EVENT_TYPE_PRICE.equals(eventType)) {
			listener.currentPrice(event.currentPrice(), 
					event.increment(), 
					event.isFrom(sniperId));
		}
	}

}
