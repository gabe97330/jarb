package org.jarb.populator.excel.mapping.excelrow;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManagerFactory;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.jarb.populator.excel.metamodel.ClassDefinition;
import org.jarb.populator.excel.metamodel.ClassDefinitionFinder;
import org.jarb.populator.excel.metamodel.WorksheetDefinition;
import org.jarb.populator.excel.metamodel.generator.ClassDefinitionsGenerator;
import org.jarb.populator.excel.workbook.Workbook;
import org.jarb.populator.excel.workbook.reader.PoiExcelParser;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ClassDefinitionFinderTest {

    private Class<?> foreignClass;

    private Workbook excel;
    private Set<ClassDefinition> classDefinitionSet;
    private Set<ClassDefinition> emptyClassDefinitionSet;

    private ClassDefinition customer;
    private ClassDefinition project;

    private ClassPathXmlApplicationContext context;
    private EntityManagerFactory entityManagerFactory;

    @Before
    public void setupClassDefinitionFinder() throws InvalidFormatException, IOException, InstantiationException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, SecurityException, NoSuchMethodException, ClassNotFoundException {
        excel = new PoiExcelParser().parse(new FileInputStream("src/test/resources/ExcelUnitTesting.xls"));

        emptyClassDefinitionSet = new HashSet<ClassDefinition>();

        context = new ClassPathXmlApplicationContext("test-context.xml");
        entityManagerFactory = (EntityManagerFactory) context.getBean("entityManagerFactory");

        foreignClass = domain.entities.Project.class;

        Metamodel metamodel = entityManagerFactory.getMetamodel();
        EntityType<?> persistentEntity = ClassDefinitionsGenerator.getEntityFromMetamodel(domain.entities.Customer.class, metamodel);
        EntityType<?> foreignEntity = ClassDefinitionsGenerator.getEntityFromMetamodel(domain.entities.Project.class, metamodel);

        classDefinitionSet = new HashSet<ClassDefinition>();
        customer = ClassDefinitionsGenerator.createSingleClassDefinitionFromMetamodel(entityManagerFactory, persistentEntity, false);
        customer.setWorksheetDefinition(WorksheetDefinition.analyzeWorksheet(customer, excel));
        project = ClassDefinitionsGenerator.createSingleClassDefinitionFromMetamodel(entityManagerFactory, foreignEntity, false);
        project.setWorksheetDefinition(WorksheetDefinition.analyzeWorksheet(project, excel));
        classDefinitionSet.add(customer);
        classDefinitionSet.add(project);

        //For code coverage purposes:
        Constructor<ClassDefinitionFinder> constructor = ClassDefinitionFinder.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    public void testFindClassDefinitionByPersistentClass() {
        assertEquals(foreignClass, ClassDefinitionFinder.findClassDefinitionByPersistentClass(classDefinitionSet, domain.entities.Project.class)
                .getPersistentClass());
        assertEquals(null, ClassDefinitionFinder.findClassDefinitionByPersistentClass(classDefinitionSet, domain.entities.Employee.class));
        assertEquals(null, ClassDefinitionFinder.findClassDefinitionByPersistentClass(emptyClassDefinitionSet, domain.entities.Project.class));
    }
}