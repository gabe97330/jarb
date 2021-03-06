package org.jarb.populator.excel.workbook;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class WorkbookTest {
    private Workbook workbook;

    @Before
    public void setUp() {
        workbook = new Workbook();
    }

    @Test
    public void testAddAndReplaceSheet() {
        assertEquals(0, workbook.getSheetCount());
        final Sheet sheet = workbook.createSheet("test");
        assertEquals(1, workbook.getSheetCount());
        assertEquals(sheet, workbook.getSheet("test"));
        List<Sheet> sheets = workbook.getSheets();
        assertEquals(Arrays.asList(sheet), sheets);
        final Sheet another = workbook.createSheet("another");
        assertEquals(another, workbook.getSheet("another"));
        assertEquals(2, workbook.getSheetCount());
        workbook.removeSheet("test");
        assertEquals(1, workbook.getSheetCount());
        workbook.removeSheet("another");
        assertEquals(0, workbook.getSheetCount());
    }

    @Test
    public void testFindByName() {
        final Sheet sheet = workbook.createSheet("test");
        final Sheet another = workbook.createSheet("another");
        assertEquals(sheet, workbook.getSheet("test"));
        assertEquals(another, workbook.getSheet("another"));
        assertNull(workbook.getSheet("unknown"));
    }

}
