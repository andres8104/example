package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;

public class BookPage {

    private WebDriver driver;
    public WebDriverWait wait;

    private By breadcrumb = By.xpath("//*[@class='breadcrumbs block']");
    private By monthlySection = By.xpath("//*[@class='deal-list-slider fare-slider intuitive-indicator hover-CTA']");
    private By monthly = By.xpath("//*[@class='top-section']");
    private By faresSection = By.xpath("//*[@class='search-container']");
    private By fares = By.xpath("//*[@class='select-destination']");
    private By currencySymbol = By.xpath("//*[@class='fare-atom-price-currency fare-atom-price-currency--front']");
    private By currencyPrice = By.xpath("//*[@class='fare-atom-price-total-price']");
    private By fromLabel = By.xpath("//*[@class='destination-container']");
    private By asteriskLabel = By.xpath("//*[@class='fare-atom-price-disclaimer-indicator']");
    private By dailyHistogram = By.xpath("//*[@class='bar-body']");
    private By days = By.xpath("//*[@class='chart-bar']");
    private By nextMonthButton = By.xpath("//*[@class='slick-arrow slick-next']");
    private By linkCheckAvailability = By.xpath("//*[@class='link-check-availability']");
    private By noDataMessage = By.xpath("//*[@class='aeroplane-section-noimg']");
    private By barDailyHistogram = By.xpath("//*[@class='color   ']");
    private By barDailyHistogramRoutes = By.xpath("//*[@class='title-tooltip-deal']");
    private By barDailyHistogramDates = By.xpath("//*[@class='body-tooltip-deal']");
    private By popupCloseButton = By.xpath("//*[@class='material-icons']");
    private By airportsLists = By.xpath("//*[@class='em__field form-group LocationSelector__container false false']");

    public BookPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 30);
    }

    public BookPage goTOMonthlySection() {
        executeScript("arguments[0].scrollIntoView(true)", breadcrumb);
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(monthlySection));
        return this;
    }

    public BookPage goTOFaresSection() {
        executeScript("arguments[0].scrollIntoView(true)", breadcrumb);
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(faresSection));
        return this;
    }

    public BookPage goTODailyHistogram() {
        executeScript("arguments[0].scrollIntoView(true)", breadcrumb);
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(dailyHistogram));
        executeScript("arguments[0].scrollIntoView(true)", dailyHistogram);
        return this;
    }

    public BookPage goTONextCardsInCarousel() {
        driver.findElement(nextMonthButton).click();
        return this;
    }

    public boolean isVisibleMonthlySection() {
        return driver.findElement(monthlySection).isDisplayed();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public String getOriginAirportSelected() {
        List<WebElement> faresElements = driver.findElements(fares);
        return new Select(faresElements.get(0)).getFirstSelectedOption().getText().substring(4);
    }

    public String getArrivalAirportSelected() {
        List<WebElement> faresElements = driver.findElements(fares);
        return new Select(faresElements.get(1)).getFirstSelectedOption().getText().substring(4);
    }

    public boolean isVisibleCardInCarouselPosition(int position) {
        List<WebElement> monthElements = driver.findElements(monthly);
        return monthElements.get(position).isDisplayed();
    }

    public String getMonthOfCardInCarousel(int position) {
        List<WebElement> monthElements = driver.findElements(monthly);
        return monthElements.get(position).getText();
    }

    public String getDayFromHistogram(int position) {
        String text = getTextFromHistogramPosition(position);
        return text.substring(4, text.length() - 3);
    }

    public int getHistogramSize() {
        return driver.findElements(days).size();
    }

    private String getTextFromHistogramPosition(int position) {
        List<WebElement> histogramDays = driver.findElements(days);
        return histogramDays.get(position).getText();
    }

    public String getShortMonthFromHistogram(int position) {
        return getTextFromHistogramPosition(position).substring(0, 3);
    }

    public boolean isVisibleMonthDataInCard(int position) {
        return verifyMonthlyCardElements(monthly, position);
    }

    public boolean isVisibleFromLabelInCard(int position) {
        return verifyMonthlyCardElements(fromLabel, position);
    }

    public boolean isVisibleCurrencySymbolInCard(int position) {
        return verifyMonthlyCardElements(currencySymbol, position);
    }

    public boolean isVisibleCurrencyPriceInCard(int position) {
        return verifyMonthlyCardElements(currencyPrice, position);
    }

    public boolean isVisibleAsteriskLabelInCard(int position) {
        return verifyMonthlyCardElements(asteriskLabel, position);
    }

    private boolean verifyMonthlyCardElements(By selector, int position) {
        List<WebElement> elements = driver.findElements(selector);
        return elements.get(position).isDisplayed();
    }

    public BookPage clickOnMonthCardInPosition(int position) {
        List<WebElement> monthElements = driver.findElements(monthly);
        monthElements.get(position).click();
        return this;
    }

    public BookPage findCardWithoutData() throws InterruptedException {
        List<WebElement> elements = driver.findElements(By.xpath("//*[@class='card deal-item fare-item--vertical-tile is-empty-fare ']"));
        for (int i = 0 ;i<3; i++){
            try {
                if (!elements.get(0).isDisplayed()){
                    goTONextCardsInCarousel();
                    Thread.sleep(500);
                }else{
                    break;
                }
            }catch (Exception e){

            }
        }
        return this;
    }

    public boolean isVisibleLinkCheckAvailability(){
        List<WebElement> linksAvailabilityList = driver.findElements(linkCheckAvailability);
        return linksAvailabilityList.get(0).isDisplayed();
    }

    public Boolean isVisibleNoDataMessage(){
        List<WebElement> noDataMessageList = driver.findElements(noDataMessage);
        return noDataMessageList.get(0).isDisplayed();
    }

    public String getTextNoDataMessage(){
        List<WebElement> noDataMessageList = driver.findElements(noDataMessage);
        return noDataMessageList.get(0).getText();
    }

    public boolean isVisibleRouteInTooltipDailyBar(int position) throws InterruptedException {
        List<WebElement> dailyHistogramTooltipsList = driver.findElements(barDailyHistogramRoutes);
        showTooltipInHistogram(position);
        return dailyHistogramTooltipsList.get(position).isDisplayed();
    }

    public boolean isVisibleDatesInTooltipDailyBar(int position) throws InterruptedException {
        List<WebElement> dailyHistogramTooltipsList = driver.findElements(barDailyHistogramDates);
        showTooltipInHistogram(position);
        return dailyHistogramTooltipsList.get(position).isDisplayed();
    }

    public BookPage goTOPopup(){
        List<WebElement> histogramDays = driver.findElements(barDailyHistogram);
        histogramDays.get(0).click();
        return this;
    }

    public Boolean isVisibleOriginAirportInPopup(){
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(popupCloseButton));
        List<WebElement> airportsElements = driver.findElements(airportsLists);
        return airportsElements.get(2).isDisplayed();
    }

    public Boolean isVisibleArrivalAirportInPopup(){
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(popupCloseButton));
        List<WebElement> airportsElements = driver.findElements(airportsLists);
        return airportsElements.get(3).isDisplayed();
    }

    protected void showTooltipInHistogram(int position) throws InterruptedException {
        List<WebElement> dailyHistogramBarsList = driver.findElements(barDailyHistogram);
        Actions actions = new Actions(driver);
        //actions.moveToElement(dailyHistogramBarsList.get(position)).perform();
        actions.clickAndHold(dailyHistogramBarsList.get(position)).moveToElement(dailyHistogramBarsList.get(position));
        Thread.sleep(500);
    }

    protected void executeScript(String argument, By by){
        JavascriptExecutor executor = (JavascriptExecutor) driver; // esto permite no hacer scroll
        executor.executeScript(argument, driver.findElement(by));
    }

}