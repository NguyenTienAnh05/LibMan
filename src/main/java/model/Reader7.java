package model;

/**
 * Bạn đọc (theo sơ đồ thiết kế): kế thừa Member7.
 */
public class Reader7 extends Member7 {
    private static final long serialVersionUID = 1L;

    private String readerCode;
    private Library_card7 card;

    public Reader7() {
        super();
    }

    public String getReaderCode() { return readerCode; }
    public void setReaderCode(String readerCode) { this.readerCode = readerCode; }
    public Library_card7 getCard() { return card; }
    public void setCard(Library_card7 card) { this.card = card; }
}
