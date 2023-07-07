import java.util.Date;
import java.util.Timer;

public class Ticket {
    private int ID;
    private String NoTicket;
    private String keperluan;
    private Date tanggal;
    private Timer waktu_masuk;
    private Timer waktu_keluar;

    private Ticket nextReferences;

    public Ticket getNextReferences() {
        return nextReferences;
    }

    public void setNextReferences(Ticket nextReferences) {
        this.nextReferences = nextReferences;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public Timer getWaktu_masuk() {
        return waktu_masuk;
    }

    public void setWaktu_masuk(Timer waktu_masuk) {
        this.waktu_masuk = waktu_masuk;
    }

    public Timer getWaktu_keluar() {
        return waktu_keluar;
    }

    public void setWaktu_keluar(Timer waktu_keluar) {
        this.waktu_keluar = waktu_keluar;
    }

    public Ticket(String noTicket, String keperluan) {
        NoTicket = noTicket;
        this.keperluan = keperluan;
    }

    public String getNoTicket() {
        return NoTicket;
    }

    public void setNoTicket(String noTicket) {
        NoTicket = noTicket;
    }

    public String getKeperluan() {
        return keperluan;
    }

    public void setKeperluan(String keperluan) {
        this.keperluan = keperluan;
    }

}
