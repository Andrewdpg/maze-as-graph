package ui;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

public class SolutionTest {

    Menu menu;

    void initSetup() {
        menu = new Menu();
    }

    @Test
    public void test1() {
        initSetup();
        menu.setNumCols(4);
        menu.setNumRows(4);
        menu.setEntrances(new Integer[][] { { 1, 0 }, { 0, 3 }, { 3, 0 }, });
        menu.setMatrix(new int[][] {
                { 1, 2, 4, 2 },
                { 3, -1, 2, 1 },
                { -1, -1, 3, 8 },
                { 2, -1, 1, 0 } });
        menu.setEndRow(3);
        menu.setEndCol(2);
        List<String> result = menu.solve();

        assertEquals("2", result.get(0));
        assertEquals("(0,3) -> (1,3) -> (1,2) -> (2,2) -> (3,2)", result.get(1));
        assertEquals("7", result.get(2));
    }

    @Test
    public void test2() {
        initSetup();
        menu.setNumCols(5);
        menu.setNumRows(4);
        menu.setEntrances(new Integer[][] { { 0, 0 }, { 1, 1 }, { 3, 3 } });
        menu.setMatrix(new int[][] {
                { 1, 2, 4, 2, 1 },
                { 3, -1, 2, 1, 3 },
                { -1, 5, 3, -1, 2 },
                { 2, 1, 1, 0, -1 } });
        menu.setEndRow(3);
        menu.setEndCol(2);
        List<String> result = menu.solve();

        assertEquals("2", result.get(0));
        assertEquals("(3,3) -> (3,2)", result.get(1));
        assertEquals("1", result.get(2));
    }

    @Test
    public void test3() {
        initSetup();
        menu.setNumCols(3);
        menu.setNumRows(3);
        menu.setEntrances(new Integer[][] { { 0, 0 } });
        menu.setMatrix(new int[][] {
                { 1, 2, 3 },
                { -1, -1, -1 },
                { 3, 2, 1 } });
        menu.setEndRow(2);
        menu.setEndCol(2);
        List<String> result = menu.solve();

        assertEquals("-1", result.get(0));
    }

    @Test
    public void test4() {
        initSetup();
        menu.setNumCols(3);
        menu.setNumRows(3);
        menu.setEntrances(new Integer[][] { { 0, 0 }, { 2, 0 } });
        menu.setMatrix(new int[][] {
                { 1, 2, 3 },
                { -1, -1, 2 },
                { 3, -1, 1 } });
        menu.setEndRow(2);
        menu.setEndCol(2);
        List<String> result = menu.solve();

        assertEquals("1", result.get(0));
        assertEquals("(0,0) -> (0,1) -> (0,2) -> (1,2) -> (2,2)", result.get(1));
        assertEquals("8", result.get(2));
    }

    @Test
    public void test5() {
        initSetup();
        menu.setNumCols(4);
        menu.setNumRows(4);
        menu.setEntrances(new Integer[][] { { 0, 0 }, { 0, 2 }, { 3, 0 } });
        menu.setMatrix(new int[][] {
                { 1, 2, 4, 2 },
                { 3, -1, 2, 1 },
                { -1, -1, 3, 8 },
                { 2, -1, 1, 0 } });
        menu.setEndRow(3);
        menu.setEndCol(2);
        List<String> result = menu.solve();

        assertEquals("2", result.get(0));
        assertEquals("(0,2) -> (1,2) -> (2,2) -> (3,2)", result.get(1));
        assertEquals("6", result.get(2));
    }

    @Test
    public void test6() {
        initSetup();
        menu.setNumCols(3);
        menu.setNumRows(3);
        menu.setEntrances(new Integer[][] { { 0, 0 } });
        menu.setMatrix(new int[][] {
                { 1, -1, 3 },
                { 2, -1, -1 },
                { 3, 2, -1 } });
        menu.setEndRow(2);
        menu.setEndCol(2);
        List<String> result = menu.solve();

        assertEquals("-1", result.get(0));
    }

    @Test
    public void test7() {
        initSetup();
        menu.setNumCols(3);
        menu.setNumRows(3);
        menu.setEntrances(new Integer[][] { { 1, 0 }, { 1, 2 } });
        menu.setMatrix(new int[][] {
                { -1, -1, -1 },
                { -1, -1, -1 },
                { -1, -1, -1 } });
        menu.setEndRow(2);
        menu.setEndCol(2);
        List<String> result = menu.solve();

        assertEquals("-1", result.get(0));
    }

}
