package wonyoung.goosauctionandroid.test;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.packet.Message;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit3.*;

import wonyoung.goosauctionandroid.AuctionEventListener;
import wonyoung.goosauctionandroid.AuctionEventListener.PriceSource;
import wonyoung.goosauctionandroid.AuctionMessageTranslator;

import junit.framework.TestCase;

public class AuctionMessageTranslatorTest extends TestCase {

	private final Mockery context = new JUnit3Mockery();
	protected final AuctionEventListener listener = 
			context.mock(AuctionEventListener.class);
	
	private static final Chat UNUSED_CHAT = null;
	private final AuctionMessageTranslator translator
		= new AuctionMessageTranslator(ApplicationRunner.SNIPER_ID, listener);


	public void testNotifiesAuctionClosedWhenCloseMessageReceivedFromOtherBidder() {
		context.checking(new Expectations() {{
			oneOf(listener).auctionClosed();
		}});
		
		Message message = new Message();
		message.setBody("SOLVersion: 1.1; Event: CLOSE;");
		
		translator.processMessage(UNUSED_CHAT, message);
	}
	
	public void testNotifiesBidDetailsWhenCurrentPriceMessageReceivedFromOtherBidder() {
		context.checking(new Expectations() {{
			exactly(1).of(listener).currentPrice(192, 7, PriceSource.FromOtherBidder);
		}});
		
		Message message = new Message();
		message.setBody("SOLVersion: 1.1; Event: PRICE; CurrentPrice: 192; Increment: 7; Bidder: Someone else;");
		
		translator.processMessage(UNUSED_CHAT, message);
	}
	
	public void testNotifiesBidDetailsWhenCurrentPriceMessageReceivedFromSniper() {
		context.checking(new Expectations() {{
			exactly(1).of(listener).currentPrice(234, 5, PriceSource.FromSniper);
		}});
		
		Message message = new Message();
		message.setBody("SOLVersion: 1.1; Event: PRICE; CurrentPrice: 234; Increment: 5; Bidder: " + ApplicationRunner.SNIPER_ID + ";");
		
		translator.processMessage(UNUSED_CHAT, message);
	}
	
	public void tearDown() {
		context.assertIsSatisfied();
	}
}
