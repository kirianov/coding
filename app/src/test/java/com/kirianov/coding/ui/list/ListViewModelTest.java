package com.kirianov.coding.ui.list;

import static org.junit.Assert.*;

import com.kirianov.coding.model.Item;
import com.kirianov.coding.network.responses.ListItemResponse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

@RunWith(JUnit4.class)
public class ListViewModelTest {

    private ListViewModel viewModel;

    @Before
    public void setUp() throws Exception {
        viewModel = new ListViewModel();
    }

    @Test
    public void testMappingNotNull() {
        List<ListItemResponse> response = new ArrayList<>();

        ListItemResponse response1 = new ListItemResponse();
        response1.setDbn("1");
        response1.setSchoolName("2");
        response1.setOverviewParagraph("3");
        response1.setPhoneNumber("4");
        response1.setLocation("5");
        response1.setLatitude("6");
        response1.setLongitude("7");
        response.add(response1);

        ListItemResponse response2 = new ListItemResponse();
        response2.setDbn("11");
        response2.setSchoolName("12");
        response2.setOverviewParagraph("13");
        response2.setPhoneNumber("14");
        response2.setLocation("15");
        response2.setLatitude("16");
        response2.setLongitude("17");
        response.add(response2);

        List<Item> expected = new ArrayList<>();

        Item expected1 = new Item();
        expected1.setDbn("1");
        expected1.setSchoolName("2");
        expected1.setOverviewParagraph("3");
        expected1.setPhoneNumber("4");
        expected1.setLocation("5");
        expected1.setLatitude("6");
        expected1.setLongitude("7");
        expected.add(expected1);

        Item expected2 = new Item();
        expected2.setDbn("11");
        expected2.setSchoolName("12");
        expected2.setOverviewParagraph("13");
        expected2.setPhoneNumber("14");
        expected2.setLocation("15");
        expected2.setLatitude("16");
        expected2.setLongitude("17");
        expected.add(expected2);

        List<Item> actual = viewModel.mapping(response);

        assertNotNull(actual);
        assertEquals("Not equal size: ", expected.size(), actual.size());
        for (int i = 0; i < actual.size(); i++) {
            assertEquals("Not equal item at " + i + " position: ", expected.get(i), actual.get(i));
            // assertEquals("Not equal item at " + i + " position: ", expected.get(i).toString(), actual.get(i).toString());
        }
    }

    @After
    public void destroy() throws Exception {
        viewModel = null;
    }
}
