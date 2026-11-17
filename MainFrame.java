import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainFrame extends JFrame implements ActionListener, MouseListener {

    private JTextField txtNama;
    private JTextField txtNIM;
    private JTextField txtNilai;
    private JComboBox<String> cmbProdi; // Komponen atomic baru (Dropdown)
    private JButton btnProses, btnReset;
    private JTextArea txtOutput;
    private JLabel lblStatus;

    public MainFrame() {
        setTitle("Aplikasi Cek Nilai & Status Kelulusan Mahasiswa");
        setSize(600, 530);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center window
        setLayout(new BorderLayout(10, 10));

        // Intermediate Container: JPanel dengan GridLayout (5 baris, 2 kolom)
        JPanel panelForm = new JPanel(new GridLayout(5, 2, 8, 8));
        panelForm.setBorder(BorderFactory.createEmptyBorder(15, 15, 5, 15));

        // Atomic Components
        JLabel lblNama = new JLabel("Nama Mahasiswa:");
        txtNama = new JTextField();

        JLabel lblNIM = new JLabel("NIM:");
        txtNIM = new JTextField();

        JLabel lblProdi = new JLabel("Program Studi:");
        String[] daftarProdi = {"Teknik Informatika", "Sistem Informasi", "Ilmu Komputer"};
        cmbProdi = new JComboBox<>(daftarProdi);

        JLabel lblNilai = new JLabel("Nilai Angka (0-100):");
        txtNilai = new JTextField();

        btnProses = new JButton("Proses Nilai");
        btnReset = new JButton("Reset Form");

        // Menambahkan komponen ke panel form
        panelForm.add(lblNama);
        panelForm.add(txtNama);
        panelForm.add(lblNIM);
        panelForm.add(txtNIM);
        panelForm.add(lblProdi);
        panelForm.add(cmbProdi);
        panelForm.add(lblNilai);
        panelForm.add(txtNilai);
        panelForm.add(btnProses);
        panelForm.add(btnReset);

        // Intermediate Container: JScrollPane & JTextArea
        txtOutput = new JTextArea();
        txtOutput.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtOutput);
        scrollPane.setBorder(BorderFactory.createTitledBorder(" Riwayat Hasil Cek Nilai "));

        lblStatus = new JLabel(" Status: Ready");
        lblStatus.setPreferredSize(new Dimension(getWidth(), 25));

        // Layout Manager Utama: BorderLayout
        add(panelForm, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(lblStatus, BorderLayout.SOUTH);

        // Event Handling: ActionListener & MouseListener
        btnProses.addActionListener(this);
        btnReset.addActionListener(this);

        btnProses.addMouseListener(this);
        btnReset.addMouseListener(this);

        // Tambahan Event Adapter (KeyAdapter) untuk kemudahan input via Enter
        txtNilai.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    btnProses.doClick(); // Memicu tombol proses ketika Enter ditekan
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnProses) {
            String nama = txtNama.getText().trim();
            String nim = txtNIM.getText().trim();
            String prodi = (String) cmbProdi.getSelectedItem();
            String strNilai = txtNilai.getText().trim();

            if (nama.isEmpty() || nim.isEmpty() || strNilai.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Mohon isi semua data dengan lengkap!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                double nilai = Double.parseDouble(strNilai);
                if (nilai < 0 || nilai > 100) {
                    JOptionPane.showMessageDialog(this, "Nilai harus berada di antara 0 sampai 100!", "Error Nilai", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String hurufMutu;
                String status;

                if (nilai >= 85) {
                    hurufMutu = "A";
                    status = "LULUS (Sangat Baik)";
                } else if (nilai >= 70) {
                    hurufMutu = "B";
                    status = "LULUS (Baik)";
                } else if (nilai >= 65) {
                    hurufMutu = "C";
                    status = "LULUS (Cukup)";
                } else if (nilai >= 40) {
                    hurufMutu = "D";
                    status = "TIDAK LULUS";
                } else {
                    hurufMutu = "E";
                    status = "TIDAK LULUS";
                }

                txtOutput.append("NIM: " + nim + " | Nama: " + nama + " | Prodi: " + prodi + "\n");
                txtOutput.append("-> Nilai Angka: " + nilai + " | Huruf Mutu: " + hurufMutu + " | Status: " + status + "\n");
                txtOutput.append("--------------------------------------------------------------------------\n");

                clearForm();
                lblStatus.setText(" Status: Data nilai berhasil diproses!");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Nilai angka harus diisi dengan format angka yang valid!", "Format Salah", JOptionPane.ERROR_MESSAGE);
            }

        } else if (e.getSource() == btnReset) {
            clearForm();
            txtOutput.setText("");
            lblStatus.setText(" Status: Form di-reset");
        }
    }

    private void clearForm() {
        txtNama.setText("");
        txtNIM.setText(""); 
        txtNilai.setText("");
        cmbProdi.setSelectedIndex(0);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == btnProses) {
            lblStatus.setText(" Status: Kursor berada di atas tombol Proses Nilai");
        } else if (e.getSource() == btnReset) {
            lblStatus.setText(" Status: Kursor berada di atas tombol Reset Form");
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        lblStatus.setText(" Status: Ready");
    }

    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}