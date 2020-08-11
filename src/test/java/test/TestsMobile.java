package test;

import base.BaseTestsMobile;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class TestsMobile extends BaseTestsMobile {

    @Test
    //18. Check the number of fares in the histogram bars appearing in desktop is different from the ones that appear on mobile
    public void verifyNumberFaresMobile() throws InterruptedException {
        bookPage = bookPage.goTODailyHistogram();
        Thread.sleep(500);
        assertEquals(bookPage.getHistogramSizeMobile(),10, "Histogram size is not 10");

    }

    @Test
    //19. Check that a button appears below of the fares
    public void verifyButtonBellowFares() throws InterruptedException {
        bookPage = bookPage.goTODailyHistogram();
        Thread.sleep(500);
        assertTrue(bookPage.isVisibleSeMoreLink(), "See More Link is not visible");

    }

}
