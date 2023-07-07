import connection.DBConnect;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LQueue {
    private SLinkedList elements;
    private int size;

    Date tanggal = new Date();
    SimpleDateFormat dateFormatTampil = new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat dateFormatSQL = new SimpleDateFormat("dd/MM/yyyy");

    public LQueue() {
        elements = new SLinkedList();
        size = 0;
    }
    public void clear() {
        elements = new SLinkedList();
        size = 0;
    }


    public boolean isEmpty() {
        return size == 0;
    }

    public void enqueue(String newElement, String keperluan) {
        System.out.println("enqueue " + newElement);

        Ticket newNode = new Ticket(newElement, keperluan);
        elements.addLast(newNode);
        size++;
    }


    public String dequeue(String data) {
        if (size == 0) {
            System.out.println("Cannot dequeue, Queue is empty");
            return null;
        }

        int index = -1;
        for (int i = 0; i < elements.getSize() ; i++) {
            if (elements.getTicket(i).getNoTicket().equals(data)) {
                index = i;
                // Mendapatkan waktu saat ini
                java.sql.Timestamp waktuSekarang = new java.sql.Timestamp(System.currentTimeMillis());
                try{
                    DBConnect connection = new DBConnect();

                    String sql2 = "UPDATE antrian SET status = 0, waktu_keluar = ? WHERE Nomor_Antrian = ? AND Tanggal=?";
                    connection.pstat = connection.conn.prepareStatement(sql2);
                    connection.pstat.setTimestamp(1, waktuSekarang);
                    connection.pstat.setString(2, data);
                    connection.pstat.setString(3, dateFormatSQL.format(tanggal));
                    connection.pstat.executeUpdate();

                    connection.pstat.close();
                }catch (Exception ex){

                }
                break;
            }
        }

        if (index == -1) {
            System.out.println("Cannot dequeue, Data not found in the queue");
            return null;
        }

        String frontElement = elements.getTicket(index).getNoTicket();
        System.out.println("Dequeue " + frontElement);

        elements.deleteMiddle(frontElement);

        size--;

        return frontElement;
    }



    public Ticket peek(int index) {
        if (index < 0 || index >= size) {
            System.out.println("Invalid index");
            return null;
        }

        Ticket currentNode = elements.getHead();
        int currentIndex = 0;

        while (currentNode != null && currentIndex < index) {
            currentNode = currentNode.getNextReferences();
            currentIndex++;
        }

        if (currentNode == null) {
            System.out.println("Invalid index");
            return null;
        }

        Ticket temp = new Ticket(currentNode.getNoTicket(), currentNode.getKeperluan());
        return temp;
    }






}
