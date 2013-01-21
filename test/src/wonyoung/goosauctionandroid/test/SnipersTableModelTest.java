package wonyoung.goosauctionandroid.test;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit3.JUnit3Mockery;

import wonyoung.goosauctionandroid.Column;
import wonyoung.goosauctionandroid.MainActivity;
import wonyoung.goosauctionandroid.SniperState;
import wonyoung.goosauctionandroid.SnipersTableModel;
import wonyoung.goosauctionandroid.SniperSnapShot;
import android.test.AndroidTestCase;

public class SnipersTableModelTest extends AndroidTestCase {

	private final Mockery context = new JUnit3Mockery();
	private TableModelListener listener = context.mock(TableModelListener.class);
	private SnipersTableModel model;
	
	public void setUp() {
		model = new SnipersTableModel(getContext(), 0);
//		model.addTableModelListener(listener);
	}
	
//	public void testHasEnoughColumns() {
//		assertThat(model.getColumnCount(), equalTo(Column.values().length));
//	}
	
	public void testSetsSniperValuesInColumns() {
		context.checking(new Expectations() {{
			ignoring(listener);
//			one(listener).tableChanged(with(aRowChangedEvent()));
		}});
		model.sniperStatusChanged(new SniperSnapShot("item id", 555, 666, SniperState.BIDDING));
		
		assertColumnEquals(Column.ITEM_IDENTIFIER, "item id");
		assertColumnEquals(Column.LAST_PRICE, 555);
		assertColumnEquals(Column.LAST_BID, 666);
		assertColumnEquals(Column.SNIPER_STATE, MainActivity.STATUS_BIDDING);
	}

//	protected Matcher<TableModelEvent> aRowChangedEvent() {
//		return samePropertyValuesAs(new TableModelEvent(model, 0));
//	}

	private void assertColumnEquals(Column column, Object expected) {
		final int rowIndex = 0;
		final int columnIndex = column.ordinal();
		assertEquals(expected, model.getValueAt(rowIndex, columnIndex));
	}
	
}
