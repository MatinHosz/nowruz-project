package db.example;

import db.Entity;
import db.Trackable;

import java.util.Date;

public class Document extends Entity implements Trackable {
    public static final int DOCUMENT_ENTITY_CODE = 17;

    public String content;
    private Date creationDate;
    private Date lastModificationDate;


    public Document(String content) {
        this.content = content;
    }

    public void setCreationDate(Date date) {
        creationDate = date;
    }
    public Date getCreationDate() {
        return creationDate;
    }

    public void setLastModificationDate(Date date) {
        lastModificationDate = date;
    }
    public Date getLastModificationDate() {
        return lastModificationDate;
    }

    @Override
    public Document copy() {
        Document copyDocument = new Document(content);
        copyDocument.id = id;
        copyDocument.creationDate = (this.creationDate != null) ? new Date(this.creationDate.getTime()) : null;
        copyDocument.creationDate = (this.lastModificationDate != null) ? new Date(this.lastModificationDate.getTime()) : null;
        return copyDocument;
    }
    @Override
    public int getEntityCode() { return DOCUMENT_ENTITY_CODE; }
}
