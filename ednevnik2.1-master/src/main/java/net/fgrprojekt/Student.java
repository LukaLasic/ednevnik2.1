package net.fgrprojekt;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;


@Entity
@Table(name="students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long student_id;

    @Column(length = 200, nullable = false)
    @NotBlank(message = "Molimo unesite ime studenta.")
    String Ime;

    @Column(length = 150, nullable = false)
    @NotBlank(message = "Molimo unesite prezime studenta.")
    String Prezime;



    @Column(length = 50, nullable = false)
    String Matematika;

    @Column(length = 50, nullable = false)
    String Hrvatski_jezik;

    @Column(length = 5, nullable = false)
    String Engleski_jezik;

    @Column(length = 5, nullable = false)
    String Fizika;

    public Long getStudent_id() {
        return student_id;
    }

    public void setStudent_id(Long student_id) {
        this.student_id = student_id;
    }

    public String getIme() {
        return Ime;
    }

    public void setIme(String ime) {
        Ime = ime;
    }

    public String getPrezime() {
        return Prezime;
    }

    public void setPrezime(String prezime) {
        Prezime = prezime;
    }



    public String getMatematika() {
        return Matematika;
    }

    public void setMatematika(String matematika) {
        Matematika = matematika;
    }

    public String getHrvatski_jezik() {
        return Hrvatski_jezik;
    }

    public void setHrvatski_jezik(String hrvatski_jezik) {
        Hrvatski_jezik = hrvatski_jezik;
    }

    public String getEngleski_jezik() {
        return Engleski_jezik;
    }

    public void setEngleski_jezik(String engleski_jezik) {
        Engleski_jezik = engleski_jezik;
    }

    public String getFizika() {
        return Fizika;
    }

    public void setFizika(String fizika) {
        Fizika = fizika;
    }
}