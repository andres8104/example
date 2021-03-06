package test;

import base.BaseTestsDesktop;
import org.testng.annotations.Test;
import pages.BookPage;
import pages.FlightPage;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class TestsDesktop extends BaseTestsDesktop {

    @Test
    //2. Go to the top of the module
    public void goTopModule() {
       bookPage =  bookPage.goTOMonthlySection();
       assertTrue(bookPage.isVisibleMonthlySection(),
               "Monthly section does not show");
    }

    @Test
    //3. Look at the line after the module heading
    public void verifyLineAfterHeading(){
        bookPage =  bookPage.goTOFaresSection();
        String originAirportFromUrl = bookPage.getCurrentUrl().split("-")[4];
        String arrivalAirportFromUrl = bookPage.getCurrentUrl().split("-")[6];

        assertTrue(originAirportFromUrl.equalsIgnoreCase(bookPage.getOriginAirportSelected()),
                "Origin airport from url and selected do not match");
        assertTrue(arrivalAirportFromUrl.equalsIgnoreCase(bookPage.getArrivalAirportSelected()),
                "Arrival airport from url and selected do not match");
    }

    //@Test
    //4. Select a valid airport code from the From (Origin) dropdown
    public void selectFromAirport(){

    }

    //@Test
    //5. Check for the skeleton screen
    public void verifySkeleton(){

    }

    @Test
    //6. Look at the monthly carousel section of the module
    public void verifyMonthlyCarousel(){
        bookPage =  bookPage.goTOMonthlySection();
        LocalDate currentDate = LocalDate.now();
        Month currentMonth = currentDate.getMonth();
        int currentYear = currentDate.getYear();
        /**
         * Verifying first card in carousel
         * */
         assertTrue(bookPage.isVisibleCardInCarouselPosition(0),
                 "First card of carousel is not visible");
        String dateCompare = currentMonth.toString() + Integer.toString(currentYear);
        assertTrue(dateCompare.equalsIgnoreCase(bookPage.getMonthOfCardInCarousel(0)));

        /**
         * Verifying Second card in carousel
         * */
        Month nextMonth1 = getNextMonth(currentMonth);
        if (currentMonth.getValue() > nextMonth1.getValue()) {
            currentYear++;
        }
        assertTrue(bookPage.isVisibleCardInCarouselPosition(1),
                "Second card of carousel is not visible");
        dateCompare = nextMonth1.toString() + currentYear;
        assertTrue(dateCompare.equalsIgnoreCase(bookPage.getMonthOfCardInCarousel(1)));

        /**
         * Verifying Third card in carousel
         * */
        Month nextMonth2 = getNextMonth(nextMonth1);
        if (nextMonth1.getValue() > nextMonth2.getValue()) {
            currentYear++;
        }
        assertTrue(bookPage.isVisibleCardInCarouselPosition(2),
                "Third card of carousel is not visible");
        dateCompare = nextMonth2.toString() + currentYear;
        assertTrue(dateCompare.equalsIgnoreCase(bookPage.getMonthOfCardInCarousel(2)));
    }

    @Test
    //7. Look within one of the monthly cards from the monthly carousel
    public void verifyMonthlyCard(){
        bookPage =  bookPage.goTOMonthlySection();
        assertTrue(bookPage.isVisibleMonthDataInCard(0), "Date data is not visible on card");
        assertTrue(bookPage.isVisibleFromLabelInCard(0), "From label is not visible on card");
        assertTrue(bookPage.isVisibleCurrencySymbolInCard(0), "Currency symbol is not visible on card");
        assertTrue(bookPage.isVisibleCurrencyPriceInCard(0), "Currency price is not visible on card");
        assertTrue(bookPage.isVisibleAsteriskLabelInCard(0), "Asterisk Label is not visible on card");
    }

    @Test
    //8. Click on a monthly carousel card
    public void clickMonthlyCarousel() throws InterruptedException {
        bookPage = bookPage.goTODailyHistogram();
        /**
        * Verifying histogram for first card in carousel
        * */

        //getting current day of month
        LocalDate currentDate = LocalDate.now();
        int currentDay = currentDate.getDayOfMonth();
        String shortMonth = currentDate.getMonth().toString().substring(0,3);

        assertEquals(shortMonth, bookPage.getShortMonthFromHistogram(0),
                "Short month in histogram and current does not match");
        assertEquals(Integer.toString(currentDay+1), bookPage.getDayFromHistogram(0),
                "Day in histogram and current does not match");
        assertEquals(35, bookPage.getHistogramSize(), "Histogram size is not 35");

        /**
         * Verifying histogram from a card different than first
         * */
        bookPage = bookPage.clickOnMonthCardInPosition(1);
        Thread.sleep(2000);
        shortMonth = bookPage.getMonthOfCardInCarousel(1).substring(0,3).toUpperCase();

        assertEquals(shortMonth, bookPage.getShortMonthFromHistogram(0),
                "Short month in histogram and current does not match");
        assertEquals(Integer.toString(1), bookPage.getDayFromHistogram(0),
                "Day in histogram and current does not match");
        assertEquals(35, bookPage.getHistogramSize(), "Histogram size is not 35");
    }

    @Test
    //9. Click on the chevron / arrow to the right of the monthly carousel
    public void clickArrowMonthlyCarousel() throws InterruptedException {
        bookPage =  bookPage.goTOMonthlySection().goTONextCardsInCarousel();
        Thread.sleep(500);

        /**
         * Getting third month from current
         */
        Month thirdMonth = LocalDate.now().getMonth();
        int currentYear = LocalDate.now().getYear();

        int monthValue = thirdMonth.getValue();
        if (monthValue + 3 > 12){
            monthValue = monthValue - 9;
            currentYear++;
        } else {
            monthValue = monthValue + 3;
        }
        thirdMonth = Month.of(monthValue);

        /**
         * Verifying fourth card in carousel
         * */
        assertTrue(bookPage.isVisibleCardInCarouselPosition(3),
                "Fourth card of carousel is not visible");
        String dateCompare = thirdMonth.toString() + Integer.toString(currentYear);
        assertTrue(dateCompare.equalsIgnoreCase(bookPage.getMonthOfCardInCarousel(3)));

        /**
         * Verifying fifth card in carousel
         * */
        Month nextMonth1 = getNextMonth(thirdMonth);
        if (thirdMonth.getValue() > nextMonth1.getValue()) {
            currentYear++;
        }
        assertTrue(bookPage.isVisibleCardInCarouselPosition(4),
                "Fifth card of carousel is not visible");
        dateCompare = nextMonth1.toString() + currentYear;
        assertTrue(dateCompare.equalsIgnoreCase(bookPage.getMonthOfCardInCarousel(4)));

        /**
         * Verifying Sixth card in carousel
         * */
        Month nextMonth2 = getNextMonth(nextMonth1);
        if (nextMonth1.getValue() > nextMonth2.getValue()) {
            currentYear++;
        }
        assertTrue(bookPage.isVisibleCardInCarouselPosition(5),
                "Sixth card of carousel is not visible");
        dateCompare = nextMonth2.toString() + currentYear;
        assertTrue(dateCompare.equalsIgnoreCase(bookPage.getMonthOfCardInCarousel(5)));
    }

    @Test
    //10. Look for a monthly carousel card that has no data available
    public void verifyMonthlyCarouselWithoutData() throws InterruptedException {
        bookPage = bookPage.goTOMonthlySection().findCardWithoutData();
        assertTrue(bookPage.isVisibleLinkCheckAvailability());
        assertTrue(bookPage.isVisibleNoDataMessage());
        assertEquals(bookPage.getTextNoDataMessage(),
                "No fares searched this month",
                "No data message and expected does not match");
    }

    @Test
    //11. There should be a second part of the module that displays the price data throughout the month (histogram bars)
    public void verifyPriceDataMonthly(){
        bookPage = bookPage.goTODailyHistogram();
        LocalDate currentDate = LocalDate.now();
        int currentDay = currentDate.getDayOfMonth();
        String shortMonth = currentDate.getMonth().toString().substring(0,3);

        /**
         * Verifying first day in the daily histogram
         */
        DayOfWeek nexDayOfWeek = DayOfWeek.of(currentDate.getDayOfWeek().getValue() + 1);
        String shortDayOfWeek = nexDayOfWeek.toString().substring(0, 2);
        assertEquals(shortMonth, bookPage.getShortMonthFromHistogram(0),
                "Short month in histogram and current does not match");
        assertEquals(Integer.toString(currentDay+1), bookPage.getDayFromHistogram(0),
                "Day in histogram and current does not match");
        assertTrue(shortDayOfWeek.equalsIgnoreCase(bookPage.getShortDayOfWeekFromHistogram(0)));
    }

    //@Test
    //12. Look for the lowest fare on the daily histogram
    public void searchLowestFare(){

    }

    @Test
    //13. Hover over one of the daily bar graphs
    public void verifyTooltipDailyBar() throws InterruptedException {
        bookPage =  bookPage.goTODailyHistogram();
        assertTrue(bookPage.isVisibleRouteInTooltipDailyBar(0),
                "The route does not appears in tooltip");
        assertTrue(bookPage.isVisibleDatesInTooltipDailyBar(),
                "The dates does not appears in tooltip");
        assertTrue(bookPage.isVisiblePriceInTooltipDailyBar(),
                "The Price does not appears in tooltip");
    }

    @Test
    //14. Click on one of the daily bar graphs
    public void verifyPopupPresenceFromDailyBar() throws InterruptedException {
        bookPage = bookPage.goTODailyHistogram().goTOPopup();
        assertTrue(bookPage.isVisibleOriginAirportInPopup() && bookPage.isVisibleArrivalAirportInPopup(),
                "Origin or Arrival airports are not visible in the popup");
    }

    @Test
    //15. Check for the data inside the popup is the same than the fare selected
    public void verifyDataPopupFromDailyBar() throws InterruptedException {
        bookPage = bookPage.goTOMonthlySection().goTODailyHistogram();
        String originAirportFare = bookPage.getOriginAirportSelected();
        String arrivalAirportFare = bookPage.getArrivalAirportSelected();
        String originAirportFareMnemonic = bookPage.getOriginAirportSelectedMnemonic();
        String arrivalAirportFareMnemonic = bookPage.getArrivalAirportSelectedMnemonic();
        String datesSelectedHistogram = bookPage.getDatesSelectedHistogram();
        String dateDepartureSelectedHistogram = datesSelectedHistogram.split("-")[0].trim();
        String dateReturnSelectedHistogram = datesSelectedHistogram.split("-")[1].trim();
        bookPage = bookPage.goTOPopup();
        String originAirportPopup = bookPage.getOriginAirportFromPopup();
        String arrivalAirportPopup = bookPage.getArrivalAirportFromPopup();
        String departureDatePopup = bookPage.getDateDepartureSelectedPopup();
        String returnDatePopup = bookPage.getDateReturnSelectedPopup();
        assertTrue(originAirportPopup.contains(originAirportFare) && originAirportPopup.contains(originAirportFareMnemonic),
                "Origin airport city or mnemonic does not match");
        assertTrue(arrivalAirportPopup.contains(arrivalAirportFare) && arrivalAirportPopup.contains(arrivalAirportFareMnemonic),
                "Arrival airport city or mnemonic does not match");

        assertTrue(dateDepartureSelectedHistogram.equalsIgnoreCase(departureDatePopup),
                "Departure date selected does not match with departure date in popup");
        assertTrue(dateReturnSelectedHistogram.equalsIgnoreCase(returnDatePopup),
                "Return date selected does not match with Return date in popup");

    }

    @Test
    //16. Check which is the month appearing in the months carousel
    public void verifyMonthInCarousel(){
        bookPage =  bookPage.goTOMonthlySection();
        LocalDate currentDate = LocalDate.now();
        Month currentMonth = currentDate.getMonth();
        int currentYear = currentDate.getYear();
        /**
         * Verifying first card in carousel
         * */
        assertTrue(bookPage.isVisibleCardInCarouselPosition(0),
                "First card of carousel is not visible");
        String dateCompare = currentMonth.toString() + Integer.toString(currentYear);
        assertTrue(dateCompare.equalsIgnoreCase(bookPage.getMonthOfCardInCarousel(0)));
    }

    @Test
    //17. Check the number of fares in the histogram bars appearing in desktop is different from the ones that appear on mobile
    public void verifyNumberFaresDesktop(){
        bookPage = bookPage.goTODailyHistogram();
        assertEquals(35, bookPage.getHistogramSize(), "Histogram size is not 35");
    }

    //@Test
    //20. Check if the module is calling the histogram endpoint
    public void verifyHistogramEndpoint(){

    }

    //@Test
    // ---- En construccion
    //21. Click the Book now button inside the pop up and check the landing page
    public void verifyLandingPage() throws InterruptedException {
        bookPage = bookPage.goTODailyHistogram().goTOPopup();
        String originAirportPopup = bookPage.getOriginAirportFromPopup();
        String arrivalAirportPopup = bookPage.getArrivalAirportFromPopup();
        String originMnemonic = bookPage.getAirportMnemonicFromPopup(originAirportPopup);
        String arrivalMnemonic = bookPage.getAirportMnemonicFromPopup(arrivalAirportPopup);
        FlightPage flightPage = bookPage.goTOFlightPage();
        assertEquals(originMnemonic, flightPage.getOriginAirportMnemonicFlightPage());
        assertEquals(arrivalMnemonic, flightPage.getArrivalAirportMnemonicFlightPage());
    }

    //@Test
    //22. Scrape the date range inside the histogram endpoint for the any desired month
    public void verifyDateRangeHistogramEndpoint(){

    }

    //@Test
    //23. Block the collect and tracking libraries loading in network
    public void blockRequestsGoogleAnalitycs(){

    }

    private Month getNextMonth(Month currentMonth) {
        if (currentMonth.equals(Month.DECEMBER)){
            return Month.JANUARY;
        }else return Month.of(currentMonth.getValue()+1);

    }
}
