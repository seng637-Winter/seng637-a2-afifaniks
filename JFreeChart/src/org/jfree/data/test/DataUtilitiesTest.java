package org.jfree.data.test;

import org.jfree.data.DataUtilities;
import org.jfree.data.Values2D;
import org.jfree.data.KeyedValues;
import org.jfree.data.DefaultKeyedValues;
import static org.junit.Assert.*;

import org.junit.*;
import org.jmock.*;

public class DataUtilitiesTest {
	/**
	 * The mocking context used for creating mock objects in this test suite.
	 */
    private Mockery mockingContext;
    /**
     * A mock object representing an instance of Values2D class.
     */
    private Values2D values2dMock;
    /**
     * A mock object representing an instance of KeyedValues class.
     */
    private KeyedValues keyedValueMock;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        this.mockingContext = new Mockery();
        this.values2dMock = this.mockingContext.mock(Values2D.class);
        this.keyedValueMock = this.mockingContext.mock(KeyedValues.class);
    }
	    
    /**
     * Test case to verify the calculation of the column total for the first column.
     * It mocks the necessary behavior of the {@link Values2D} object and tests the
     * {@link DataUtilities#calculateColumnTotal(Values2D, int)} method.
     */
    @Test
    public void testCalculateColumnTotalOfFirstColumn() {
        mockingContext.checking(new Expectations() {{
            one(values2dMock).getRowCount();
            will(returnValue(3));  // Assuming there are 3 rows
            one(values2dMock).getValue(0, 0);
            will(returnValue(4));
            one(values2dMock).getValue(1, 0);
            will(returnValue(7));
            one(values2dMock).getValue(2, 0);
            will(returnValue(9));
        }});
        
        double expectedValue = 20.0;
        // Calculate column total by calling the function
        double result = DataUtilities.calculateColumnTotal(values2dMock, 0);
        assertEquals("Column total should be 20.0", expectedValue, result, .000000001d);
    }
    
    /**
     * Test case to verify the calculation of the total for the second column using the
     * {@link DataUtilities#calculateColumnTotal(Values2D, int)} method.
     */
    @Test
    public void testCalculateColumnTotalOfSecondColumn() {
        mockingContext.checking(new Expectations() {
            {
                one(values2dMock).getRowCount();
                will(returnValue(3));
                one(values2dMock).getValue(0, 1);
                will(returnValue(7.0));
                one(values2dMock).getValue(1, 1);
                will(returnValue(4.0));
                one(values2dMock).getValue(2, 1);
                will(returnValue(10.5));
            }
        });

        double expectedValue = 21.5;
        // Calculate column total by calling the function
        double result = DataUtilities.calculateColumnTotal(values2dMock, 1);
        assertEquals("Column total should be 21.5", expectedValue, result, .000000001d);
    }

    /**
     * Test case to verify the calculation of the total for a column where a positive boundary value exists.
     * It includes a scenario where a positive boundary value (Double.MAX_VALUE) exists in the Values2D object.
     */
    @Test
    public void testCalculateColumnTotalWhenPositiveBoundaryExists() {
        mockingContext.checking(new Expectations() {
            {
                one(values2dMock).getRowCount();
                will(returnValue(3));
                one(values2dMock).getValue(0, 0);
                will(returnValue(Double.MAX_VALUE));
                one(values2dMock).getValue(1, 0);
                will(returnValue(2.5));
                one(values2dMock).getValue(2, 0);
                will(returnValue(1.5));
            }
        });
        
        // Total expected column value
        double expectedValue = Double.MAX_VALUE + 2.5 + 1.5;
        // Actual result calculated by the targeted function
        double result = DataUtilities.calculateColumnTotal(values2dMock, 0);
        assertEquals("Should return " + expectedValue, expectedValue, result, .000000001d);
    }

    /**
     * Test case to verify the calculation of the total for a column where a positive boundary value exists.
     * It includes a scenario where a negative value 2e-51 exists in the Values2D object.
     */
    @Test
    public void testCalculateColumnTotalWhenNegativeExists() {
        mockingContext.checking(new Expectations() {
            {
                one(values2dMock).getRowCount();
                will(returnValue(3));
                one(values2dMock).getValue(0, 0);
                will(returnValue(Math.pow(2, -51)));
                one(values2dMock).getValue(1, 0);
                will(returnValue(12.5));
                one(values2dMock).getValue(2, 0);
                will(returnValue(-12.5));
            }
        });

        double result = DataUtilities.calculateColumnTotal(values2dMock, 0);
        assertEquals(Math.pow(2, -51), result, .000000001d);
    }

    /**
     * Test case to verify the calculation of the total for a column when index reaches the integer positive boundary.
     * It includes a scenario where a positive boundary value (Double.MAX_VALUE) exists in the Values2D object.
     */
    @Test
    public void testCalculateColumnTotalWhenTheIndexBecomesTooLarge() {
        mockingContext.checking(new Expectations() {
            {
                one(values2dMock).getRowCount();
                will(returnValue(3));
                one(values2dMock).getValue(0, Integer.MAX_VALUE);
                will(returnValue(7.5));
                one(values2dMock).getValue(1, Integer.MAX_VALUE);
                will(returnValue(2.5));
                one(values2dMock).getValue(2, Integer.MAX_VALUE);
                will(returnValue(5.0));
            }
        });
        
        double expectedValue = 15.0;
        double result = DataUtilities.calculateColumnTotal(values2dMock, Integer.MAX_VALUE);
        assertEquals("Should calculate column total correctly with max index", expectedValue, result, .000000001d);
    }

    /**
     * Test case to verify the calculation of the total for the first row using the 
     * {@link DataUtilities#calculateRowTotal(Values2D, int)} method.
     * This test sets up the mocking context to simulate an instance of {@link Values2D} class
     * with three columns and verifies that the method calculates the total correctly for the first row.
     */
    @Test
    public void testCalculateRowTotalOfFirstRow() {
        mockingContext.checking(new Expectations() {
            {
                one(values2dMock).getColumnCount();
                will(returnValue(3));
                one(values2dMock).getValue(0, 0);
                will(returnValue(5));
                one(values2dMock).getValue(0, 1);
                will(returnValue(5));
                one(values2dMock).getValue(0, 2);
                will(returnValue(20));
            }
        });

        // Expected value after the calculation
        double expectedValue = 30;
        // Actual value after the calculation
        double result = DataUtilities.calculateRowTotal(values2dMock, 0);     
        assertEquals("Should return " + expectedValue, expectedValue, result, .000000001d);
    }
    
    /**
     * Test case to verify the calculation of the total for the second row. The test assumes
     * that there exists three columns and verifies that the method calculates the total 
     * correctly for the second row.
     */
    @Test
    public void testCalculateRowTotalOfSecondRow() {
        mockingContext.checking(new Expectations() {
            {
                one(values2dMock).getColumnCount();
                will(returnValue(3));
                one(values2dMock).getValue(1, 0);
                will(returnValue(7.5));
                one(values2dMock).getValue(1, 1);
                will(returnValue(7.5));
                one(values2dMock).getValue(1, 2);
                will(returnValue(5.0));
            }
        });
        
        // Expected value after the row calculation
        double expectedValue = 20.0;
        // Actual result produced by the method
        double result = DataUtilities.calculateRowTotal(values2dMock, 1);
        assertEquals("Should return " + expectedValue, expectedValue, result, .000000001d);
    }
    
    /**
     * Test case to verify the calculation of the total for the second row. The test assumes
     * that there exists three columns and verifies that the method calculates the total 
     * correctly for the last row.
     */
    @Test
    public void testCalculateRowTotalOfLastRow() {
        mockingContext.checking(new Expectations() {
            {
                one(values2dMock).getColumnCount();
                will(returnValue(3));
                one(values2dMock).getValue(2, 0);
                will(returnValue(5.0));
                one(values2dMock).getValue(2, 1);
                will(returnValue(2.5));
                one(values2dMock).getValue(2, 2);
                will(returnValue(5.0));
            }
        });
        
        double expectedValue = 12.5;
        double result = DataUtilities.calculateRowTotal(values2dMock, 2);
        assertEquals("Should return " + expectedValue, expectedValue, result, .000000001d);
    }
    
    /**
     * Test case to verify the calculation of the total for a row where the sum of the row is zero, 
     * using the {@link DataUtilities#calculateRowTotal(Values2D, int)} method.
     */
    @Test
    public void testCalculateRowTotalWhenSumOfARowIsZero() {
        mockingContext.checking(new Expectations() {
            {
                one(values2dMock).getColumnCount();
                will(returnValue(3));
                one(values2dMock).getValue(0, 0);
                will(returnValue(7.5));
                one(values2dMock).getValue(0, 1);
                will(returnValue(2.5));
                one(values2dMock).getValue(0, 2);
                will(returnValue(-10));
            }
        });
        double result = DataUtilities.calculateRowTotal(values2dMock, 0);
        assertEquals("Should return 0.0", 0, result, .000000001d);
    }


    /**
     * Test case to verify the calculation of the total for a row where a positive boundary value exists.
     * It includes a scenario where a positive boundary value (Double.MAX_VALUE) exists in the Values2D object.
     */
    @Test
    public void testCalculateRowTotalWhenPositiveBoundaryExists() {
        mockingContext.checking(new Expectations() {
            {
                one(values2dMock).getColumnCount();
                will(returnValue(3));
                one(values2dMock).getValue(0, 0);
                will(returnValue(Double.MAX_VALUE));
                one(values2dMock).getValue(0, 1);
                will(returnValue(2.5));
                one(values2dMock).getValue(0, 2);
                will(returnValue(-2.5));
            }
        });
        
        // Expected value is negative boundary value
        double expectedValue = Double.MAX_VALUE;
        // Actual value computed by the function
        double result = DataUtilities.calculateRowTotal(values2dMock, 0);
        assertEquals("Should return " + expectedValue, expectedValue, result, .000000001d);
    }
    
    /**
     * Test case to verify the calculation of the total for a row where a negative boundary value exists.
     * It includes a scenario where a negative boundary value (Double.MIN_VALUE) exists in the Values2D object.
     */
    @Test
    public void testCalculateRowTotalWhenNegativeBoundaryExists() {
        mockingContext.checking(new Expectations() {
            {
                one(values2dMock).getColumnCount();
                will(returnValue(3));
                one(values2dMock).getValue(0, 0);
                will(returnValue(Double.MIN_VALUE));
                one(values2dMock).getValue(0, 1);
                will(returnValue(2.5));
                one(values2dMock).getValue(0, 2);
                will(returnValue(-2.5));
            }
        });
        
        double expectedValue = Double.MIN_VALUE;
        double result = DataUtilities.calculateRowTotal(values2dMock, 0);
        assertEquals("Should return " + expectedValue, expectedValue, result, .000000001d);
    }
    
    /**
     * Test case to verify the behavior of the {@link DataUtilities#createNumberArray(double[])} method
     * when provided with valid data.
     * This test method creates a one-dimensional array of doubles as test parameters and calls the
     * {@link DataUtilities#createNumberArray(double[])} method with these parameters. It expects that
     * the method correctly converts each double value into a {@link Number} object, resulting in a
     * one-dimensional array of {@link Number}s. The test asserts that each element of the expected
     * and actual arrays match.
     */
    @Test
    public void testCreateNumberArrayWithValidData() {
    	double[] testParameters = new double[]{1.0, 2.0, 3.0}; 
        Number[] actualArray = DataUtilities.createNumberArray(testParameters);
        
        // Expected Number[] array
        Number[] expected = new Number[]{1.0, 2.0, 3.0};
        assertArrayEquals("Should return a valid Number[] array containing the values", expected, actualArray);
    }
    
    /**
     * This test method creates a one-dimensional array of doubles as test parameters, including a value
     * representing the positive boundary (Double.MAX_VALUE). The test asserts that each
     * element of the expected and actual arrays match.
     */
    @Test
    public void testCreateNumberArrayWhenPositiveBoundaryExists() {
    	double[] testParameters = new double[]{1.0, 2.0, Double.MAX_VALUE}; 
        Number[] actualArray = DataUtilities.createNumberArray(testParameters);
        
        // Expected Number[] array including Double.MAX_VALUE
        Number[] expected = new Number[]{1.0, 2.0, Double.MAX_VALUE};
        assertArrayEquals("Should return a valid Number[] array containing the values", expected, actualArray);
    }
    
    /**
     * This test method creates a one-dimensional array of doubles as test parameters, including a value
     * representing the negative boundary (Double.MIN_VALUE). The test asserts that each
     * element of the expected and actual arrays match.
     */
    @Test
    public void testCreateNumberArrayWhenNegativeBoundaryExists() {
    	double[] testParameters = new double[]{1.0, 2.0, Double.MIN_VALUE}; 
        Number[] actualArray = DataUtilities.createNumberArray(testParameters);
        
        Number[] expected = new Number[]{1.0, 2.0, Double.MIN_VALUE};
        assertArrayEquals("Should return a valid Number[] array containing the values", expected, actualArray);
    }
    
    /**
	 * This test method passes null as the test parameter to the {@link DataUtilities#createNumberArray(double[])}
	 * method and expects that it throws an {@link IllegalArgumentException}. It verifies that the method correctly
	 * handles the null input and throws the expected exception.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCreateNumberForIllegalArguments() {
		double testParameter[] = null;
		DataUtilities.createNumberArray(testParameter);
    }
    
    /**
     * Test case to verify the behavior of the {@link DataUtilities#createNumberArray2D(double[][] data)} method
     * when provided with valid data.
     * This test method creates a two-dimensional array of doubles as test parameters, and then expects
     * that the {@link DataUtilities#createNumberArray2D(double[][] data)} method converts it into a two-dimensional
     * array of {@link Number}s. The test asserts that each element of the expected and output arrays match.    
     */
    @Test
    public void testCreateNumberArray2DWithValidData() {
		double[][] testParameters = {
				{1.0, 2.0, 3.0, 4.0, 5.0}, 
				{5.0, 6.0, 7.0, 8.0, 9.0, 10.0}
			};
		
		// Expected 2D Number[][] array
		Number[][] expectedArray = {
				{1.0, 2.0, 3.0, 4.0, 5.0}, 
				{5.0, 6.0, 7.0, 8.0, 9.0, 10.0}
			};
		Number[][] output = DataUtilities.createNumberArray2D(testParameters);
		
		assertArrayEquals("Each of the elements should match.", expectedArray, output);
    }
    
    /**
     * Test case to verify the behavior of the {@link DataUtilities#createNumberArray2D(double[][] data)} method
     * when the parameter contains a positive boundary value.   
     */
    @Test
    public void testCreateNumberArray2DWhenPositiveBoundaryExists() {
		double[][] testParameters = {
				{1.0, 2.0, Double.MAX_VALUE, 4.0, 5.0}, 
				{5.0, 6.0, 7.0, 8.0, Double.MAX_VALUE, 10.0}
			};
		
		// Expected Number[][] array including double boundary value
		Number[][] expectedArray = {
				{1.0, 2.0, Double.MAX_VALUE, 4.0, 5.0}, 
				{5.0, 6.0, 7.0, 8.0, Double.MAX_VALUE, 10.0}
			};
		Number[][] output = DataUtilities.createNumberArray2D(testParameters);
		
		assertArrayEquals("Each of the elements should match.", expectedArray, output);
    }
    
    /**
     * Test case to verify the behavior of the {@link DataUtilities#createNumberArray2D(double[][] data)} method
     * when the parameter contains a negative boundary value.   
     */
    @Test
    public void testCreateNumberArray2DWhenNegativeBoundaryExists() {
		double[][] testParameters = {
				{1.0, 2.0, Double.MIN_VALUE, 4.0, 5.0}, 
				{5.0, 6.0, 7.0, 8.0, Double.MIN_VALUE, 10.0}
			};
		
		// Expected Number[][] array including double boundary value
		Number[][] expectedArray = {
				{1.0, 2.0, Double.MIN_VALUE, 4.0, 5.0}, 
				{5.0, 6.0, 7.0, 8.0, Double.MIN_VALUE, 10.0}
			};
		Number[][] output = DataUtilities.createNumberArray2D(testParameters);
		
		assertArrayEquals("Each of the elements should match.", expectedArray, output);
    }
    
    /**
	 * This test method passes null as the test parameter to the {@link DataUtilities#createNumberArray2D(double[][])}
	 * method and expects that it throws an {@link IllegalArgumentException}. It verifies that the method correctly
	 * handles the null input and throws the expected exception.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCreateNumber2DForIllegalArguments() {
		double[][] testParameters = null;
		// Expects to throw exception
		DataUtilities.createNumberArray2D(testParameters);
    }
    
    /**
     * Test case to verify the behavior of the {@link DataUtilities#getCumulativePercentages(KeyedValues)} method
     * when provided with an empty {@link KeyedValues} object. The test case expects that calling 
     * {@link DataUtilities#getCumulativePercentages(KeyedValues)} on this object returns another
     * empty {@link KeyedValues} object. It verifies that the method handles the empty input correctly.
     */
    @Test
    public void testGetCumulativePercentagesWithEmptyKeyedValueObject() {
    	// Create a mock when KeyedValues has no item or 0 itemCount
    	mockingContext.checking(new Expectations() {
            {
                allowing(keyedValueMock).getItemCount();
                will(returnValue(0));
            }
        });

    	// Calculate cumulative percentage
        KeyedValues cumPercentage = DataUtilities.getCumulativePercentages(keyedValueMock);
        KeyedValues expected = new DefaultKeyedValues();
        assertEquals(
                "Calling getCumulativePercentage on an empty KeyedValues, should return an empty KeyedValues object",
                expected, cumPercentage);
    }

    /**
     * This test case ensures that the method works as expected when a KeyedValue with only item is passed.
     */
    @Test
    public void testGetCumulativePercentagesWithOneRowKeyedValue() {
        mockingContext.checking(new Expectations() {
            {
                allowing(keyedValueMock).getItemCount();
                will(returnValue(1));
                allowing(keyedValueMock).getKey(0);
                will(returnValue(0));
                allowing(keyedValueMock).getValue(0);
                will(returnValue(1));
            }
        });        

        KeyedValues result = DataUtilities.getCumulativePercentages(keyedValueMock);
        assertEquals("The Cumulative Percentage of only one item with value 1 should be 1.0", 1.0, result.getValue(0));
    }

    /**
     * This test case verifies that the method works properly when one value in the KeyedValue object is 0.
     */
    @Test
    public void testGetCumulativePercentagesWithOneZeroValueItem() {
        mockingContext.checking(new Expectations() {
            {
                allowing(keyedValueMock).getItemCount();
                will(returnValue(1));
                allowing(keyedValueMock).getKey(0);
                will(returnValue(0));
                allowing(keyedValueMock).getValue(0);
                will(returnValue(0));
            }
        });

        KeyedValues result = DataUtilities.getCumulativePercentages(keyedValueMock);
        assertEquals("The cumulative percentage of 0 in data should be NaN", Double.NaN,
                result.getValue(0));
    }

    /**
     * This test case verifies that the method works properly when one value in the KeyedValue object is null.
     */
    @Test
    public void testGetCumulativePercentagesWhenNullExists() {
    	// Create mock for the case when KeyedValue has null item value
        mockingContext.checking(new Expectations() {
            {
                allowing(keyedValueMock).getItemCount();
                will(returnValue(1));
                allowing(keyedValueMock).getKey(0);
                will(returnValue(0));
                allowing(keyedValueMock).getValue(0);
                will(returnValue(null));
            }
        });

        KeyedValues result = DataUtilities.getCumulativePercentages(keyedValueMock);
        assertEquals("The cumulative percentage of null should be NaN", Double.NaN, result.getValue(0));
    }

    /**
     * This test case verifies that the method works properly when the KeyedValues object has multiple
     * item/values associated to it.
     */
    @Test
    public void testGetCumulativePercentagesWhenMultipleKeyValuedItemsExist() {
        mockingContext.checking(new Expectations() {
            {
                allowing(keyedValueMock).getItemCount();
                will(returnValue(3));
                allowing(keyedValueMock).getKey(0);
                will(returnValue(0));
                allowing(keyedValueMock).getKey(1);
                will(returnValue(1));
                allowing(keyedValueMock).getKey(2);
                will(returnValue(2));
                allowing(keyedValueMock).getValue(0);
                will(returnValue(3));
                allowing(keyedValueMock).getValue(1);
                will(returnValue(2));
                allowing(keyedValueMock).getValue(2);
                will(returnValue(1));
            }
        });

        KeyedValues result = DataUtilities.getCumulativePercentages(keyedValueMock);

        assertEquals("The cumulative percentage of 3 should be 3/6", 3/6,
                result.getValue(0).doubleValue(), 0.000000001d);
        assertEquals("The cumulative percentage of 2 should be (3 + 2)/6", 5/6, result.getValue(1).doubleValue(),
                0.000000001d);
        assertEquals("The cumulative percentage of 1 should be (3 + 2 + 1)/6", 6/6, result.getValue(1).doubleValue(),
                0.000000001d);
    }
    
    /**
     * Tests if the {@link DataUtilities#getCumulativePercentages(double[])} method
     * correctly handles null input by throwing an exception of {@link IllegalArgumentException}.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testGetCumulativePercentagesWithNullData() {
    	// Expects the method to throw error
        DataUtilities.getCumulativePercentages(null);
    }

}