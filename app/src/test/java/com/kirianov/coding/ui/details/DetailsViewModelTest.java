package com.kirianov.coding.ui.details;

import static org.junit.Assert.*;

import com.kirianov.coding.model.ItemDetails;
import com.kirianov.coding.network.responses.DetailsItemResponse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class DetailsViewModelTest {

    private DetailsViewModel viewModel;

    @Before
    public void setUp() throws Exception {
        viewModel = new DetailsViewModel();
    }

    @Test
    public void testMappingNotNull() {
        DetailsItemResponse response = new DetailsItemResponse();
        response.setDbn("1");
        response.setSchoolName("2");
        response.setSatMathScore("3");
        response.setSatReadingScore("4");
        response.setSatWritingScore("5");
        response.setNumSatTakers("6");

        ItemDetails expected = new ItemDetails();
        expected.setDbn("1");
        expected.setSchoolName("2");
        expected.setSatMathScore("3");
        expected.setSatReadingScore("4");
        expected.setSatWritingScore("5");
        expected.setNumSatTakers("6");

        ItemDetails actual = viewModel.mapping(response);

        assertNotNull(actual);
        assertEquals("Not equal item: ", expected, actual);
//        assertEquals("Not equal item: ", expected.toString(), actual.toString());
    }

    @After
    public void destroy() throws Exception {
        viewModel = null;
    }
}
