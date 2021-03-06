package org.jarb.populator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.easymock.EasyMock;
import org.jarb.populator.condition.ConditionChecker;
import org.jarb.populator.condition.ResourceExistsConditionChecker;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

public class ConditionalDatabasePopulatorTest {
    private DatabasePopulator populatorMock;

    @Before
    public void setUp() {
        populatorMock = EasyMock.createMock(DatabasePopulator.class);
    }

    /**
     * Ensure that we populate whenever the state is supported.
     */
    @Test
    public void testSupported() throws Exception {
        final ConditionChecker existingResourceExists = new ResourceExistsConditionChecker(new ClassPathResource("create-schema.sql"));
        assertTrue(existingResourceExists.checkCondition().isSatisfied()); // Resource 'create-schema.sql' exists on our classpath
        
        ConditionalDatabasePopulator conditionalPopulator = new ConditionalDatabasePopulator(populatorMock, existingResourceExists);

        populatorMock.populate();
        EasyMock.expectLastCall();
        EasyMock.replay(populatorMock);

        conditionalPopulator.populate();

        EasyMock.verify(populatorMock);
    }

    /**
     * Whenever our state is unsupported, no populate should happen.
     */
    @Test
    public void testUnsupported() throws Exception {
        final ConditionChecker unknownResourceDoesNotExists = new ResourceExistsConditionChecker(new ClassPathResource("unknown.sql"));
        assertFalse(unknownResourceDoesNotExists.checkCondition().isSatisfied()); // Resource 'unknown.sql' does not exist on our classpath
        
        ConditionalDatabasePopulator conditionalPopulator = new ConditionalDatabasePopulator(populatorMock, unknownResourceDoesNotExists);

        EasyMock.replay(populatorMock);

        // Should not perform any populating, because the condition is not satisfied
        conditionalPopulator.populate();

        // We can even recieve an exception about the unsatisfied condition
        conditionalPopulator.setThrowExceptionIfUnsupported(true);
        try {
            conditionalPopulator.populate();
            fail("Expected an illegal state exception because our condition was not satisfied.");
        } catch (IllegalStateException e) {
            assertEquals(
                    "Database populator (" + populatorMock + ") was not executed, because:\n" +
                    " - Resource 'class path resource [unknown.sql]' does not exist."
                    , e.getMessage()
            );
        }

        EasyMock.verify(populatorMock);
    }

}
