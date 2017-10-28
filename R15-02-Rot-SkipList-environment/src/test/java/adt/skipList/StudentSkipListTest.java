package adt.skipList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StudentSkipListTest {

	SkipList<String> skip;
	SkipListNode<String>[] array;

	SkipListImpl<Integer> list;
	private int maxLevel = 20;

	@Before
	public void setUp() {
		skip = new SkipListImpl<String>(4);

		list = new SkipListImpl<Integer>(maxLevel);

	}

	@Test
	public void testInsert() {
		skip.insert(10, "A", 2);
		skip.insert(20, "B", 1);
		skip.insert(0, "C", 1);
		skip.insert(15, "D", 3);
		skip.insert(5, "E", 3);

		assertEquals(5, skip.size());

		array = skip.toArray();
		assertEquals("[<ROOT,4,4>, <0,1>, <5,3>, <10,2>, <15,3>, <20,1>, <NIL,4>]", Arrays.toString(array));
		assertEquals(0, array[0].getForward(0).getKey());
		assertEquals(5, array[0].getForward(1).getKey());
		assertEquals(5, array[0].getForward(2).getKey());
		assertEquals(5, array[1].getForward(0).getKey());
		assertEquals(10, array[2].getForward(0).getKey());
		assertEquals(10, array[2].getForward(1).getKey());
		assertEquals(15, array[2].getForward(2).getKey());
		assertEquals(15, array[3].getForward(0).getKey());
		assertEquals(15, array[3].getForward(1).getKey());
		assertEquals(20, array[4].getForward(0).getKey());
		assertEquals(Integer.MAX_VALUE, array[4].getForward(1).getKey());
		assertEquals(Integer.MAX_VALUE, array[4].getForward(2).getKey());
		assertEquals(Integer.MAX_VALUE, array[5].getForward(0).getKey());

		List<String> aux = new ArrayList<>();
		for (int i = 1; i < array.length - 1; ++i)
			aux.add(array[i].getValue());

		assertEquals("[C, E, A, D, B]", aux.toString());

		skip.insert(0, "A", 1);
		skip.insert(5, "B", 3);
		skip.insert(10, "C", 2);
		skip.insert(15, "D", 3);
		skip.insert(20, "E", 1);

		assertEquals(5, skip.size());

		array = skip.toArray();
		assertEquals("[<ROOT,4,4>, <0,1>, <5,3>, <10,2>, <15,3>, <20,1>, <NIL,4>]", Arrays.toString(array));
		assertEquals(0, array[0].getForward(0).getKey());
		assertEquals(5, array[0].getForward(1).getKey());
		assertEquals(5, array[0].getForward(2).getKey());
		assertEquals(5, array[1].getForward(0).getKey());
		assertEquals(10, array[2].getForward(0).getKey());
		assertEquals(10, array[2].getForward(1).getKey());
		assertEquals(15, array[3].getForward(0).getKey());
		assertEquals(20, array[4].getForward(0).getKey());
		assertEquals(Integer.MAX_VALUE, array[4].getForward(1).getKey());
		assertEquals(Integer.MAX_VALUE, array[5].getForward(0).getKey());

		aux.clear();
		for (int i = 1; i < array.length - 1; ++i)
			aux.add(array[i].getValue());

		assertEquals("[A, B, C, D, E]", aux.toString());
	}

	@Test
	public void testInsertHeight() {
		for (int i = 0; i < 20; i++) {
			list.insert(i, 5, i);
		}
	}

	@Test
	public void testHeight() {

		assertEquals(0, list.height());

		list.insert(2, 5, 14);
		assertTrue(list.height() == 14);
		list.insert(3, 2, 8);
		assertTrue(list.height() == 14);
		list.insert(7, 3, 15);
		assertTrue(list.height() == 15);
		list.insert(4, 10, 20);
		assertTrue(list.height() == 20);

	}

	@Test
	public void testSearch2() {
		assertNull(list.search(2));

		assertEquals(0, list.height());

		list.insert(4, 4, 1);

		assertEquals(1, list.height());

		assertEquals(4, list.search(4).getKey());
		list.insert(8, 5, 2);

		assertEquals(2, list.height());

		assertEquals(8, list.search(8).getKey());
		list.insert(1, 6, 3);

		assertEquals(3, list.height());

		assertEquals(1, list.search(1).getKey());
		list.insert(-4, 7, 4);
		assertEquals(-4, list.search(-4).getKey());

		assertEquals(4, list.height());

		assertEquals(4, list.size());
	}

	@Test
	public void testRemove2() {
		list.insert(2, 3, 1);
		list.insert(5, 6, 2);
		list.insert(1, 9, 3);
		list.insert(3, 1, 4);
		list.insert(7, 0, 5);
		list.insert(9, 2, 6);

		assertNull(list.search(10));

		assertEquals(6, list.size());

		list.remove(5);
		assertEquals(5, list.size());
		list.remove(1);
		assertEquals(4, list.size());
		list.remove(3);
		assertEquals(3, list.size());
		list.remove(2);
		assertEquals(2, list.size());
		list.remove(7);
		assertEquals(1, list.size());
		list.remove(9);
		assertEquals(0, list.size());

	}

	@Test
	public void testSearch() {
		skip.insert(10, "A", 2);
		skip.insert(20, "B", 1);
		skip.insert(0, "C", 1);
		skip.insert(15, "D", 3);
		skip.insert(5, "E", 2);

		assertEquals("A", skip.search(10).getValue());
		assertEquals("B", skip.search(20).getValue());
		assertEquals("C", skip.search(0).getValue());
		assertEquals("D", skip.search(15).getValue());
		assertEquals("E", skip.search(5).getValue());

		assertEquals(null, skip.search(-10));
		assertEquals(null, skip.search(30));
		assertEquals(null, skip.search(9));
	}

	@Test
	public void testRemove() {
		skip.insert(10, "A", 1);
		skip.insert(20, "B", 2);
		skip.insert(0, "C", 2);
		skip.insert(15, "D", 3);
		skip.insert(5, "E", 1);

		skip.insert(-10, "F", 1);
		skip.insert(30, "G", 3);
		skip.insert(9, "H", 2);
		skip.insert(17, "I", 2);
		skip.insert(-2, "J", 1);

		assertEquals(10, skip.size());

		skip.remove(10);
		skip.remove(20);
		skip.remove(0);
		skip.remove(15);
		skip.remove(5);

		assertEquals(5, skip.size());

		array = skip.toArray();
		assertEquals("[<ROOT,4,4>, <-10,1>, <-2,1>, <9,2>, <17,2>, <30,3>, <NIL,4>]", Arrays.toString(array));
		assertEquals(-10, array[0].getForward(0).getKey());
		assertEquals(9, array[0].getForward(1).getKey());
		assertEquals(30, array[0].getForward(2).getKey());
		assertEquals(-2, array[1].getForward(0).getKey());
		assertEquals(9, array[2].getForward(0).getKey());
		assertEquals(17, array[3].getForward(0).getKey());
		assertEquals(17, array[3].getForward(1).getKey());
		assertEquals(30, array[4].getForward(0).getKey());
		assertEquals(30, array[4].getForward(1).getKey());
		assertEquals(Integer.MAX_VALUE, array[5].getForward(0).getKey());
		assertEquals(Integer.MAX_VALUE, array[5].getForward(1).getKey());
		assertEquals(Integer.MAX_VALUE, array[5].getForward(2).getKey());

		skip.remove(-10);
		skip.remove(30);
		skip.remove(9);
		skip.remove(17);
		skip.remove(-2);

		assertEquals(0, skip.size());

		array = skip.toArray();
		assertEquals("[<ROOT,4,4>, <NIL,4>]", Arrays.toString(array));
		assertEquals(Integer.MAX_VALUE, array[0].getForward(0).getKey());
	}

	@Test
	public void testToArray() {
		Assert.assertArrayEquals(new Integer[] { Integer.MIN_VALUE, Integer.MAX_VALUE },
				castArrayOfSkipNode(list.toArray()));

		list.insert(4, 5, 1);
		list.insert(6, 5, 2);
		list.insert(8, 5, 3);
		Assert.assertArrayEquals(new Integer[] { Integer.MIN_VALUE, 4, 6, 8, Integer.MAX_VALUE },
				castArrayOfSkipNode(list.toArray()));

		list.insert(1, 5, 4);
		list.insert(3, 5, 5);
		list.insert(9, 5, 6);
		Assert.assertArrayEquals(new Integer[] { Integer.MIN_VALUE, 1, 3, 4, 6, 8, 9, Integer.MAX_VALUE },
				castArrayOfSkipNode(list.toArray()));

		list.insert(5, 5, 7);
		list.insert(2, 5, 8);
		list.insert(7, 5, 9);
		Assert.assertArrayEquals(new Integer[] { Integer.MIN_VALUE, 1, 2, 3, 4, 5, 6, 7, 8, 9, Integer.MAX_VALUE },
				castArrayOfSkipNode(list.toArray()));

		list.insert(0, 3, 2);
		Assert.assertArrayEquals(new Integer[] { Integer.MIN_VALUE, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, Integer.MAX_VALUE },
				castArrayOfSkipNode(list.toArray()));

	}

	private Integer[] castArrayOfSkipNode(SkipListNode[] nodes) {
		Integer[] resp = new Integer[nodes.length];
		for (int i = 0; i < resp.length; i++) {
			resp[i] = nodes[i].getKey();
		}
		return resp;
	}

}
