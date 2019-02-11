package iait.eiv.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class PersonaPK implements Serializable {

    private static final long serialVersionUID = -8686841786142756198L;

    private Integer tipoDoc;
    private Integer numDoc;

    public Integer getTipoDoc() {
        return tipoDoc;
    }

    public void setTipoDoc(Integer tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    public Integer getNumDoc() {
        return numDoc;
    }

    public void setNumDoc(Integer numDoc) {
        this.numDoc = numDoc;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((numDoc == null) ? 0 : numDoc.hashCode());
        result = prime * result + ((tipoDoc == null) ? 0 : tipoDoc.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PersonaPK other = (PersonaPK) obj;
        if (numDoc == null) {
            if (other.numDoc != null)
                return false;
        } else if (!numDoc.equals(other.numDoc))
            return false;
        if (tipoDoc == null) {
            if (other.tipoDoc != null)
                return false;
        } else if (!tipoDoc.equals(other.tipoDoc))
            return false;
        return true;
    }

}
