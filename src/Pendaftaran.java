import connection.DBConnect;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class Pendaftaran extends JFrame {
    private JPanel panel1;
    private JTable tblTicket;
    private JComboBox<String> cmbKeperluan;
    private JButton simpanButton;
    private JLabel label_tanggal;
    Date tanggal = new Date();

    SimpleDateFormat dateFormatTampil = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat dateFormatSQL = new SimpleDateFormat("dd/MM/yyyy");
    String tanggalString = dateFormatTampil.format(tanggal);

    private DefaultTableModel model;

    public void frameConfig() {
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        // Mengatur state frame menjadi maksimalkan
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocation(0, 0);
    }

    public Pendaftaran() {
        System.out.println(generateID());
        frameConfig(); // Mengatur konfigurasi panel
        label_tanggal.setText(tanggalString);
        model = new DefaultTableModel();
        tblTicket.setModel(model);

        addColumn();
        loadData(model);

        simpanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(cmbKeperluan.getSelectedIndex() == 0){
                    JOptionPane.showMessageDialog(null, "Pilih keperluan nasabah!",
                            "Peringatan!", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }else{
                    try {
                        DBConnect connection = new DBConnect();
                        String query = "INSERT INTO  antrian (Nomor_Antrian, Keperluan, tanggal, status) values (?, ?, ?,?)";
                        connection.pstat = connection.conn.prepareStatement(query);
                        connection.pstat.setString(1, generateID());
                        connection.pstat.setString(2, cmbKeperluan.getSelectedItem().toString());
                        connection.pstat.setString(3, dateFormatSQL.format(tanggal));
                        connection.pstat.setString(4, "1");

                        connection.pstat.executeUpdate();
                        connection.pstat.close();

                        JOptionPane.showMessageDialog(null, "Penambahan Tiket Berhasil!",
                                "Informasi!", JOptionPane.INFORMATION_MESSAGE);

                        try{
                            HashMap<String, Object> parameter = new HashMap<>();

                            parameter.put("keperluan", cmbKeperluan.getSelectedItem().toString());
                            parameter.put("id", generateID());
                            DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"id", "keperluan"}, 0);
                            String id = generateID();
                            String keperluan = cmbKeperluan.getSelectedItem().toString();
                            tableModel.addRow(new Object[]{id, keperluan});

                            JRDataSource dataSource = new JRTableModelDataSource(tableModel);
                            JasperDesign jasperDesign = JRXmlLoader.load("jasper/MyReports/No_Antrian.jrxml");
                            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
                            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameter, dataSource);
                            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
                            jasperViewer.setVisible(true);
                        }catch (Exception ex)
                        {
                            ex.printStackTrace();
                        }


                        cmbKeperluan.setSelectedIndex(0);
                        loadData(model);
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Terjadi error saat insert data obat: " + ex);
                    }
                }



            }
        });
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Pendaftaran().setVisible(true);
            }
        });
    }

    public void addColumn() {
        model.addColumn("Nomor Ticket");
        model.addColumn("Keperluan");
    }


    public void loadData(DefaultTableModel model) {
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();

        try {
            DBConnect connection = new DBConnect();
            String tanggalHariIni = dateFormatSQL.format(tanggal);
            connection.stat = connection.conn.createStatement();
            String query = "SELECT * FROM antrian WHERE status = 1 AND tanggal = '" + tanggalHariIni + "'";
            connection.result = connection.stat.executeQuery(query);

            while (connection.result.next()) {
                Object[] obj = new Object[2];
                obj[0] = connection.result.getString("Nomor_Antrian");
                obj[1] = connection.result.getString("Keperluan");
                model.addRow(obj);
            }
            connection.stat.close();
            connection.result.close();
        } catch (Exception e) {
            System.out.println("Terjadi error saat load data obat: " + e);
        }
    }

    public String generateID() {
        String nextAntrian = "";

        DBConnect connection = new DBConnect();
        try {
            connection.stat = connection.conn.createStatement();
            // Mendapatkan tanggal hari ini

            String tanggalHariIni = dateFormatSQL.format(tanggal);

            String query = "SELECT MAX(Nomor_Antrian) FROM antrian WHERE tanggal = '" + tanggalHariIni + "'";
            connection.result = connection.stat.executeQuery(query);

            // Memeriksa hasil query
            if (connection.result.next()) {
                int maxAntrian = connection.result.getInt(1);

                // Jika sudah ada data antrian untuk hari ini
                if (maxAntrian > 0) {
                    int nextAntrianValue = maxAntrian + 1;
                    nextAntrian = String.valueOf(nextAntrianValue);
                } else {
                    // Jika belum ada data antrian untuk hari ini
                    nextAntrian = "1";
                }
            }
            connection.stat.close();
            connection.result.close();
        } catch (Exception e) {
            System.out.println("Terjadi error saat load data obat: " + e);
        }

        return nextAntrian;
    }


}
