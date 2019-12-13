package pageinterfaces;

public class AbstractPageUI {
    public static final String DYNAMIC_PAGE = "//span[text()='%s']";
    public static final String DYNAMIC_POPUP_MESSAGE = "//p[contains(text(),'%s')]";
    public static final String DYNAMIC_BUTTON = "(//div | //button)[contains(text(),'%s')]";
    public static final String INLINE_ERROR_MESSAGE = "//label[contains(text(),'%s')]//following-sibling::div[contains(text(),'%s')]";
    public static final String DYNAMIC_TEXT_BOX = "//label[contains(text(),'%s')]//following-sibling::input";
    public static final String DYNAMIC_AMOUNT_TEXT_BOX = "//label[contains(text(),'%s')]/following-sibling::div/input";
    public static final String DIALOG_POPUP = "//div[@class='modal-dialog']";
    public static final String POPUP_CLOSE_BUTTON = "//button[@class='close']";
    public static final String DYNAMIC_LINK = "//a[contains(text(),'%s')]";
}
