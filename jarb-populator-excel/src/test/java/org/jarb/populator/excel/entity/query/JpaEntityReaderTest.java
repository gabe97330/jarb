package org.jarb.populator.excel.entity.query;

import static org.junit.Assert.assertFalse;

import java.io.FileNotFoundException;

import org.jarb.populator.excel.DefaultExcelTestDataCase;
import org.jarb.populator.excel.entity.EntityRegistry;
import org.junit.Before;
import org.junit.Test;

import domain.entities.Project;

public class JpaEntityReaderTest extends DefaultExcelTestDataCase {
    private JpaEntityReader entityReader;

    @Before
    public void setUpReader() {
        entityReader = new JpaEntityReader(getEntityManagerFactory());
    }

    @Test
    public void testFetchAll() throws FileNotFoundException {
        getExcelDataManager().loadWorkbook("src/test/resources/Excel.xls").persist();
        EntityRegistry registry = entityReader.readAll();
        assertFalse(registry.forClass(Project.class).isEmpty());
    }

}
