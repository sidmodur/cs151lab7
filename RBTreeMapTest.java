import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

public class RBTreeMapTest {

	public void testConstruct() {
		RBTreeMap test = new RBTreeMap(15, "hello");
		System.out.println(test.color);
		System.out.println(test);
		assertTrue("New tree has black root", test.color == 1);
	}

	
	public void testInsert() {
		RBTreeMap test = new RBTreeMap(10, "grandparent");
		test.put(15, new RBTreeMap(15, "parent"));
		test.put(20, new RBTreeMap(20, "child"));
		System.out.println(test);
		Iterator<Integer> i = test.keys();
		while (i.hasNext()) {
			System.out.println(i.next());
		}

	}
	
	@Test
	public void testLeftRotation() {
		RBTreeMap test = new RBTreeMap(10, "grandparent");
		test.put(15, new RBTreeMap(15, "parent"));
		test.put(5, new RBTreeMap(5, "uncle"));
		test.put(20, new RBTreeMap(20, "subject 1"));
		test.put(30, new RBTreeMap(30, "subject 2"));
		test.put(40, new RBTreeMap(40, "subject 3"));
		test.put(50, new RBTreeMap(50, "subject 4"));
		System.out.println(test);
		Iterator<Integer> i = test.keys();
		while (i.hasNext()) {
			System.out.println(i.next());
		}

	}
	

	// When a child is added with the same key, should it overwrite the parent?
	public void testInserting() {
		RBTreeMap test = new RBTreeMap(0, "parent");
		test.put(1, new RBTreeMap(0, "child"));
		System.out.println(test);
	}
	
	
	public void testIsEmpty() {
		RBTreeMap test = new RBTreeMap();
		assertTrue("Empty Tree is Null on contruction", test.value == null);
	}

}
