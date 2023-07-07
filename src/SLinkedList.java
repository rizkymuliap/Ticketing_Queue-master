public class SLinkedList {
    private Ticket head;
    private Ticket tail;
    private int size;

    public Ticket getHead() {
        return head;
    }

    public void setHead(Ticket head) {
        this.head = head;
    }

    public Ticket getTail() {
        return tail;
    }

    public void setTail(Ticket tail) {
        this.tail = tail;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public SLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public boolean isEmpty() {
        if ((this.head==null) && (this.tail==null)) {
            return true;
        } else {
            return false;
        }
    }

    public void addFirst(Ticket node) {
        if (!isEmpty()) {
            node.setNextReferences(head);
            head = node;
        } else {
            node.setNextReferences(null);
            tail = node;
            head = node;
        }
        ++this.size;
    }

    public int getSize() {
        return size;
    }



    public void addLast(Ticket node) {
        if (!isEmpty()) {
            node.setNextReferences(null);
            this.tail.setNextReferences(node);
            tail = node;
        } else {
            node.setNextReferences(null);
            tail = node;
            head = node;
        }
        ++this.size;
    }

    public void deleteHead() {
        Ticket pointer;

        pointer = head;
        head = pointer.getNextReferences();
        pointer = null;
    }

    public Ticket search(String data) {
        Ticket pointer;

        if (isEmpty()) return null;

        pointer = head;

        while (pointer != null) {
            if (pointer.getNoTicket().contentEquals(data))
                return pointer;

            pointer = pointer.getNextReferences();
        }

        return null;
    }

    public void display() {
        Ticket pointer;

        pointer = head;

        System.out.println("Size :" + this.size);

        while (pointer != null) {
            System.out.println(pointer.getNoTicket());
            pointer = pointer.getNextReferences();
        }

    }

    public Ticket getTicket(int i) {
        Ticket currentNode = head;
        int currentIndex = 0;

        while (currentNode != null && currentIndex < i) {
            currentNode = currentNode.getNextReferences();
            currentIndex++;
        }

        return currentNode;
    }


    public void deleteMiddle(String nomor) {
        if (isEmpty()) {
            return; // Tidak ada elemen dalam linked list
        }

        Ticket current = head;
        Ticket previous = null;

        // Mencari elemen dengan nomor yang sesuai
        while (current != null && !current.getNoTicket().equals(nomor)) {
            previous = current;
            current = current.getNextReferences();
        }

        if (current == null) {
            return; // Elemen tidak ditemukan
        }

        // Menghapus elemen tengah
        if (current == head) {
            deleteHead(); // Jika elemen yang dihapus adalah kepala
        } else {
            previous.setNextReferences(current.getNextReferences());
            current = null;
        }

        size--;
    }



}
