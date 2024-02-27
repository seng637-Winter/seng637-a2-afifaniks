
package org.jfree.data.test;

import static org.junit.Assert.*;
import org.jfree.data.Range;
import org.junit.*;


public class RangeTest {
	private Range exampleRange;
    private Range anotherRange;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        // Initialize two different range objects before each test method
        this.exampleRange = new Range(-10, 10);
        this.anotherRange = new Range(0.0, 5.0);
    }

    /*
     * Test case for getCentralValue
     */
    @Test
    public void testGetCentralValueWithPositiveRange() {
        // Create a range with positive bounds
        Range range = new Range(2.0, 10.0);
        
        // Check the central value of the range
        assertEquals("The central value should be 6.0", 6.0, range.getCentralValue(), 0.0001);
    }

    @Test
    public void testGetCentralValueWithNegativeRange() {
        // Create a range with negative bounds
        Range range = new Range(-10.0, -2.0);
        
        // Check the central value of the range
        assertEquals("The central value should be -6.0", -6.0, range.getCentralValue(), 0.0001);
    }

    @Test
    public void testGetCentralValueWithMixedRange() {
        // Create a range with one negative and one positive bound
        Range range = new Range(-4.0, 6.0);
        
        // Check the central value of the range
        assertEquals("The central value should be 1.0", 1.0, range.getCentralValue(), 0.0001);
    }

    @Test
    public void testGetCentralValueWithZeroRange() {
        // Create a range where both bounds are the same
        Range range = new Range(5.0, 5.0);
        
        // Check the central value of the range
        assertEquals("The central value should be 5.0", 5.0, range.getCentralValue(), 0.0001);
    }

    @Test
    public void testGetCentralValueWithLargeRange() {
        // Create a range with a large span
        Range range = new Range(100.0, 300.0);
        
        // Check the central value of the range
        assertEquals("The central value should be 200.0", 200.0, range.getCentralValue(), 0.0001);
    }
    
    @Test
    public void testGetCentralValueWithPositiveBoundaryRange() {
        // Create a range with positive boundary
        Range range = new Range(10.0, Double.MAX_VALUE);       
        
        Double expectedValue = 8.988465674311579E307;
        // Check the central value of the range
        assertEquals("The central value should be 8.988465674311579E307", expectedValue, range.getCentralValue(), 0.0001);
    }
    
    @Test
    public void testGetCentralValueWithNegativeBoundaryRange() {
        // Create a range with negative boundary
        Range range = new Range(Double.MIN_VALUE, Double.MAX_VALUE);       
        
        Double expectedValue = (Double.MIN_VALUE  + Double.MAX_VALUE) / 2;
        // Check the central value of the range
        assertEquals("The central value should be " + expectedValue, expectedValue, range.getCentralValue(), 0.0001);
    }
    
    /*
     * Test case for contains
     */
    @Test
    public void testContainsWithNumberInsideRange() {
        // Create a range
        Range range = new Range(1.0, 5.0);
        
        // Verify that the range contains 3.0
        assertTrue("The range should contain 3.0", range.contains(3.0));
    }

    @Test
    public void testContainsWithNumberOutsideRange() {
        // Create a range
        Range range = new Range(1.0, 5.0);
        
        // Verify that the range does not contain 6.0
        assertFalse("The range should not contain 6.0", range.contains(6.0));
    }

    @Test
    public void testContainsWithNumberEqualToLowerBound() {
        // Create a range
        Range range = new Range(Double.MIN_VALUE, 5.0);
        
        // Verify that the range contains 1.0
        assertTrue("The range should contain 1.0", range.contains(Double.MIN_VALUE));
    }

    @Test
    public void testContainsWithNumberEqualToUpperBound() {
        // Create a range
        Range range = new Range(1.0, Double.MAX_VALUE);
        
        // Verify that the range contains 5.0
        assertTrue("The range should contain 5.0", range.contains(Double.MAX_VALUE));
    }
    
    @Test
    public void testContainsWithRangeHavingEqualToNaN() {
        // Create a range
        Range range = new Range(Double.NaN, Double.NaN);
        
        // Verify that the range contains 5.0
        assertFalse("The range should not contain 1.0", range.contains(1.0));
    }

    @Test
    public void testContainsWithNumberBelowLowerBound() {
        // Create a range
        Range range = new Range(1.0, 5.0);
        
        // Verify that the range does not contain 0.0
        assertFalse("The range should not contain 0.0", range.contains(0.0));
    }

    @Test
    public void testContainsWithNumberAboveUpperBound() {
        // Create a range
        Range range = new Range(1.0, 5.0);
        
        // Verify that the range does not contain 10.0
        assertFalse("The range should not contain 10.0", range.contains(10.0));
    }
    
    /*
     * Test case for intersect
     */
    @Test
    public void testIntersectsWhenRangesOverlap() {
        // Create a range
        Range range = new Range(1.0, 5.0);
        
        // Verify that the range intersects with another range that overlaps it
        assertTrue("The ranges should intersect", range.intersects(3.0, 7.0));
    }

    @Test
    public void testIntersectsWhenRangesDoNotOverlap() {
        // Create a range
        Range range = new Range(1.0, 5.0);
        
        // Verify that the range does not intersect with a range that is entirely greater
        assertFalse("The ranges should not intersect", range.intersects(6.0, 10.0));
    }

    @Test
    public void testIntersectsWhenRangesTouchAtStart() {
        // Create a range
        Range range = new Range(1.0, 5.0);
        
        // Verify that the range intersects with another range that touches its start
        assertTrue("The ranges should intersect", range.intersects(0.0, 1.0));
    }

    @Test
    public void testIntersectsWhenRangesTouchAtEnd() {
        // Create a range
        Range range = new Range(1.0, 5.0);
        
        // Verify that the range intersects with another range that touches its end
        assertTrue("The ranges should intersect", range.intersects(5.0, 6.0));
    }

    @Test
    public void testIntersectsWhenContainedWithinAnotherRange() {
        // Create a range
        Range range = new Range(2.0, 4.0);
        
        // Verify that the range intersects with another range that completely contains it
        assertTrue("The ranges should intersect", range.intersects(1.0, 5.0));
    }

    @Test
    public void testIntersectsWhenContainingAnotherRange() {
        // Create a range
        Range range = new Range(1.0, 5.0);
        
        // Verify that the range intersects with another range that is completely within it
        assertTrue("The ranges should intersect", range.intersects(2.0, 4.0));
    }

    @Test
    public void testIntersectsWhenRangesAreIdentical() {
        // Create a range
        Range range = new Range(1.0, 5.0);
        
        // Verify that the range intersects with another range that is identical to it
        assertTrue("The ranges should intersect", range.intersects(1.0, 5.0));
    }

    @Test
    public void testIntersectsWhenRangesAreAdjacentButNotOverlapping() {
        // Create a range
        Range range = new Range(1.0, 5.0);
        
        // Verify that the range does not intersect with a range that is adjacent but not overlapping
        assertFalse("The ranges should not intersect", range.intersects(5.1, 10.0));
    }

    
	/*
	 * Test case for combine
	 */
    @Test
    public void testCombineWithFirstRangeNull() {
        // One range is null and the other is not
        Range range2 = new Range(5, 10);
        
        // Combine ranges
        Range combined = Range.combine(null, range2);
        
        // Verify that the combined range equals the non-null range
        assertNotNull(combined);
        assertEquals(5, combined.getLowerBound(), 0.001);
        assertEquals(10, combined.getUpperBound(), 0.001);
    }

    @Test
    public void testCombineWithSecondRangeNull() {
        // One range is null and the other is not
        Range range1 = new Range(1, 5);
        
        // Combine ranges
        Range combined = Range.combine(range1, null);
        
        // Verify that the combined range equals the non-null range
        assertNotNull(combined);
        assertEquals(1, combined.getLowerBound(), 0.001);
        assertEquals(5, combined.getUpperBound(), 0.001);
    }
    
    @Test
    public void testCombineWithValidRange() {
        // Define two valid ranges
        Range range1 = new Range(1, 5);
        Range range2 = new Range(2, 7);
        
        // Combine ranges
        Range combined = Range.combine(range2, range1);
        
        // Verify that the combined range equals the non-null range
        assertNotNull(combined);
        assertEquals(1, combined.getLowerBound(), 0.001);
        assertEquals(7, combined.getUpperBound(), 0.001);
    }

    @Test
    public void testCombineWithBothRangesNull() {
        Range range1 = null;
        Range range2 = null;
        Range combined = Range.combine(range1, range2);
        assertNull(combined);
    }

    
    /*
     * Test case for equals
     */
    @Test
    public void testEqualsWithIdenticalRanges() {
        // Create two identical ranges
        Range range1 = new Range(1.0, 5.0);
        Range range2 = new Range(1.0, 5.0);
        
        // Test equality
        assertTrue("Identical ranges should be considered equal", range1.equals(range2));
    }

    @Test
    public void testEqualsWithDifferentRanges() {
        // Create two different ranges
        Range range1 = new Range(1.0, 5.0);
        Range range2 = new Range(2.0, 6.0);
        
        // Test inequality
        assertFalse("Different ranges should not be considered equal", range1.equals(range2));
    }

    @Test
    public void testEqualsWithNull() {
        // Create a range
        Range range = new Range(1.0, 5.0);
        
        // Test equality with null
        assertFalse("A range should not be equal to null", range.equals(null));
    }

    @Test
    public void testEqualsWithDifferentObjectType() {
        // Create a range and another object of different type
        Range range = new Range(1.0, 5.0);
        Object other = new Object();
        
        // Test inequality with different object type
        assertFalse("A range should not be equal to an object of a different type", range.equals(other));
    }

    @Test
    public void testEqualsWithSelf() {
        // Create a range
        Range range = new Range(1.0, 5.0);
        
        // Test a range for equality with itself
        assertTrue("A range should be equal to itself", range.equals(range));
    }

    @Test
    public void testEqualsWithDifferentUpperBounds() {
        // Create two ranges with different upper bounds
        Range range1 = new Range(1.0, 5.0);
        Range range2 = new Range(1.0, 6.0);
        
        // Test inequality
        assertFalse("Ranges with different upper bounds should not be considered equal", range1.equals(range2));
    }

    @Test
    public void testEqualsWithDifferentLowerBounds() {
        // Create two ranges with different lower bounds
        Range range1 = new Range(1.0, 5.0);
        Range range2 = new Range(2.0, 5.0);
        
        // Test inequality
        assertFalse("Ranges with different lower bounds should not be considered equal", range1.equals(range2));
    }
    
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }    
    
}
