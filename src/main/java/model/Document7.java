package model;

import java.io.Serializable;


public class Document7 implements Serializable {
    private static final long serialVersionUID = 1L;

    private int ID;
    private String documentName;
    private String category;
    private String authors;
    private int publishYear;
    private double price;
    private String description;
    private Publisher7 publisher;

    public Document7() {
    }

    public int getID() { return ID; }
    public void setID(int ID) { this.ID = ID; }

    public String getDocumentName() { return documentName; }
    public void setDocumentName(String documentName) { this.documentName = documentName; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getAuthors() { return authors; }
    public void setAuthors(String authors) { this.authors = authors; }

    public int getPublishYear() { return publishYear; }
    public void setPublishYear(int publishYear) { this.publishYear = publishYear; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Publisher7 getPublisher() { return publisher; }
    public void setPublisher(Publisher7 publisher) { this.publisher = publisher; }
}
