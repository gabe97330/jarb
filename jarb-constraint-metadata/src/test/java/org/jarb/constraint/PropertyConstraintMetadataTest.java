package org.jarb.constraint;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * Test that setters validate the provided values.
 * @author Jeroen van Schagen
 * @since 03-06-2011
 */
public class PropertyConstraintMetadataTest {
    private MutablePropertyConstraintMetadata<String> description;

    @Before
    public void setUp() {
        description = new MutablePropertyConstraintMetadata<String>("name", String.class);
    }

    @Test
    public void testModifyLength() {
        description.setMinimumLength(4);
        description.setMaximumLength(42);
        assertEquals(Integer.valueOf(4), description.getMinimumLength());
        assertEquals(Integer.valueOf(42), description.getMaximumLength());
    }

    @Test(expected = IllegalStateException.class)
    public void testNegativeMinimumLength() {
        description.setMinimumLength(-1);
    }

    @Test(expected = IllegalStateException.class)
    public void testNegativeMaximumLength() {
        description.setMaximumLength(-1);
    }

    @Test(expected = IllegalStateException.class)
    public void testMinimLengthGreaterThanMaximum() {
        description.setMaximumLength(24);
        description.setMinimumLength(42);
    }

    @Test
    public void testModifyFractionLength() {
        description.setFractionLength(4);
        assertEquals(Integer.valueOf(4), description.getFractionLength());
    }

    @Test(expected = IllegalStateException.class)
    public void testnegativeFractionLength() {
        description.setFractionLength(-1);
    }
}
