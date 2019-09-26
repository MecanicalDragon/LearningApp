package net.medrag.fastxsd.dto;

/**
 * DTO object
 * <p>
 * {@author} Stanislav Tretyakov
 * 19.09.2018
 */
public class EntryObject {

    private String id;
    private String dienst;
    private String dienstauspraegung;
    private String anwendsungsname;
    private String comment;
    private boolean switched;

    public EntryObject() {
    }

    public EntryObject(String id, String dienst, String dienstauspraegung, String anwendsungsname, String comment, boolean switched) {
        this.id = id;
        this.dienst = dienst;
        this.dienstauspraegung = dienstauspraegung;
        this.anwendsungsname = anwendsungsname;
        this.comment = comment;
        this.switched = switched;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAnwendsungsname() {
        return anwendsungsname;
    }

    public void setAnwendsungsname(String anwendsungsname) {
        this.anwendsungsname = anwendsungsname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDienstauspraegung() {
        return dienstauspraegung;
    }

    public void setDienstauspraegung(String dienstauspraegung) {
        this.dienstauspraegung = dienstauspraegung;
    }

    public boolean isSwitched() {
        return switched;
    }

    public void setSwitched(boolean switched) {
        this.switched = switched;
    }

    public String getDienst() {
        return dienst;
    }

    public void setDienst(String dienst) {
        this.dienst = dienst;
    }

    @Override
    public String toString() {
        return "EntryObject{" +
                "id='" + id + '\'' +
                ", dienst='" + dienst + '\'' +
                ", dienstauspraegung='" + dienstauspraegung + '\'' +
                ", anwendsungsname='" + anwendsungsname + '\'' +
                ", comment='" + comment + '\'' +
                ", switched=" + switched +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EntryObject that = (EntryObject) o;

        if (switched != that.switched) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (dienst != null ? !dienst.equals(that.dienst) : that.dienst != null) return false;
        return dienstauspraegung != null ? dienstauspraegung.equals(that.dienstauspraegung) : that.dienstauspraegung == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (dienst != null ? dienst.hashCode() : 0);
        result = 31 * result + (dienstauspraegung != null ? dienstauspraegung.hashCode() : 0);
        result = 31 * result + (switched ? 1 : 0);
        return result;
    }
}
