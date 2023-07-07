import connection.DBConnect;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Antrian_Ticket extends JFrame {
    private JButton selesaiButton5;
    private JButton selesaiButton4;
    private JButton selesaiButton3;
    private JButton selesaiButton2;
    private JButton selesaiButton1;
    private JLabel labelCS1;
    private JLabel labelCS2;
    private JLabel labelAmbiluang1;
    private JLabel labelAmbiluang2;
    private JLabel labelBuatKartu1;
    private JPanel panel1;
    private JButton Refresh;

    int jumlahData;
    int nomor1 = 0, nomor2 = 0, nomor3 = 0, nomor4 = 0, nomor5 = 0;
    LQueue antrianBank = new LQueue();
    Date tanggal = new Date();
    SimpleDateFormat dateFormatTampil = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat dateFormatSQL = new SimpleDateFormat("dd/MM/yyyy");

    public void frameConfig(){
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        // Mengatur state frame menjadi maksimalkan
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocation(0, 0);
    }

    public Antrian_Ticket()  {


        frameConfig();
        loadAwalQueue();

        labelCS1.setText("---");
        labelCS2.setText("---");
        labelAmbiluang1.setText("---");
        labelAmbiluang2.setText("---");
        labelBuatKartu1.setText("---");

        loadQueue();

        jumlahData = jumlahData - 5;
        antrianBank.dequeue(String.valueOf(nomor1));
        antrianBank.dequeue(String.valueOf(nomor2));
        antrianBank.dequeue(String.valueOf(nomor3));
        antrianBank.dequeue(String.valueOf(nomor4));
        antrianBank.dequeue(String.valueOf(nomor5));


        selesaiButton5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 labelCS1.setText("---");
                 loadQueue();
                 antrianBank.dequeue(String.valueOf(nomor1));
                 jumlahData--;
            }
        });

        selesaiButton4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                labelCS2.setText("---");
                loadQueue();
                antrianBank.dequeue(String.valueOf(nomor2));
                jumlahData--;
            }
        });
        selesaiButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                labelAmbiluang1.setText("---");
                loadQueue();
                antrianBank.dequeue(String.valueOf(nomor3));
                jumlahData--;
            }
        });
        selesaiButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                labelAmbiluang2.setText("---");
                loadQueue();
                antrianBank.dequeue(String.valueOf(nomor4));
                jumlahData--;
            }
        });
        selesaiButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                labelBuatKartu1.setText("---");
                loadQueue();
                antrianBank.dequeue(String.valueOf(nomor5));
                jumlahData--;
            }
        });
        Refresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                antrianBank.clear();

                jumlahData = jumlahData - 5;

                loadAwalQueue();
                antrianBank.dequeue(String.valueOf(nomor1));
                antrianBank.dequeue(String.valueOf(nomor2));
                antrianBank.dequeue(String.valueOf(nomor3));
                antrianBank.dequeue(String.valueOf(nomor4));
                antrianBank.dequeue(String.valueOf(nomor5));

                loadQueue();
            }
        });
    }

    public void loadAwalQueue(){
        jumlahData = 0;

        try {
            DBConnect connection = new DBConnect();
            String tanggalHariIni = dateFormatSQL.format(tanggal);
            connection.stat = connection.conn.createStatement();
            String query = "SELECT * FROM antrian WHERE status = 1 AND tanggal = '" + tanggalHariIni + "'";
            connection.result = connection.stat.executeQuery(query);

            while (connection.result.next()) {
                jumlahData++;
                antrianBank.enqueue(connection.result.getString("Nomor_Antrian"), connection.result.getString("Keperluan"));
            }
            connection.stat.close();
            connection.result.close();
        } catch (Exception e) {
            System.out.println("Terjadi error saat load data obat: " + e);
        }
    }

    public void loadQueue()
    {
        for(int i =0; i < jumlahData; i++){
            switch (antrianBank.peek(i).getKeperluan()){
                case "Customer Service" :
                    if(labelCS1.getText().equals("---")){
                        labelCS1.setText(antrianBank.peek(i).getNoTicket());
                        nomor1 = Integer.parseInt(labelCS1.getText());
                        waktuMasuk(String.valueOf(nomor1));
                        //antrianBank.dequeue(String.valueOf(nomor1));
                    }else if(labelCS2.getText().equals("---")){
                        labelCS2.setText(antrianBank.peek(i).getNoTicket());
                        nomor2 = Integer.parseInt(labelCS2.getText());
                        waktuMasuk(String.valueOf(nomor2));
                        //antrianBank.dequeue(String.valueOf(nomor2));
                    }
                    break;
                case "Ambil Uang" :
                    if(labelAmbiluang1.getText().equals("---")){
                        labelAmbiluang1.setText(antrianBank.peek(i).getNoTicket());
                        nomor3 = Integer.parseInt(labelAmbiluang1.getText());
                        waktuMasuk(String.valueOf(nomor3));
                        //antrianBank.dequeue(String.valueOf(nomor3));
                    }else if(labelAmbiluang2.getText().equals("---")){
                        labelAmbiluang2.setText(antrianBank.peek(i).getNoTicket());
                        nomor4 = Integer.parseInt(labelAmbiluang2.getText());
                        waktuMasuk(String.valueOf(nomor4));
                        //antrianBank.dequeue(String.valueOf(nomor4));
                    }
                    break;
                case "Buat Kartu" :
                    if(labelBuatKartu1.getText().equals("---")){
                        labelBuatKartu1.setText(antrianBank.peek(i).getNoTicket());
                        nomor5 = Integer.parseInt(labelBuatKartu1.getText());
                        waktuMasuk(String.valueOf(nomor5));
                        //antrianBank.dequeue(String.valueOf(nomor5));
                    }
                    break;
            }
        }
    }

    public void waktuMasuk(String nomor){
        try {
            DBConnect connection = new DBConnect();

            // Mendapatkan waktu saat ini
            java.sql.Timestamp waktuSekarang = new java.sql.Timestamp(System.currentTimeMillis());

            String sql2 = "UPDATE antrian SET  waktu_masuk = ? WHERE Nomor_Antrian = ? AND Tanggal = ?";
            connection.pstat = connection.conn.prepareStatement(sql2);
            connection.pstat.setTimestamp(1, waktuSekarang);
            connection.pstat.setString(2, nomor);
            connection.pstat.setString(3, dateFormatSQL.format(tanggal));
            connection.pstat.executeUpdate();

            connection.pstat.close();
        } catch (Exception ex) {
            // Tangkap dan tangani exception jika terjadi kesalahan
            ex.printStackTrace();
        }

    }

    public static void main(String[] args) {
        new Antrian_Ticket().setVisible(true);
    }
}
