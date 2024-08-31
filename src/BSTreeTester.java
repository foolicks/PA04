import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;
class BSTreeTester {

    private BSTree<Integer> bst;

    @BeforeEach
    void setUp() {
        bst = new BSTree<>();
    }

//    @Test
//    void testInsert() {
//        assertTrue(bst.insert(5));
//        assertTrue(bst.insert(3));
//        assertTrue(bst.insert(7));
//        assertFalse(bst.insert(5)); // Duplicate key
//    }

    @Test
    void testFindKey() {
        bst.insert(5);
        bst.insert(3);
        bst.insert(7);

        assertTrue(bst.findKey(5));
        assertTrue(bst.findKey(3));
        assertTrue(bst.findKey(7));
        assertFalse(bst.findKey(9));

        assertThrows(NullPointerException.class, () -> bst.findKey(null));
    }

    @Test
    void testGetSize() {
        assertEquals(0, bst.getSize());

        bst.insert(5);
        assertEquals(1, bst.getSize());

        bst.insert(3);
        bst.insert(7);
        assertEquals(3, bst.getSize());
    }

    @Test
    void testGetRoot() {
        assertNull(bst.getRoot());

        bst.insert(5);
        assertNotNull(bst.getRoot());
    }

    @Test
    void testInsertData() {
        bst.insert(5);
        bst.insert(3);
        bst.insert(7);

        bst.insertData(5, 10);
        bst.insertData(3, 20);
        bst.insertData(7, 30);

        LinkedList<Integer> dataList = bst.findDataList(5);
        assertEquals(1, dataList.size());
        assertTrue(dataList.contains(10));

        assertThrows(IllegalArgumentException.class, () -> bst.insertData(9, 40));
    }

    @Test
    void testFindDataList() {
        bst.insert(5);
        bst.insert(3);
        bst.insert(7);

        bst.insertData(5, 10);
        bst.insertData(3, 20);
        bst.insertData(7, 30);

        LinkedList<Integer> dataList = bst.findDataList(5);
        assertEquals(1, dataList.size());
        assertTrue(dataList.contains(10));

        assertThrows(IllegalArgumentException.class, () -> bst.findDataList(9));
    }

    @Test
    void testFindHeight() {
        assertEquals(-1, bst.findHeight());

        bst.insert(5);
        assertEquals(0, bst.findHeight());

        bst.insert(3);
        bst.insert(7);
        assertEquals(1, bst.findHeight());

        bst.insert(2);
        bst.insert(4);
        bst.insert(6);
        bst.insert(8);
        assertEquals(2, bst.findHeight());
    }

    @Test
    void testIterator() {
        bst.insert(5);
        bst.insert(3);
        bst.insert(7);
        bst.insert(2);
        bst.insert(4);
        bst.insert(6);
        bst.insert(8);

        List<Integer> expected = Arrays.asList(2, 3, 4, 5, 6, 7, 8);
        Iterator<Integer> iterator = bst.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            assertEquals(expected.get(i), iterator.next());
            i++;
        }
        assertFalse(iterator.hasNext());
    }

    @Test
    void testIntersection() {
        List<Integer> list1 = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> list2 = Arrays.asList(3, 4, 5, 6, 7);

        ArrayList<Integer> intersection = bst.intersection(list1.iterator(), list2.iterator());
        assertEquals(Arrays.asList(5), intersection);
    }

    @Test
    void testLevelMax() {
        bst.insert(5);
        bst.insert(3);
        bst.insert(7);
        bst.insert(2);
        bst.insert(4);
        bst.insert(6);
        bst.insert(8);

        assertEquals(Integer.valueOf(5), bst.levelMax(0));
        assertEquals(Integer.valueOf(7), bst.levelMax(1));
        assertEquals(Integer.valueOf(8), bst.levelMax(2));
        assertNull(bst.levelMax(-1));
        assertNull(bst.levelMax(3)); // Level doesn't exist
    }
}